
public class ActivityAction implements Action{
	private ActiveEntity entity;
	private WorldModel world;
	private ImageStore imageStore;
	
	public ActivityAction(ActiveEntity entity, WorldModel world, ImageStore imageStore) {
		this.entity = entity;
		this.world = world;
		this.imageStore = imageStore;
	}
	
	public void executeAction(EventScheduler scheduler) {
		entity.executeActivity(world, imageStore, scheduler);
	}
}
