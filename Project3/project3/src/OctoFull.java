import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public class OctoFull extends MovingEntity{
	private int resourceLimit;
	private int resourceCount;
	
	public OctoFull(String id, int resourceLimit, Point position, int actionPeriod, 
			int animationPeriod, List<PImage> images)
	{
		super(id, position, images, actionPeriod, animationPeriod);
		this.resourceLimit = resourceLimit;
		this.resourceCount = resourceLimit;
	}
	
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		Optional<Entity> fullTarget = world.findNearest(super.getPosition(), Atlantis.class);

		if (fullTarget.isPresent() &&
			moveTo(world, fullTarget.get(), scheduler))
		{
			//at atlantis trigger animation
			((Atlantis) (fullTarget.get())).scheduleActions(scheduler, world, imageStore);
	
			//transform to unfull
			transformFull(world, scheduler, imageStore);
		}
		else
		{
		  	scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),
	        super.getActionPeriod());
		}
	}
	
	private void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
		OctoNotFull octo = new OctoNotFull(super.getID(), resourceLimit,
		         super.getPosition(), super.getActionPeriod(), super.getAnimationPeriod(),
		         super.getImages());

		world.removeEntity(this);
		scheduler.unscheduleAllEvents(this);

		world.addEntity(octo);
		octo.scheduleActions(scheduler, world, imageStore);
	}
	
	public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
		if (super.getPosition().adjacent(target.getPosition()))
	      {
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
