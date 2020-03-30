import java.util.List;
import java.util.Optional;
import java.util.Random;
import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */


final class Entity
{
	private static final Random rand = new Random();
	
	private static final String CRAB_KEY = "crab";
   private static final String CRAB_ID_SUFFIX = " -- crab";
   private static final int CRAB_PERIOD_SCALE = 4;
   private static final int CRAB_ANIMATION_MIN = 50;
   private static final int CRAB_ANIMATION_MAX = 150;
   
   private static final String QUAKE_KEY = "quake";
   
   private static final String FISH_KEY = "fish";
   private static final String FISH_ID_PREFIX = "fish -- ";
   private static final int FISH_CORRUPT_MIN = 20000;
   private static final int FISH_CORRUPT_MAX = 30000;
   
   private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;
   
   private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
	
   private final EntityKind kind;
   private final String id;
   private Point position;
   private final List<PImage> images;
   private int imageIndex;
   private final int resourceLimit;
   private int resourceCount;
   private final int actionPeriod;
   private final int animationPeriod;

   public Entity(EntityKind kind, String id, Point position,
      List<PImage> images, int resourceLimit, int resourceCount,
      int actionPeriod, int animationPeriod)
   {
      this.kind = kind;
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;
   }
   
   public EntityKind getEntityKind() {
	   return kind;
   }
   
   public int getActionPeriod() {
	   return actionPeriod;
   }
   
   public Point getPosition() {
	   return position;
   }
   
   public void setPosition(Point p) {
	   position = p;
   }

   public static PImage getCurrentImage(Entity entity) {
	   return ((Entity)entity).images.get(((Entity)entity).imageIndex);
   }
   
   public int getAnimationPeriod()
   {
      switch (kind)
      {
         case OCTO_FULL:
         case OCTO_NOT_FULL:
         case CRAB:
         case QUAKE:
         case ATLANTIS:
            return animationPeriod;
      default:
         throw new UnsupportedOperationException(
            String.format("getAnimationPeriod not supported for %s",
            kind));
      }
   }
   
   public void nextImage()
   {
      imageIndex = (imageIndex + 1) % images.size();
   }
   
   public Action createAnimationAction(int repeatCount)
   {
      return new Action(ActionKind.ANIMATION, this, null, null, repeatCount);
   }
   
   public Action createActivityAction(WorldModel world,
      ImageStore imageStore)
   {
      return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
   }

   public void executeOctoFullActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> fullTarget = world.findNearest(position,
         EntityKind.ATLANTIS);

