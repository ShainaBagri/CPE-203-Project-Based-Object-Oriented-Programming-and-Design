import java.awt.Color;
import java.awt.Point;

public interface Shape {
	public Color getColor();
	public void setColor(Color c);
	public double getArea();
	public double getPerimeter();
	public void translate(Point p);
}
