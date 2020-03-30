import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public class Sgrass extends ActiveEntity{
	private static final Random rand = new Random();
	private static final String FISH_KEY = "fish";
	private static final String FISH_ID_PREFIX = "fish -- ";
	private static final int FISH_CORRUPT_MIN = 20000;
	private static final int FISH_CORRUPT_MAX = 30000;

	public Sgrass(String id, Point position, int actionPeriod, List<PImage> images)
    {
      super(id, position, images, actionPeriod);
    }
	
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		Optional<Point> openPt = world.findOpenAround(super.getPosition());

	    if (openPt.isPresent())
	    {
	    	Fish fish = new Fish(FISH_ID_PREFIX + super.getID(), openPt.get(), FISH_CORRUPT_MIN +
	        	rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
	            	imageStore.getImageList(FISH_KEY));
	        world.addEntity(fish);
	        fish.scheduleActions(scheduler, world, imageStore);
	     }

	     scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), super.getActionPeriod());
	}
}
