package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link primitives.Ray} class.
 */
class RayTest {
    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        //positive scalar:
        assertEquals(new Point(3, 0, 0), ray.getPoint(2), "Incorrect calculation of a point on a horn");

        //negative distance
        assertEquals(new Point(-1, 0, 0), ray.getPoint(-2), "Incorrect calculation of a point on a horn");

        // =============== Boundary Values Tests ==================
        //t=0
        assertEquals(new Point(1, 0, 0), ray.getPoint(0), "Incorrect calculation of a point on a horn");
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: The closest point is in the middle of the list
        List<Point> points =
                List.of(new Point(2, 2, 2), new Point(2, 1, 1), new Point(3, 3, 3));
        assertEquals(points.get(1), ray.findClosestPoint(points),
                "The closest point is not found correctly");
        // =============== Boundary Values Tests ==================
        // TC10: The list is null
        assertNull(ray.findClosestPoint(null),
                "The function should return null when the list is null");

        // TC11: The closest point is the first point in the list
        points = List.of(new Point(2, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3));
        assertEquals(new Point(2, 1, 1), ray.findClosestPoint(points),
                "The closest point is not found correctly");

        // TC12: The closest point is the last point in the list
        points = List.of(new Point(2, 2, 2), new Point(3, 3, 3), new Point(2, 1, 1));
        assertEquals(new Point(2, 1, 1), ray.findClosestPoint(points),
                "The closest point is not found correctly");
    }
}