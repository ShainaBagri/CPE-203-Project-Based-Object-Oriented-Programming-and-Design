import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class WorkSpace {
	private List<Shape> lis;
	
	public WorkSpace() {
		lis = new ArrayList<>();
	}
	public void add(Shape shape) {
		lis.add(shape);
	}
	public Shape get(int index) {
		return lis.get(index);
	}
	public int size() {
		return lis.size();
	}
	public List<Circle> getCircles() {
		List<Circle> ans = new ArrayList<>();
		for(Shape s:lis) {
			if(s instanceof Circle) {
				ans.add((Circle)s);
			}
		}
		return ans;
	}
	public List<Rectangle> getRectangles() {
		List<Rectangle> ans = new ArrayList<>();
		for(Shape s:lis) {
			if(s instanceof Rectangle) {
				ans.add((Rectangle)s);
			}
		}
		return ans;
	}
	public List<Triangle> getTriangles() {
		List<Triangle> ans = new ArrayList<>();
		for(Shape s:lis) {
			if(s instanceof Triangle) {
				ans.add((Triangle)s);
			}
		}
		return ans;
	}
	public List<Shape> getShapesByColor(Color color) {
		List<Shape> ans = new ArrayList<>();
		for(Shape s:lis) {
			if(s.getColor().equals(color)) {
				ans.add(s);
			}
		}
		return ans;
	}
	public double getAreaOfAllShapes() {
		double sum = 0;
		for(Shape s:lis) {
			sum+=s.getArea();
		}
		return sum;
	}
	public double getPerimeterOfAllShapes() {
		double sum = 0;
		for(Shape s:lis) {
			sum+=s.getPerimeter();
		}
		return sum;
	}
}
