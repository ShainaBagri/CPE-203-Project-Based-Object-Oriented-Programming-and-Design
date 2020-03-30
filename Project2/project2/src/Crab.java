import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public class Crab implements AnimatedEntity, MovingEntity{
	private static final String QUAKE_KEY = "quake";
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int actionPeriod;
	private int animationPeriod;
	public Crab(String id, Point position,
		      int actionPeriod, int animationPeriod, List<PImage> images)
   {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;
   }
	
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point p) {
		position = p;
	}
	public PImage getCurrentImage() {
		return images.get(imageIndex);
	}
	public void nextImage() {
		imageIndex = (imageIndex + 1) % images.size();
	}
	
	public int getActionPeriod() {
		return actionPeriod;
	}
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		Optional<Entity> crabTarget = world.findNearest(position, Sgrass.class);
		      long nextPeriod = actionPeriod;

		if (crabTarget.isPresent())
        {
			Point tgtPos = crabTarget.get().getPosition();

			if (moveTo(world, crabTarget.get(), scheduler))
			{
				Quake quake = new Quake(tgtPos, imageStore.getImageList(QUAKE_KEY));

				world.addEntity(quake);
				nextPeriod += actionPeriod;
				quake.scheduleActions(scheduler, world, imageStore);
			}
        }

		scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), nextPeriod);
	}
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), actionPeriod);
	    scheduler.scheduleEvent(this, new AnimationAction(this, 0), getAnimationPeriod());
	}
	
	public int getAnimationPeriod() {
		return animationPeriod;
	}
	
	public Point nextPosition(WorldModel world, Point destPos) {
		int horiz = Integer.signum(destPos.x - position.x);
	    Point newPos = new Point(position.x + horiz, position.y);

	    Optional<Entity> occupant = world.getOccupant(newPos);

	    if (horiz == 0 ||
	    	(occupant.isPresent() && !((occupant.get()).getClass() == Fish.class)))
	    {
	    	int vert = Integer.signum(destPos.y - position.y);
	        newPos = new Point(position.x, position.y + vert);
	        occupant = world.getOccupant(newPos);

	        if (vert == 0 ||
	        	(occupant.isPresent() && !((occupant.get()).getClass() == Fish.class)))
	        {
	            newPos = position;
	        }
	     }

	     return newPos;
	}
	public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
		if (position.adjacent(target.getPosition()))
	    {
			world.removeEntity(target);
			scheduler.unscheduleAllEvents(target);
			return true;
	    }
		else
		{
			Point nextPos = nextPosition(world, target.getPosition());

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
}
