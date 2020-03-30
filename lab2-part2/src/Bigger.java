
public class Bigger {
	public static double whichIsBigger(Circle c, Rectangle r, Polygon p) {
		return Math.max(c.perimeter(), Math.max(r.perimeter(), p.perimeter()));
	}
}
