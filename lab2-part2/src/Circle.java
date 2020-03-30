
public class Circle {
	private final Point p;
	private final double r;
	
	public Circle(Point p, double r) {
		this.p = p;
		this.r = r;
	}
	
	public Point getCenter() {
		return p;
	}
	
	public double getRadius() {
		return r;
	}
	
	public double perimeter() {
		return 2*Math.PI*r;
	}
}
