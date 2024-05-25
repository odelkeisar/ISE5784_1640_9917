package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Triangle class.
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#Triangle(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    public void testConstructor() {

        // =============== Boundary Values Tests ==================
        //Throwing an error when 2 points merge:
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(new Point(1, 2, 0), new Point(1, 2, 0), new Point(1, 1, 1)),
                "ERROR: Failed constructing a correct triangle");

        //Throwing an error when 3 points appear on one line:
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(new Point(2, 4, 0), new Point(1, 2, 0), new Point(3, 6, 0)),
                "ERROR: Failed constructing a correct triangle");
    }

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        Triangle t = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        assertEquals(new Vector(0, 0, 1), t.getNormal(new Point(0, 0, 0)), "ERROR: GetNormal ERROR");
    }


    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}}.
     */
    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        Plane plane = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        Ray ray;
        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside triangle(1 point)
        ray = new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1));
        assertEquals(List.of(new Point(1d / 3, 1d / 3, 1d / 3)), triangle.findIntersections(ray),
                "Bad intersection");

        // TC02: Against edge(0 points)
        ray = new Ray(new Point(0, 0, -1), new Vector(1, 1, 0));
        assertEquals(List.of(new Point(1, 1, -1)), plane.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "Bad intersection");

        // TC03: Against vertex(0 points)
        ray = new Ray(new Point(0, 0, 2), new Vector(-1, -1, 0));
        assertEquals(List.of(new Point(-0.5, -0.5, 2)), plane.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "Bad intersection");


        // =============== Boundary Values Tests ==================
        // TC04: Point on edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0, -6, 0.5), new Vector(-0.5, 10, -0.5))),
                "Point on edge");

        // TC05: Point in vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0, -6, 1), new Vector(0, 10, 0)))
                ,"Point in vertex");

        // TC06: Point on edge's continuation (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0, -6, -4), new Vector(0, 10, 5)))
                ,"Point on edge's continuation");
    }
}