import processing.core.PImage;

public interface Entity {
	public Point getPosition();
	public void setPosition(Point p);
	public PImage getCurrentImage();
	public void nextImage();
}
