
public class Rectangle {
	private final Point tl;
	private final Point br;
	
	public Rectangle(Point tl, Point br) {
		this.tl = tl;
		this.br = br;
	}
	
	public Point getTopLeft() {
		return tl;
	}
	
	public Point getBottomRight() {
		return br;
	}
	
	public double perimeter() {
		double width = tl.getY() - br.getY();
		double length = br.getX() - tl.getX();
		return (2*Math.abs(width))+(2*Math.abs(length));
	}
}
