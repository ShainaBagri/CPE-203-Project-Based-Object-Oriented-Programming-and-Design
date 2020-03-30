import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public class Crab extends MovingEntity{
	private static final String QUAKE_KEY = "quake";
	
	public Crab(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
      super(id, position, images, actionPeriod, animationPeriod);
    }
	
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		Optional<Entity> crabTarget = world.findNearest(super.getPosition(), Sgrass.class);
		      long nextPeriod = super.getActionPeriod();

		if (crabTarget.isPresent())
        {
			Point tgtPos = crabTarget.get().getPosition();

			if (moveTo(world, crabTarget.get(), scheduler))
			{
				Quake quake = new Quake(tgtPos, imageStore.getImageList(QUAKE_KEY));

				world.addEntity(quake);
				nextPeriod += super.getActionPeriod();
				quake.scheduleActions(scheduler, world, imageStore);
			}
        }

		scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), nextPeriod);
	}

	public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
		if (super.getPosition().adjacent(target.getPosition()))
	    {
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
