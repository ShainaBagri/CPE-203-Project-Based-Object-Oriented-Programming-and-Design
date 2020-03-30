import java.util.List;

import processing.core.PImage;

public class Entity {
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	
	public Entity(String id, Point position, List<PImage> images) {
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;
	}
	
	public String getID() {
		return id;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point p) {
		position = p;
	}
	public PImage getCurrentImage() {
		return images.get(imageIndex);
	}
	public List<PImage> getImages() {
		return images;
	}
	public int getImageIndex() {
		return imageIndex;
	}
	public void setImageIndex(int ind) {
		imageIndex = ind;
	}
}
