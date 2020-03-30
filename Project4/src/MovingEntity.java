import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import processing.core.PImage;

public abstract class MovingEntity extends AnimatedEntity{
	
	PathingStrategy pathStrat;

	public MovingEntity(String id, Point position, List<PImage> images, int actionPeriod, 
			int animationPeriod) {
		super(id, position, images, actionPeriod, animationPeriod);
		this.pathStrat = new AStarPathingStrategy();
	}
	
	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), super.getActionPeriod());
	    scheduler.scheduleEvent(this, new AnimationAction(this, 0), getAnimationPeriod());
	}
	
	public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
	
	public Point nextPosition(WorldModel world, Point destPos) {
		List<Point> points = pathStrat.computePath(this.getPosition(), destPos,
                p ->  world.withinBounds(p) && !world.isOccupied(p),
                (p1, p2) -> neighbors(p1,p2),
                DIAGONAL_CARDINAL_NEIGHBORS);
		
		if (points.size() == 0)
        {
           return this.getPosition();
        }
		
		return points.get(0);
	}
	
	private static boolean neighbors(Point p1, Point p2) {
	      return p1.x+1 == p2.x && p1.y == p2.y ||
	             p1.x-1 == p2.x && p1.y == p2.y ||
	             p1.x == p2.x && p1.y+1 == p2.y ||
	             p1.x == p2.x && p1.y-1 == p2.y;
	}
	
	private static final Function<Point, Stream<Point>> DIAGONAL_CARDINAL_NEIGHBORS =
		      point ->
		         Stream.<Point>builder()
		            .add(new Point(point.x - 1, point.y - 1))
		            .add(new Point(point.x + 1, point.y + 1))
		            .add(new Point(point.x - 1, point.y + 1))
		            .add(new Point(point.x + 1, point.y - 1))
		            .add(new Point(point.x, point.y - 1))
		            .add(new Point(point.x, point.y + 1))
		            .add(new Point(point.x - 1, point.y))
		            .add(new Point(point.x + 1, point.y))
		            .build();
}
