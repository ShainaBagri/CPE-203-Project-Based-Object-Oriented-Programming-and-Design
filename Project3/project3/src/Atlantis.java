import java.util.List;

import processing.core.PImage;

public class Atlantis extends AnimatedEntity{
	private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;
	
	public Atlantis(String id, Point position, List<PImage> images)
    {
      super(id, position, images, 0, 0);
    }
	
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		scheduler.unscheduleAllEvents(this);
	    world.removeEntity(this);
	}
	
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, new AnimationAction(this, ATLANTIS_ANIMATION_REPEAT_COUNT),
                getAnimationPeriod());
	}
}
