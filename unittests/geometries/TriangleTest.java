package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

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
}