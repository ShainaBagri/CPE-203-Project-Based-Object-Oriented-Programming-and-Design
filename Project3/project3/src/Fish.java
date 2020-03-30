import java.util.List;
import java.util.Random;

import processing.core.PImage;

public class Fish extends ActiveEntity{
	private static final Random rand = new Random();
	private static final String CRAB_KEY = "crab";
    private static final String CRAB_ID_SUFFIX = " -- crab";
    private static final int CRAB_PERIOD_SCALE = 4;
    private static final int CRAB_ANIMATION_MIN = 50;
    private static final int CRAB_ANIMATION_MAX = 150;

	public Fish(String id, Point position, int actionPeriod, List<PImage> images)
    {
      super(id, position, images, actionPeriod);
    }
	
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		Point pos = super.getPosition();  // store current position before removing

	    world.removeEntity(this);
	    scheduler.unscheduleAllEvents(this);

	    Crab crab = new Crab(super.getID() + CRAB_ID_SUFFIX, pos, super.getActionPeriod() / CRAB_PERIOD_SCALE, 
	    		CRAB_ANIMATION_MIN + rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
	            imageStore.getImageList(CRAB_KEY));

	    world.addEntity(crab);
	    crab.scheduleActions(scheduler, world, imageStore);
	}
}