      if (fullTarget.isPresent() &&
         moveToFull(world, fullTarget.get(), scheduler))
      {
         //at atlantis trigger animation
    	  fullTarget.get().scheduleActions(scheduler, world, imageStore);

         //transform to unfull
         transformFull(world, scheduler, imageStore);
      }
      else
      {
    	  	scheduler.scheduleEvent(this, createActivityAction(world, imageStore),
            actionPeriod);
      }
   }
   
   public void executeOctoNotFullActivity(WorldModel world, 
		   ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> notFullTarget = world.findNearest(position,
         EntityKind.FISH);

      if (!notFullTarget.isPresent() ||
         !moveToNotFull(world, notFullTarget.get(), scheduler) ||
         !transformNotFull(world, scheduler, imageStore))
      {
    	  scheduler.scheduleEvent(this,
            createActivityAction(world, imageStore),
            actionPeriod);
      }
   }

   public void executeFishActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Point pos = position;  // store current position before removing

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      Entity crab = world.createCrab(id + CRAB_ID_SUFFIX,
              pos, actionPeriod / CRAB_PERIOD_SCALE,
              CRAB_ANIMATION_MIN +
                      rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
              imageStore.getImageList(CRAB_KEY));

      world.addEntity(crab);
      crab.scheduleActions(scheduler, world, imageStore);
   }

   public void executeCrabActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> crabTarget = world.findNearest(
         position, EntityKind.SGRASS);
      long nextPeriod = actionPeriod;

      if (crabTarget.isPresent())
      {
         Point tgtPos = crabTarget.get().position;

         if (moveToCrab(world, crabTarget.get(), scheduler))
         {
            Entity quake = world.createQuake(tgtPos,
               imageStore.getImageList(QUAKE_KEY));

            world.addEntity(quake);
            nextPeriod += actionPeriod;
            quake.scheduleActions(scheduler, world, imageStore);
         }
      }

      scheduler.scheduleEvent(this,
         createActivityAction(world, imageStore),
         nextPeriod);
   }
   
   public void executeQuakeActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
	   scheduler.unscheduleAllEvents(this);
      world.removeEntity(this);
   }
 
   public void executeAtlantisActivity(WorldModel world,
                                           ImageStore imageStore, EventScheduler scheduler)
   {
	   scheduler.unscheduleAllEvents(this);
      world.removeEntity(this);
   }

   public void executeSgrassActivity(WorldModel world,
      ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Point> openPt = world.findOpenAround(position);

      if (openPt.isPresent())
      {
         Entity fish = world.createFish(FISH_ID_PREFIX + id,
                 openPt.get(), FISH_CORRUPT_MIN +
                         rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
                         imageStore.getImageList(FISH_KEY));
         world.addEntity(fish);
         fish.scheduleActions(scheduler, world, imageStore);
      }

      scheduler.scheduleEvent(this,
         createActivityAction(world, imageStore),
         actionPeriod);
   }

   public void scheduleActions(EventScheduler scheduler,
      WorldModel world, ImageStore imageStore)
   {
      switch (kind)
      {
      case OCTO_FULL:
    	  scheduler.scheduleEvent(this,
            createActivityAction(world, imageStore),
            actionPeriod);
    	  scheduler.scheduleEvent(this, createAnimationAction(0),
            getAnimationPeriod());
         break;

      case OCTO_NOT_FULL:
    	  scheduler.scheduleEvent(this,
            createActivityAction(world, imageStore),
            actionPeriod);
         scheduler.scheduleEvent(this,
            createAnimationAction(0), getAnimationPeriod());
         break;

      case FISH:
    	  scheduler.scheduleEvent(this,
            createActivityAction(world, imageStore),
            actionPeriod);
         break;

      case CRAB:
    	  scheduler.scheduleEvent(this,
            createActivityAction(world, imageStore),
            actionPeriod);
         scheduler.scheduleEvent(this,
            createAnimationAction(0), getAnimationPeriod());
         break;

      case QUAKE:
    	  scheduler.scheduleEvent(this,
            createActivityAction(world, imageStore),
            actionPeriod);
         scheduler.scheduleEvent(this,
            createAnimationAction(QUAKE_ANIMATION_REPEAT_COUNT),
            getAnimationPeriod());
         break;

      case SGRASS:
         scheduler.scheduleEvent(this,
            createActivityAction(world, imageStore),
            actionPeriod);
         break;
      case ATLANTIS:
         scheduler.scheduleEvent(this,
                    createAnimationAction(ATLANTIS_ANIMATION_REPEAT_COUNT),
                    getAnimationPeriod());
            break;

      default:
      }
   }

   private boolean transformNotFull(WorldModel world,
      EventScheduler scheduler, ImageStore imageStore)
   {
      if (resourceCount >= resourceLimit)
      {
         Entity octo = world.createOctoFull(id, resourceLimit,
            position, actionPeriod, animationPeriod,
            images);

         world.removeEntity(this);
         scheduler.unscheduleAllEvents(this);

         world.addEntity(octo);
         octo.scheduleActions(scheduler, world, imageStore);

         return true;
      }

      return false;
   }

   private void transformFull(WorldModel world,
      EventScheduler scheduler, ImageStore imageStore)
   {
      Entity octo = world.createOctoNotFull(id, resourceLimit,
         position, actionPeriod, animationPeriod,
         images);

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      world.addEntity(octo);
      octo.scheduleActions(scheduler, world, imageStore);
   }

   private boolean moveToNotFull(WorldModel world,
      Entity target, EventScheduler scheduler)
   {
      if (position.adjacent(target.position))
      {
         resourceCount += 1;
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);

         return true;
      }
      else
      {
         Point nextPos = nextPositionOcto(world, target.position);

         if (!position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }

   private boolean moveToFull(WorldModel world,
      Entity target, EventScheduler scheduler)
   {
      if (position.adjacent(target.position))
      {
         return true;
      }
      else
      {
         Point nextPos = nextPositionOcto(world, target.position);

         if (!position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }

   private boolean moveToCrab(WorldModel world,
      Entity target, EventScheduler scheduler)
   {
      if (position.adjacent(target.position))
      {
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);
         return true;
      }
      else
      {
         Point nextPos = nextPositionCrab(world, target.position);

         if (!position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }

   private Point nextPositionOcto(WorldModel world,
      Point destPos)
   {
      int horiz = Integer.signum(destPos.x - position.x);
      Point newPos = new Point(position.x + horiz,
         position.y);

      if (horiz == 0 || world.isOccupied(newPos))
      {
         int vert = Integer.signum(destPos.y - position.y);
         newPos = new Point(position.x,
            position.y + vert);

         if (vert == 0 || world.isOccupied(newPos))
         {
            newPos = position;
         }
      }

      return newPos;
   }

   private Point nextPositionCrab(WorldModel world,
      Point destPos)
   {
      int horiz = Integer.signum(destPos.x - position.x);
      Point newPos = new Point(position.x + horiz,
         position.y);

      Optional<Entity> occupant = world.getOccupant(newPos);

      if (horiz == 0 ||
         (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
      {
         int vert = Integer.signum(destPos.y - position.y);
         newPos = new Point(position.x, position.y + vert);
         occupant = world.getOccupant(newPos);

         if (vert == 0 ||
            (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
         {
            newPos = position;
         }
      }

      return newPos;
   }

}
