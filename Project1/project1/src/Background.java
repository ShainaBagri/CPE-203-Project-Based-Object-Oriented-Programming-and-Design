import java.util.List;
import processing.core.PImage;

final class Background
{
   private final String id;
   private final List<PImage> images;
   private int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }
   
   public static PImage getCurrentImage(Background entity) {
	   return ((Background)entity).images
	            .get(((Background)entity).imageIndex);
   }
}
