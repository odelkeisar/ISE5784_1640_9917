package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void testFindIntersections() {

        Geometries gr = new Geometries();

        // ============ Boundary Value Test ==============

        //checks for an empty geometry list.
        assertNull(gr.findIntersections(new Ray(new Point(0,1,0),new Vector(1,0,0))),"empty collection");

        // checks for no intersections
        Sphere sphere = new Sphere(new Point(1,0,0), 4.0);
        Triangle triangle = new Triangle(new Point(-1, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Plane plane = new Plane(new Point(1, 0, 0), new Vector(0, 0, 1));
        Geometries gr1=new Geometries(sphere,triangle ,plane);
        assertNull(gr1.findIntersections(new Ray(new Point(-4, 6, 2),new Vector(1, 0, 0))));

        //checks for one expected intersection
        assertEquals(1,gr1.findIntersections(new Ray(new Point(-3, -4, -1), new Vector(4, -3, 1))).size());

        // intersects with all the shapes
        assertEquals(4,gr1.findIntersections(new Ray(new Point(0, 0.5, 6), new Vector(0, 0, -12))).size());

        // ============ Equivalence Partitions Tests ==============
        //checks for expected more than one shape intersecting
        assertEquals(3,gr1.findIntersections(new Ray(new Point(0, 3, 6), new Vector(0, 0, -12))).size());
    }
}