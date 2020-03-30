import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public class OctoNotFull extends MovingEntity{
	private int resourceLimit;
	private int resourceCount;
	
	public OctoNotFull(String id, int resourceLimit, Point position, int actionPeriod, 
			int animationPeriod, List<PImage> images)
	{
		super(id, position, images, actionPeriod, animationPeriod);
		this.resourceLimit = resourceLimit;
		this.resourceCount = 0;
	}
	
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		Optional<Entity> notFullTarget = world.findNearest(super.getPosition(),
		         Fish.class);

		if (!notFullTarget.isPresent() ||
				!moveTo(world, notFullTarget.get(), scheduler) ||
				!transformNotFull(world, scheduler, imageStore))
		{
			scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),
					super.getActionPeriod());
		}
	}
	
	private boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
		if (resourceCount >= resourceLimit)
	      {
	         OctoFull octo = new OctoFull(super.getID(), resourceLimit, super.getPosition(), 
	        		 super.getActionPeriod(), super.getAnimationPeriod(), super.getImages());

	         world.removeEntity(this);
	         scheduler.unscheduleAllEvents(this);

	         world.addEntity(octo);
	         octo.scheduleActions(scheduler, world, imageStore);

	         return true;
	      }

	      return false;
	}
	
	public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
		if (super.getPosition().adjacent(target.getPosition()))
	      {
	         resourceCount += 1;
	         world.removeEntity(target);
	         scheduler.unscheduleAllEvents(target);

	         return true;
	      }
	      else
	      {
	         Point nextPos = nextPosition(world, target.getPosition());

	         if (!super.getPosition().equals(nextPos))
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
