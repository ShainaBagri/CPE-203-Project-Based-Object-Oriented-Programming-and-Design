import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator()
   {
	   ArtistComparator artcomp = new ArtistComparator();
	   assertTrue(artcomp.compare(songs[0], songs[1]) < 0);
	   assertTrue(artcomp.compare(songs[1], songs[0]) > 0);
	   
	   assertEquals(artcomp.compare(songs[7], songs[3]), 0);
	   
	   assertTrue(artcomp.compare(songs[4], songs[5]) < 0);
	   assertTrue(artcomp.compare(songs[5], songs[4]) > 0);
   }

   @Test
   public void testLambdaTitleComparator()
   {
	   Comparator<Song> titlecomp = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
	   assertTrue(titlecomp.compare(songs[0], songs[1]) > 0);
	   assertTrue(titlecomp.compare(songs[1], songs[0]) < 0);
	   
	   assertEquals(titlecomp.compare(songs[5], songs[3]), 0);
	   
	   assertTrue(titlecomp.compare(songs[5], songs[6]) < 0);
	   assertTrue(titlecomp.compare(songs[6], songs[5]) > 0);
   }

   @Test
   public void testYearExtractorComparator()
   {
	   Comparator<Song> yearcomp = (s1, s2) -> s1.getYear()-s2.getYear();
	   Comparator<Song> year2comp = yearcomp.reversed();
	   assertTrue(year2comp.compare(songs[6], songs[7]) > 0);
	   assertTrue(year2comp.compare(songs[7], songs[6]) < 0);
	   
	   assertEquals(year2comp.compare(songs[0], songs[1]), 0);
	   
	   assertTrue(year2comp.compare(songs[4], songs[3]) < 0);
	   assertTrue(year2comp.compare(songs[3], songs[4]) > 0);
   }

   @Test
   public void testComposedComparator()
   {
	   Comparator<Song> yearcomp = (s1, s2) -> s1.getYear()-s2.getYear();
	   Comparator<Song> titlecomp = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
	   ComposedComparator comp = new ComposedComparator(yearcomp, titlecomp);
	   
	   assertTrue(comp.compare(songs[1], songs[0]) < 0);
	   assertTrue(comp.compare(songs[0], songs[1]) > 0);
	   
	   ComposedComparator comp2 = new ComposedComparator(titlecomp, yearcomp);
	   
	   assertTrue(comp2.compare(songs[7], songs[3]) < 0);
	   assertTrue(comp2.compare(songs[3], songs[7]) > 0);
   }

   @Test
   public void testThenComparing()
   {
	   Comparator<Song> titlecomp = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
	   Comparator<Song> comp = titlecomp.thenComparing(new ArtistComparator());
	   
	   assertTrue(comp.compare(songs[5], songs[3]) < 0);
	   assertTrue(comp.compare(songs[3], songs[5]) > 0);
	   assertEquals(comp.compare(songs[3], songs[7]), 0);
   }

   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );
      
      Comparator<Song> yearcomp = (s1, s2) -> s1.getYear()-s2.getYear();
	  Comparator<Song> titlecomp = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
	  ArtistComparator artcomp = new ArtistComparator();
      
      songList.sort(artcomp.thenComparing(titlecomp.thenComparing(yearcomp)));

      assertEquals(songList, expectedList);
   }
}
