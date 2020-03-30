import java.util.List;

import processing.core.PImage;

public abstract class AnimatedEntity extends ActiveEntity{
	private int animationPeriod;
	
	public AnimatedEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
		super(id, position, images, actionPeriod);
		this.animationPeriod = animationPeriod;
	}
	
	public int getAnimationPeriod() {
		return animationPeriod;
	}
	public void nextImage() {
		super.setImageIndex((super.getImageIndex() + 1) % super.getImages().size());
	}
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), super.getActionPeriod());
		scheduler.scheduleEvent(this, new AnimationAction(this, 0), getAnimationPeriod());
	}
}
