import java.util.List;

import processing.core.PImage;

public class Atlantis implements ActiveEntity, AnimatedEntity{
	private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;;
	public Atlantis(String id, Point position, List<PImage> images)
   {
      this.id = id;
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
		return 0;
	}
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		scheduler.unscheduleAllEvents(this);
	    world.removeEntity(this);
	}
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, new AnimationAction(this, ATLANTIS_ANIMATION_REPEAT_COUNT),
                getAnimationPeriod());
	}
	
	public int getAnimationPeriod() {
		return 0;
	}
	
}
