import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.Color;
import java.awt.Point;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   public static final double DELTA = 0.00001;

   /* some sample tests but you must write more! see lab write up */

   @Test
   public void testCircleGetArea()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(101.2839543, c.getArea(), DELTA);
   }

   @Test
   public void testCircleGetPerimeter()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(35.6759261, c.getPerimeter(), DELTA);
   }

   @Test public void testCircleTranslate() {
	   Circle c = new Circle(3, new Point(-2, -2), Color.BLUE);
	   c.translate(new Point(3, 4));
	   assertEquals(1, c.getCenter().getX(), DELTA);
	   assertEquals(2, c.getCenter().getY(), DELTA);
   }
   
   @Test 
	public void testCircleEquals() {
	   Circle c1 = new Circle(3, new Point(-2, -2), Color.BLUE);
	   Circle c2 = new Circle(3, new Point(-2, -2), Color.BLUE);
	   Circle c3 = new Circle(3, new Point(-2, -2), Color.RED);
	   assertTrue(c1.equals(c2));
	   assertFalse(c2.equals(c3));
	   c2 = c3;
	   assertTrue(c2.equals(c3));
   }
   
   @Test
   public void testRectangleGetArea()
   {
      Rectangle r = new Rectangle(2, 3, new Point(1, 4), Color.DARK_GRAY);

      assertEquals(6.0, r.getArea(), DELTA);
   }

   @Test
   public void testRectangleGetPerimeter()
   {
	   Rectangle r = new Rectangle(2, 3, new Point(1, 4), Color.DARK_GRAY);

	   assertEquals(10.0, r.getPerimeter(), DELTA);
   }

   @Test public void testRectangleTranslate() {
	   Rectangle r = new Rectangle(2, 3, new Point(1, 4), Color.DARK_GRAY);
	   r.translate(new Point(-1, -1));
	   assertEquals(0, r.getTopLeft().getX(), DELTA);
	   assertEquals(3, r.getTopLeft().getY(), DELTA);
   }
   
   @Test 
	public void testRectangleEquals() {
	   Rectangle r1 = new Rectangle(2, 3, new Point(1, 4), Color.DARK_GRAY);
	   Rectangle r2 = new Rectangle(2, 3, new Point(1, 4), Color.DARK_GRAY);
	   Rectangle r3 = new Rectangle(3, 2, new Point(1, 4), Color.GRAY);
	   assertTrue(r1.equals(r2));
	   assertFalse(r2.equals(r3));
	   r2 = r3;
	   assertTrue(r2.equals(r3));
   }
   
   @Test
   public void testTriangleGetArea()
   {
      Triangle t = new Triangle(new Point(0, 0), new Point(0, 5), new Point(4, 0), Color.MAGENTA);
      
      assertEquals(10.0, t.getArea(), DELTA);
   }

   @Test
   public void testTriangleGetPerimeter()
   {
	   Triangle t = new Triangle(new Point(0, 0), new Point(0, 5), new Point(4, 0), Color.MAGENTA);

      assertEquals(15.403124, t.getPerimeter(), DELTA);
   }

   @Test public void testTriangleTranslate() {
	   Triangle t = new Triangle(new Point(0, 0), new Point(0, 5), new Point(4, 0), Color.MAGENTA);
	   t.translate(new Point(1, -2));
	   assertEquals(1, t.getVertexA().getX(), DELTA);
	   assertEquals(-2, t.getVertexA().getY(), DELTA);
	   assertEquals(1, t.getVertexB().getX(), DELTA);
	   assertEquals(3, t.getVertexB().getY(), DELTA);
	   assertEquals(5, t.getVertexC().getX(), DELTA);
	   assertEquals(-2, t.getVertexC().getY(), DELTA);
   }
   
   @Test 
	public void testTriangleEquals() {
	   Triangle t1 = new Triangle(new Point(0, 0), new Point(0, 5), new Point(4, 0), Color.MAGENTA);
	   Triangle t2 = new Triangle(new Point(0, 0), new Point(0, 5), new Point(4, 0), Color.MAGENTA);
	   Triangle t3 = new Triangle(new Point(0, 0), new Point(0, 3), new Point(4, 0), Color.WHITE);
	   assertTrue(t1.equals(t2));
	   assertFalse(t2.equals(t3));
	   t2 = t3;
	   assertTrue(t2.equals(t3));
   }
   
   @Test
   public void testWorkSpaceAreaOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), 
                 Color.BLACK));

      assertEquals(114.2906063, ws.getAreaOfAllShapes(), DELTA);
   }
   
   @Test
   public void testWorkSpacePerimeterOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), 
                 Color.BLACK));

      assertEquals(61.095167, ws.getPerimeterOfAllShapes(), DELTA);
   }

   @Test
   public void testWorkSpaceGetCircles()
   {
      WorkSpace ws = new WorkSpace();
      List<Circle> expected = new LinkedList<>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(c1);
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
                 Color.BLACK));
      ws.add(c2);

      expected.add(c1);
      expected.add(c2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getCircles());
   }
   
   @Test
   public void testWorkSpaceGetRectangles()
   {
      WorkSpace ws = new WorkSpace();
      List<Rectangle> expected = new LinkedList<>();

      Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);
      Rectangle r1 = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
      Rectangle r2 = new Rectangle(2, 3, new Point(1, 4), Color.DARK_GRAY);
      Triangle t1 = new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), Color.BLACK);
      Triangle t2 = new Triangle(new Point(0, 0), new Point(0, 5), new Point(4, 0), Color.MAGENTA);
      
      ws.add(r1);
      ws.add(c1);
      ws.add(t1);
      ws.add(c2);
      ws.add(t2);
      ws.add(r2);

      expected.add(r1);
      expected.add(r2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getRectangles());
   }
   
   @Test
   public void testWorkSpaceGetTriangles()
   {
      WorkSpace ws = new WorkSpace();
      List<Triangle> expected = new LinkedList<>();

      Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);
      Rectangle r1 = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
      Rectangle r2 = new Rectangle(2, 3, new Point(1, 4), Color.DARK_GRAY);
      Triangle t1 = new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), Color.BLACK);
      Triangle t2 = new Triangle(new Point(0, 0), new Point(0, 5), new Point(4, 0), Color.MAGENTA);
      
      ws.add(r1);
      ws.add(c1);
      ws.add(t1);
      ws.add(c2);
      ws.add(t2);
      ws.add(r2);

      expected.add(t1);
      expected.add(t2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getTriangles());
   }
   
   @Test
   public void testWorkSpaceGetShapesByColor()
   {
      WorkSpace ws = new WorkSpace();
      List<Shape> expected = new LinkedList<>();

      Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);
      Rectangle r1 = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
      Rectangle r2 = new Rectangle(2, 3, new Point(1, 4), Color.DARK_GRAY);
      Triangle t1 = new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), Color.BLACK);
      Triangle t2 = new Triangle(new Point(0, 0), new Point(0, 5), new Point(4, 0), Color.MAGENTA);
      
      ws.add(r1);
      ws.add(c1);
      ws.add(t1);
      ws.add(c2);
      ws.add(t2);
      ws.add(r2);

      expected.add(r1);
      expected.add(c1);
      expected.add(t1);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getShapesByColor(Color.BLACK));
   }

   /* HINT - comment out implementation tests for the classes that you have not 
    * yet implemented */
   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getRadius", "setRadius", "getCenter", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeft", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, double.class, void.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, 
         new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testTriangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertexA", "getVertexB", "getVertexC", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class, Point.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[0], new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Triangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, clazz.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
}
