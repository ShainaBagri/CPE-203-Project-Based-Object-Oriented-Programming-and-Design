
public class AnimationAction implements Action{
	private AnimatedEntity entity;
	private int repeatCount;
	
	public AnimationAction(AnimatedEntity entity, int repeatCount) {
		this.entity = entity;
		this.repeatCount = repeatCount;
	}
	
	public void executeAction(EventScheduler scheduler) {
		entity.nextImage();

	    if(repeatCount != 1) {
	    	scheduler.scheduleEvent(entity,
	            new AnimationAction(entity, Math.max(repeatCount - 1, 0)),
	            entity.getAnimationPeriod());
	    }
	}
}
