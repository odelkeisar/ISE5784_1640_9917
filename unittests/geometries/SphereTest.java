package geometries;
import primitives.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Sphere class.
 */
class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#Sphere(primitives.Point, double)}.
     */
    @Test
    public void testConstructor() {

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class,
                () -> new Sphere(new Point(1, 2, 0), 0),
                "ERROR: Failed constructing a correct sphere");
    }
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere s = new Sphere(new Point(0, 0, 0), 4);
        Vector v = s.getNormal(new Point(4, 0, 0));
        assertEquals(new Vector(1,0,0),v, "getNormal does not work correctly");
        assertEquals(1, v.length(),0.000001, "getNormal does not return a normal with length 1");
    }
}