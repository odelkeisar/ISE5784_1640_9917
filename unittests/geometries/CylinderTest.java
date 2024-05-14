package geometries;

import primitives.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the getNormal method in the Cylinder class.
 */
class CylinderTest {


    @Test
    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    void testGetNormal() {

        Cylinder c = new Cylinder(1.0, new Ray(new Point(1, 1, 1), new Vector(1, 0, 0)), 2);

        // ============ Equivalence Partitions Tests ==============
        //Check on the side
        Point p1 = new Point(3, 1, 0);
        assertEquals(new Vector(0, 0, -1), c.getNormal(p1), "ERROR: not the correct normal");
        //Check on any basis:
        Point p3 = new Point(1, 1, 1.5);
        assertEquals(new Vector(1, 0, 0), c.getNormal(p3), "ERROR: not the correct normal");

        Point p4 = new Point(3, 1, 1.5);
        assertEquals(new Vector(1, 0, 0), c.getNormal(p4), "ERROR: not the correct normal");
        // =============== Boundary Values Tests ==================

        //if v*(p-p0)=0
        Point p2 = new Point(1, 2, 1);
        assertEquals(new Vector(0, 1, 0), c.getNormal(p2), "ERROR: not the correct normal");

        //Check on the centers of the bases
        Point p6 = new Point(3, 1, 1);
        assertEquals(new Vector(1, 0, 0), c.getNormal(p6), "ERROR: not the correct normal");

        Point p5 = new Point(1, 1, 1);
        assertEquals(new Vector(1, 0, 0), c.getNormal(p5), "ERROR: not the correct normal");

    }
}