import java.util.List;

import processing.core.PImage;

public class Quake implements ActiveEntity, AnimatedEntity{
	private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	public Quake(Point position, List<PImage> images)
   {
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
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
		return QUAKE_ACTION_PERIOD;
	}
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		
	}
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), getActionPeriod());
	    scheduler.scheduleEvent(this, new AnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT),
	            getAnimationPeriod());
	}
	
	public int getAnimationPeriod() {
		return QUAKE_ANIMATION_PERIOD;
	}
}
