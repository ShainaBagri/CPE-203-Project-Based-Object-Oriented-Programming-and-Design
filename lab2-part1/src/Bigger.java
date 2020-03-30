
public class Bigger {
	public static double whichIsBigger(Circle c, Rectangle r, Polygon p) {
		return Math.max(Util.perimeter(c), Math.max(Util.perimeter(r), Util.perimeter(p)));
	}
}
