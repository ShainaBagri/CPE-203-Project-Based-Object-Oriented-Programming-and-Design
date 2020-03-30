import java.time.LocalTime;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }
   
   public boolean equals(Object o) {
	   if(o==null)
		   return false;
	   if(o.getClass()!=this.getClass())
		   return false;
	   CourseSection c = (CourseSection) o;
	   boolean p = false;
	   boolean n = false;
	   boolean sT = false;
	   boolean eT = false;
	   if(c.prefix==null || prefix==null)
		   p = (c.prefix==null && prefix==null);
	   else
		   p = prefix.equals(c.prefix);
	   if(c.number==null || number==null)
		   n = (c.number==null && number==null);
	   else
		   n = number.equals(c.number);
	   if(c.startTime==null || startTime==null)
		   sT = (c.startTime==null && startTime==null);
	   else
		   sT = startTime.equals(c.startTime);
	   if(c.endTime==null || endTime==null)
		   eT = (c.endTime==null && endTime==null);
	   else
		   eT = endTime.equals(c.endTime);
	   return (p && n && enrollment==c.enrollment && sT && eT);
   }
   
   public int hashCode() {
	   int p = 0;
	   int n = 0;
	   int sT = 0;
	   int eT = 0;
	   if(prefix!=null) 
		   p = prefix.hashCode();
	   if(number!=null) 
		   n = number.hashCode();
	   if(startTime!=null) 
		   sT = startTime.hashCode();
	   if(endTime!=null) 
		   eT = endTime.hashCode();
	   return (p+n+enrollment+sT+eT);
	   
   }

   // additional likely methods not defined since they are not needed for testing
}
