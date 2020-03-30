import java.util.List;
import java.util.Objects;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }
   
   public boolean equals(Object o) {
	   if(o.getClass()!=this.getClass())
		   return false;
	   Student s = (Student) o;
	   return (Objects.equals(surname, s.surname) && Objects.equals(givenName, s.givenName) &&
			   Objects.equals(age, s.age) && Objects.equals(currentCourses, s.currentCourses));
   }
   
   public int hashCode() {
	   return Objects.hash(surname, givenName, age, currentCourses);
   }
}
