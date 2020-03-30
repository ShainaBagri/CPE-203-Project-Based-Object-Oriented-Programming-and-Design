import java.awt.Color;
import java.awt.Point;

public class Rectangle implements Shape{
	private double width;
	private double height;
	private Point topLeft;
	private Color color;
	
	public Rectangle(double width, double height, Point topLeft, Color color) {
		this.width = width;
		this.height = height;
		this.topLeft = topLeft;
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color c) {
		color = c;
	}
	public double getArea() {
		return width*height;
	}
	public double getPerimeter() {
		return (2*width)+(2*height);
	}
	public void translate(Point p) {
		topLeft.setLocation(topLeft.getX()+p.getX(), topLeft.getY()+p.getY());
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public Point getTopLeft() {
		return topLeft;
	}
	public boolean equals(Object o) {
		if(o instanceof Rectangle) {
			Rectangle r = (Rectangle) o;
			return (width==r.getWidth() && height==r.getHeight() && topLeft.equals(r.getTopLeft()) && color.equals(r.getColor()));
		}
		return false;
	}
}
