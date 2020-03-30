
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
}
