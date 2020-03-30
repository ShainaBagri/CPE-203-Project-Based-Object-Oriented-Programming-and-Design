
public class Util {
	public static double perimeter(Circle c) {
		return 2*Math.PI*c.getRadius();
	}
	public static double perimeter(Rectangle r) {
		double width = r.getTopLeft().getY() - r.getBottomRight().getY();
		double length = r.getBottomRight().getX() - r.getTopLeft().getX();
		return (2*Math.abs(width))+(2*Math.abs(length));
	}
	public static double perimeter(Polygon p) {
		double sum = 0;
		double x1, x2, y1, y2;
		for(int i=0; i<p.getPoints().size(); i++) {
			x1 = p.getPoints().get(i).getX();
			y1 = p.getPoints().get(i).getY();
			if(i==p.getPoints().size()-1) {
				x2 = p.getPoints().get(0).getX();
				y2 = p.getPoints().get(0).getY();
			}
			else {
				x2 = p.getPoints().get(i+1).getX();
				y2 = p.getPoints().get(i+1).getY();
			}
			sum += Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
		}
		return sum;
	}
}
