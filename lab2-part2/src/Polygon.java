import java.util.Arrays;
import java.util.List;

public class Polygon {
	private final List<Point> pts;
	
	public Polygon(List<Point> arr) {
		pts = arr;
	}
	
	public List<Point> getPoints() {
		return pts;
	}
	
	public double perimeter() {
		double sum = 0;
		double x1, x2, y1, y2;
		for(int i=0; i<pts.size(); i++) {
			x1 = pts.get(i).getX();
			y1 = pts.get(i).getY();
			if(i==pts.size()-1) {
				x2 = pts.get(0).getX();
				y2 = pts.get(0).getY();
			}
			else {
				x2 = pts.get(i+1).getX();
				y2 = pts.get(i+1).getY();
			}
			sum += Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
		}
		return sum;
	}
	
}
