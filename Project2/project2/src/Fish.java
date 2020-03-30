import java.util.List;
import java.util.Random;

import processing.core.PImage;

public class Fish implements ActiveEntity{
	private static final Random rand = new Random();
	private static final String CRAB_KEY = "crab";
    private static final String CRAB_ID_SUFFIX = " -- crab";
    private static final int CRAB_PERIOD_SCALE = 4;
    private static final int CRAB_ANIMATION_MIN = 50;
    private static final int CRAB_ANIMATION_MAX = 150;
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int actionPeriod;
	public Fish(String id, Point position, int actionPeriod, List<PImage> images)
   {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.actionPeriod = actionPeriod;
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
		Point pos = position;  // store current position before removing

	    world.removeEntity(this);
	    scheduler.unscheduleAllEvents(this);

	    Crab crab = new Crab(id + CRAB_ID_SUFFIX, pos, actionPeriod / CRAB_PERIOD_SCALE, 
	    		CRAB_ANIMATION_MIN + rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
	            imageStore.getImageList(CRAB_KEY));

	    world.addEntity(crab);
	    crab.scheduleActions(scheduler, world, imageStore);
	}
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), actionPeriod);
	}
	
}
