import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public class OctoFull implements AnimatedEntity, MovingEntity{
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int resourceLimit;
	private int resourceCount;
	private int actionPeriod;
	private int animationPeriod;
	
	public OctoFull(String id, int resourceLimit, Point position, int actionPeriod, 
			int animationPeriod, List<PImage> images)
	{
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;
		this.resourceLimit = resourceLimit;
		this.resourceCount = resourceLimit;
		this.actionPeriod = actionPeriod;
		this.animationPeriod = animationPeriod;
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
		Optional<Entity> fullTarget = world.findNearest(position, Atlantis.class);

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
	        actionPeriod);
		}
	}
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this,
	            new ActivityAction(this, world, imageStore),
	            actionPeriod);
	    scheduler.scheduleEvent(this, new AnimationAction(this, 0),
	            getAnimationPeriod());
	}
	
	public int getAnimationPeriod() {
		return animationPeriod;
	}
	
	private void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
		OctoNotFull octo = new OctoNotFull(id, resourceLimit,
		         position, actionPeriod, animationPeriod,
		         images);

		world.removeEntity(this);
		scheduler.unscheduleAllEvents(this);

		world.addEntity(octo);
		octo.scheduleActions(scheduler, world, imageStore);
	}
	
	public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
		if (position.adjacent(target.getPosition()))
	      {
	         return true;
	      }
	      else
	      {
	         Point nextPos = nextPosition(world, target.getPosition());

	         if (!position.equals(nextPos))
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
	public Point nextPosition(WorldModel world, Point destPos) {
		int horiz = Integer.signum(destPos.x - position.x);
	    	Point newPos = new Point(position.x + horiz, position.y);
	
	    if (horiz == 0 || world.isOccupied(newPos))
	    {
	        int vert = Integer.signum(destPos.y - position.y);
	        newPos = new Point(position.x, position.y + vert);
	
	        if (vert == 0 || world.isOccupied(newPos))
	        {
	        	newPos = position;
	        }
	    }
	
	    return newPos;
	}
}
