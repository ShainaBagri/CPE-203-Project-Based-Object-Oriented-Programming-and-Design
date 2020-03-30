import java.util.List;

import processing.core.PImage;

public abstract class MovingEntity extends AnimatedEntity{
	public MovingEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
		super(id, position, images, actionPeriod, animationPeriod);
	}
	
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), super.getActionPeriod());
	    scheduler.scheduleEvent(this, new AnimationAction(this, 0), getAnimationPeriod());
	}
	
	public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
	
	public Point nextPosition(WorldModel world, Point destPos) {
		int horiz = Integer.signum(destPos.x - super.getPosition().x);
	    	Point newPos = new Point(super.getPosition().x + horiz, super.getPosition().y);
	
	    if (horiz == 0 || world.isOccupied(newPos))
	    {
	        int vert = Integer.signum(destPos.y - super.getPosition().y);
	        newPos = new Point(super.getPosition().x, super.getPosition().y + vert);
	
	        if (vert == 0 || world.isOccupied(newPos))
	        {
	        	newPos = super.getPosition();
	        }
	    }
	
	    return newPos;
	}
}
