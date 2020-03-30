import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public class Sgrass implements ActiveEntity{
	private static final Random rand = new Random();
	private static final String FISH_KEY = "fish";
	private static final String FISH_ID_PREFIX = "fish -- ";
	private static final int FISH_CORRUPT_MIN = 20000;
	private static final int FISH_CORRUPT_MAX = 30000;
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int actionPeriod;
	public Sgrass(String id, Point position, int actionPeriod, List<PImage> images)
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
		Optional<Point> openPt = world.findOpenAround(position);

	    if (openPt.isPresent())
	    {
	    	Fish fish = new Fish(FISH_ID_PREFIX + id, openPt.get(), FISH_CORRUPT_MIN +
	        	rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
	            	imageStore.getImageList(FISH_KEY));
	        world.addEntity(fish);
	        fish.scheduleActions(scheduler, world, imageStore);
	     }

	     scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), actionPeriod);
	}
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), actionPeriod);
	}
	
}
