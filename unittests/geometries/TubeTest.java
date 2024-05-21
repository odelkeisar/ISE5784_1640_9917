package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tube class.
 */
class TubeTest {

    Point p = new Point(2, 4, 5);
    Vector v = new Vector(1, 2, 3);

    /**
     * Test method for {@link geometries.Tube#Tube(double, Ray)}.
     */
    @Test
    public void testConstructor() {

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class,
                () -> new Tube(0, new Ray(p, v)),
                "ERROR: Failed constructing a correct tube");
    }

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        Tube t = new Tube(1.0, new Ray(new Point(1, 1, 1), new Vector(1, 0, 0)));
        Point p1 = new Point(3, 1, 0);
        assertEquals(new Vector(0, 0, -1), t.getNormal(p1), "ERROR: not the correct normal");

        // =============== Boundary Values Tests ==================
        // if t is zero, the point is opposite the ray head, return the normalized vector from the point to the ray head
        Point p2 = new Point(1, 2, 1);
        assertEquals(new Vector(0, 1, 0), t.getNormal(p2), "ERROR: not the correct normal");
    }


    /**
     * Test method for {@link geometries.Tube#findIntersections(Ray)} indIntersections(primitives.Point)}.
     */
    @Test
    void testFindIntersections() {
    }
}