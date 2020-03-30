import java.awt.Color;
import java.awt.Point;

public class Circle implements Shape{
	private double radius;
	private Point center;
	private Color color;
	
	public Circle(double radius, Point center, Color color) {
		this.radius = radius;
		this.center = center;
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color c) {
		color = c;
	}
	public double getArea() {
		return Math.PI*radius*radius;
	}
	public double getPerimeter() {
		return 2*Math.PI*radius;
	}
	public void translate(Point p) {
		center.setLocation(center.getX()+p.getX(), center.getY()+p.getY());
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public Point getCenter() {
		return center;
	}
	public boolean equals(Object o) {
		if(o instanceof Circle) {
			Circle c = (Circle) o;
			return (radius==c.getRadius() && center.equals(c.getCenter()) && color.equals(c.getColor()));
		}
		return false;
	}
}
