import java.util.List;

import processing.core.PImage;

public class Quake extends AnimatedEntity{
	private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
	
	public Quake(Point position, List<PImage> images)
    {
      super(QUAKE_ID, position, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }
	
	
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		scheduler.unscheduleAllEvents(this);
		world.removeEntity(this);
	}
	
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), getActionPeriod());
	    scheduler.scheduleEvent(this, new AnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT), 
	    		getAnimationPeriod());
	}
}
