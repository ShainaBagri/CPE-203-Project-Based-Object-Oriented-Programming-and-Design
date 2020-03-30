import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public class OctoNotFull implements AnimatedEntity, MovingEntity{
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int resourceLimit;
	private int resourceCount;
	private int actionPeriod;
	private int animationPeriod;
	
	public OctoNotFull(String id, int resourceLimit, Point position, int actionPeriod, 
			int animationPeriod, List<PImage> images)
	{
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;
		this.resourceLimit = resourceLimit;
		this.resourceCount = 0;
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
		Optional<Entity> notFullTarget = world.findNearest(position,
		         Fish.class);

		if (!notFullTarget.isPresent() ||
				!moveTo(world, notFullTarget.get(), scheduler) ||
				!transformNotFull(world, scheduler, imageStore))
		{
			scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),
					actionPeriod);
		}
	}
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this,
	            new ActivityAction(this, world, imageStore),
	            actionPeriod);
	    scheduler.scheduleEvent(this, new AnimationAction(this, 0),
	            getAnimationPeriod());
	}
	
	public int getAnimationPeriod() {
		return animationPeriod;
	}
	
	private boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
		if (resourceCount >= resourceLimit)
	      {
	         OctoFull octo = new OctoFull(id, resourceLimit, position, actionPeriod, animationPeriod,
	            images);

	         world.removeEntity(this);
	         scheduler.unscheduleAllEvents(this);

	         world.addEntity(octo);
	         octo.scheduleActions(scheduler, world, imageStore);

	         return true;
	      }

	      return false;
	}
	
	public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
		if (position.adjacent(target.getPosition()))
	      {
	         resourceCount += 1;
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
	public Point nextPosition(WorldModel world, Point destPos) {
		int horiz = Integer.signum(destPos.x - position.x);
	    	Point newPos = new Point(position.x + horiz, position.y);
	
	    if (horiz == 0 || world.isOccupied(newPos))
	    {
	        int vert = Integer.signum(destPos.y - position.y);
	        newPos = new Point(position.x, position.y + vert);
	
	        if (vert == 0 || world.isOccupied(newPos))
	        {
	        	newPos = position;
	        }
	    }
	
	    return newPos;
	}
}
