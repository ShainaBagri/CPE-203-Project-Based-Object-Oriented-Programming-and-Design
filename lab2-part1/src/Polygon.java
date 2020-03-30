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
	
}
