
public class PathNode {
	private Point p;
	private int g;
	private int h;
	private PathNode prev;
	
	public PathNode(Point p, int g, int h, PathNode prev) {
		this.p = p;
		this.g = g;
		this.h = h;
		this.prev = prev;
	}
	
	public int getG() {
		return g;
	}
	public int getF() {
		return g + h;
	}
	public Point getPoint() {
		return p;
	}
	public PathNode getPrev() {
		return prev;
	}
	
	public void setG(int newG) {
		g = newG;
	}
	public void setPrev(PathNode newPrev) {
		prev = newPrev;
	}
}
