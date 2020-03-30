import java.awt.Color;
import java.awt.Point;

public class Triangle implements Shape{
	private Point a;
	private Point b;
	private Point c;
	private Color color;
	
	public Triangle(Point a, Point b, Point c, Color color) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color c) {
		color = c;
	}
	public double getArea() {
		double num1 = a.getX()*(b.getY()-c.getY());
		double num2 = b.getX()*(c.getY()-a.getY());
		double num3 = c.getX()*(a.getY()-b.getY());
		return Math.abs((num1 + num2 + num3)/2);
	}
	public double getPerimeter() {
		double d1 = Math.sqrt(((a.getX()-b.getX())*(a.getX()-b.getX()))+((a.getY()-b.getY())*(a.getY()-b.getY())));
		double d2 = Math.sqrt(((b.getX()-c.getX())*(b.getX()-c.getX()))+((b.getY()-c.getY())*(b.getY()-c.getY())));
		double d3 = Math.sqrt(((c.getX()-a.getX())*(c.getX()-a.getX()))+((c.getY()-a.getY())*(c.getY()-a.getY())));
		return d1+d2+d3;
	}
	public void translate(Point p) {
		a.setLocation(a.getX()+p.getX(), a.getY()+p.getY());
		b.setLocation(b.getX()+p.getX(), b.getY()+p.getY());
		c.setLocation(c.getX()+p.getX(), c.getY()+p.getY());
	}
	public Point getVertexA() {
		return a;
	}
	public Point getVertexB() {
		return b;
	}
	public Point getVertexC() {
		return c;
	}
	public boolean equals(Object o) {
		if(o instanceof Triangle) {
			Triangle t = (Triangle) o;
			return (a.equals(t.getVertexA()) && b.equals(t.getVertexB()) && c.equals(t.getVertexC()) && color.equals(t.getColor()));
		}
		return false;
	}
}
