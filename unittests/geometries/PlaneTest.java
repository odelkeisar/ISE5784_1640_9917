package geometries;

import primitives.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Plane class.
 */
class PlaneTest {
    /** Test method for {@link geometries.Plane#Plane(primitives.Point,primitives.Point,primitives.Point)}. */
    @Test
    public void testConstructor() {

        // =============== Boundary Values Tests ==================

        assertThrows(IllegalArgumentException.class,
                () -> new Plane( new Point(1, 2, 0), new Point(1, 2, 0),new Point(1, 1, 1)),
                "EROR: Failed constructing a correct plain");
        assertThrows(IllegalArgumentException.class,
                () -> new Plane( new Point(2, 4, 0), new Point(1, 2, 0),new Point(3, 6, 0)),
                "EROR: Failed constructing a correct plain");
    }
    /**
     * Test method for {@link geometries.Plane#getNormal()}.
     */
    @Test
    void testGetNormal() {

        Plane plane = new Plane(new Point(1, 2, 0), new Point(3, 1, 1), new Point(1, 1, 1));

       // ============ Equivalence Partitions Tests ==============
        Vector expectedNormal1 = new Vector(0, -2, -2).normalize();
        Vector expectedNormal2 = new Vector(0, 2, 2).normalize();
        Vector actualNormal = plane.getNormal();

        assertTrue((expectedNormal1.equals(actualNormal) || expectedNormal2.equals(actualNormal)),
                "ERROR: Normal does not work correctly");
        assertEquals(1d,actualNormal.length(),0.000001, "ERROR: The normal vector is not of length 1");
    }

    /**
     * Additional test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testTestGetNormal() {
        Plane plane = new Plane(new Point(1, 2, 0), new Point(3, 1, 1), new Point(1, 1, 1));

        // ============ Equivalence Partitions Tests ==============
        Vector expectedNormal1 = new Vector(0, -2, -2);
        Vector expectedNormal2 = new Vector(0, 2, 2);
        Vector actualNormal = plane.getNormal(new Point(1,2,0));

        assertTrue(expectedNormal1.equals(actualNormal) || expectedNormal2.equals(actualNormal),
                "ERROR: Normal does not work correctly");
        assertEquals(1,actualNormal.length(), "ERROR: The normal vector is not of length 1");

    }
}