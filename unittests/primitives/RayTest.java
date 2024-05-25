package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link primitives.Ray} class.
 */
class RayTest {

    @Test
    void testGetPoint() {
        Ray ray=new Ray(new Point(1,0,0),new Vector(1,0,0));
        // ============ Equivalence Partitions Tests ==============
        //positive scalar:
        assertEquals(new Point(3,0,0), ray.getPoint(2), "Incorrect calculation of a point on a horn");

        //negative distance
        assertEquals(new Point(-1,0,0), ray.getPoint(-2), "Incorrect calculation of a point on a horn");

        // =============== Boundary Values Tests ==================
        //t=0
        assertEquals(new Point(1,0,0), ray.getPoint(0), "Incorrect calculation of a point on a horn");
    }
}