package geometries;

import primitives.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the getNormal method in the Cylinder class.
 */
class CylinderTest {
    /**
     * Test method for {@link geometries.Cylinder #Cylinder(double, primitives.Ray, double)}.
    */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // Testing for not throwing an error in the construction of a normal cylinder
        assertDoesNotThrow(
                ()->new Cylinder(3,new Ray(new Point(1,2,3),new Vector(1,1,1)),2),
                "ERROR: Failed constructing a correct cylinder");

        // =============== Boundary Values Tests ==================
        // Error injection test in incorrect cylinder build
        assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(3,new Ray(new Point(1,2,3),new Vector(1,1,1)),0),
                "ERROR: Failed constructing a correct cylinder");
        assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(3,new Ray(new Point(1,2,3),new Vector(1,1,1)),-2),
                "ERROR: Failed constructing a correct cylinder");
    }


    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
     void testGetNormal() {

        Cylinder c = new Cylinder(1.0, new Ray(new Point(1, 1, 1), new Vector(1, 0, 0)), 2.0);

        // ============ Equivalence Partitions Tests ==============
        // Check on the side
        Point p1 = new Point(2, 1, 0);
        assertEquals(new Vector(0, 0, -1), c.getNormal(p1), "ERROR: not the correct normal");
        // Check on any basis:
        Point p3 = new Point(1, 1, 1.5);
        assertEquals(new Vector(1, 0, 0), c.getNormal(p3), "ERROR: not the correct normal");

        Point p4 = new Point(3, 1, 1.5);
        assertEquals(new Vector(1, 0, 0), c.getNormal(p4), "ERROR: not the correct normal");
        // =============== Boundary Values Tests ==================

        //Check on the centers of the bases
        Point p6 = new Point(3, 1, 1);
        assertEquals(new Vector(1, 0, 0), c.getNormal(p6), "ERROR: not the correct normal");

        Point p5 = new Point(1, 1, 1);
        assertEquals(new Vector(1, 0, 0), c.getNormal(p5), "ERROR: not the correct normal");

    }
}