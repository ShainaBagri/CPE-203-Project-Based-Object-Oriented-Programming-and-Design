import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalTime;

import org.junit.Test;

public class TestCases
{
	
   @Test
   public void testExercise1()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
   }

   @Test
   public void testExercise2()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(1, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExercise3()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertEquals(one.hashCode(), two.hashCode());
   }

   @Test
   public void testExercise4()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));

      assertNotEquals(one.hashCode(), two.hashCode());
   }
   
   @Test
   public void testExercise5()
   {
	   List<CourseSection> courselist1 = new ArrayList<>();
	   courselist1.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0)));
	   courselist1.add(new CourseSection("CSC", "225", 30,
		 LocalTime.of(1, 30), LocalTime.of(3, 0)));
	   List<CourseSection> courselist2 = new ArrayList<>();
	   courselist2.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0)));
	   courselist2.add(new CourseSection("CSC", "225", 30,
		 LocalTime.of(1, 30), LocalTime.of(3, 0)));
      final Student one = new Student("Smith", "John", 19,
         courselist1);
      final Student two = new Student("Smith", "John", 19,
         courselist2);

      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
   }
   
   @Test
   public void testExercise6()
   {
	   List<CourseSection> courselist1 = new ArrayList<>();
	   courselist1.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0)));
	   courselist1.add(new CourseSection("CSC", "225", 30,
		 LocalTime.of(1, 30), LocalTime.of(3, 0)));
	   List<CourseSection> courselist2 = new ArrayList<>();
	   courselist2.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0)));
	   courselist2.add(new CourseSection("CSC", "348", 30,
		 LocalTime.of(1, 30), LocalTime.of(3, 0)));
      final Student one = new Student("Smith", "John", 19,
         courselist1);
      final Student two = new Student("Smith", "John", 19,
         courselist2);

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }
   
   @Test
   public void testExercise7()
   {
	   List<CourseSection> courselist1 = new ArrayList<>();
	   courselist1.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0)));
	   courselist1.add(new CourseSection("CSC", "225", 30,
		 LocalTime.of(1, 30), LocalTime.of(3, 0)));
	   List<CourseSection> courselist2 = new ArrayList<>();
	   courselist2.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0)));
	   courselist2.add(new CourseSection("CSC", "225", 30,
		 LocalTime.of(1, 30), LocalTime.of(3, 0)));
      final Student one = new Student("Smith", "John", 19,
         courselist1);
      final Student two = new Student("Smith", "Julie", 19,
         courselist2);

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }
   
   @Test
   public void testExercise8()
   {
	   List<CourseSection> courselist1 = new ArrayList<>();
	   courselist1.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0)));
	   courselist1.add(new CourseSection("CSC", "225", 30,
		 LocalTime.of(1, 30), LocalTime.of(3, 0)));
	   List<CourseSection> courselist2 = new ArrayList<>();
	   courselist2.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0)));
	   courselist2.add(new CourseSection("CSC", "225", 30,
		 LocalTime.of(1, 30), LocalTime.of(3, 0)));
      final Student one = new Student("Smith", "John", 19,
         courselist1);
      final Student two = new Student("Smith", "John", 19,
         courselist2);

      assertEquals(one.hashCode(), two.hashCode());
   }
   
   @Test
   public void testExercise9()
   {
	   List<CourseSection> courselist1 = new ArrayList<>();
	   courselist1.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0)));
	   courselist1.add(new CourseSection("CSC", "225", 30,
		 LocalTime.of(1, 30), LocalTime.of(3, 0)));
	   List<CourseSection> courselist2 = new ArrayList<>();
	   courselist2.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0)));
	   courselist2.add(new CourseSection("CSC", "225", 30,
		 LocalTime.of(1, 30), LocalTime.of(3, 0)));
      final Student one = new Student("Smith", "John", 19,
         courselist1);
      final Student two = new Student("Smith", "John", 23,
         courselist2);

      assertNotEquals(one.hashCode(), two.hashCode());
   }
   
   @Test
   public void testExercise10()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", null, 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }
   
   @Test
   public void testExercise11()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         null, LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         null, LocalTime.of(11, 0));

      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
   }
}
