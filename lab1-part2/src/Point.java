
public class Point {
	private double x;
	private double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getRadius() {
		return Math.sqrt((x*x)+(y*y));
	}
	public double getAngle() { 
		double angle = Math.atan(y/x);
		if(x<0 && y>0)
			return Math.PI+angle;
		else if(x<0 && y<0)
			return (-1)*Math.PI+angle;
		else
			return angle;
			
	}
	public Point rotate90() {
		Point p = new Point(-1*y, x);
		return p;
	}
}
