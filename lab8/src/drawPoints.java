import processing.core.*;
import java.util.stream.*;
import java.util.ArrayList;
import java.util.List;

public class drawPoints extends PApplet {

	public void settings() {
    size(500, 500);
	}
  
	public void setup() {
    	background(180);
    	noLoop();
  	}

  	public void draw() {

  		double x, y, z;
   
  		String[] lines = loadStrings("drawMe.txt");
  		List<Point> points1 = new ArrayList<>();
  		//println("there are " + lines.length);
  		for (int i=0; i < lines.length; i++){
  			if (lines[i].length() > 0 ) {
        		String[] words= lines[i].split(",");
        		x = Double.parseDouble(words[0]);
        		y = Double.parseDouble(words[1]);
        		z = Double.parseDouble(words[2]);
        		points1.add(new Point(x, y, z));
        		//println("xy: " + x + " " + y);
        		//ellipse((int)x, (int)y, 1, 1);
      		}
  		}
  		List<Point> points2 = points1.stream()
  				.filter(p -> p.getZ()<=2.0)
  				.map(p -> new Point(p.getX()*0.5, p.getY()*0.5, 
  				p.getZ()*0.5))
  				.map(p -> new Point(p.getX()-150, p.getY()-37, 
  				p.getZ())).collect(Collectors.toList());
  		for (int i=0; i < points2.size(); i++){
  			//if (lines[i].length() > 0 ) {
        		//String[] words= lines[i].split(",");
        		//x = Double.parseDouble(words[0]);
        		//y = Double.parseDouble(words[1]);
  				Point p = points2.get(i);
  				//println("xy: " + p.getX() + " " + p.getY());
        		ellipse((int)p.getX(), (int)p.getY(), 1, 1);
      		//}
  		}
  	}

  	public static void main(String args[]) {
      PApplet.main("drawPoints");
   }
}
