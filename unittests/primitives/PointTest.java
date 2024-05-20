package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.isZero;

/**
 * * Unit tests for {@link primitives.Point} class.
 */
class PointTest {
    Point  p1         = new Point(1, 2, 3);
    Point  p2         = new Point(2, 4, 6);
    Point  p3         = new Point(2, 4, 5);

    Vector v1         = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);
    Vector v2         = new Vector(-2, -4, -6);
    Vector v3         = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {

        // ============ Equivalence Partitions Tests ==============
        //  TC01:Checking the correctness of the result for a subtraction method
        assertEquals(v1, p2.subtract(p1) ,"ERROR: (point2 - point1) does not work correctly");
        assertEquals( new Vector(-1, 0, 0),
                new Point(2, -2, -3).subtract( new Point(3, -2, -3)),
                "ERROR: Point - Point does not work correctly");

        // =============== Boundary Values Tests ==================
       //TC02:Exception throw test for zero vector result
        assertThrows(IllegalArgumentException.class,()->p1.subtract(p1),
                "ERROR: (point - itself) does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC03:Checking the correctness of the result for add method
        assertEquals(p2 ,p1.add(v1),"ERROR: (point + vector) = other point does not work correctly" );
        assertEquals(Point.ZERO, p1.add(v1Opposite),
                "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // =============== Boundary Values Tests ==================
        // TC04:Checking the correctness of the result for DistanceSquared method when the result is 0.
        assertTrue(isZero(p1.distanceSquared(p1)),"ERROR: point squared distance to itself is not zero");

        // ============ Equivalence Partitions Tests ==============
        // TC05:Checking the correctness of the result for DistanceSquared method
        assertTrue(isZero(p1.distanceSquared(p3) - 9),"ERROR: squared distance between points is wrong");
        assertTrue(isZero(p3.distanceSquared(p1) - 9),"ERROR: squared distance between points is wrong");

    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        // =============== Boundary Values Tests ==================
        // TC06:Checking the correctness of the result for Distance method when the result is 0.
        assertTrue(isZero(p1.distance(p1)),"ERROR: point distance to itself is not zero");

        // ============ Equivalence Partitions Tests ==============
        // TC07:Checking the correctness of the result for Distance method
        assertTrue(isZero(p1.distance(p3) - 3),"ERROR: distance between points is wrong");
        assertTrue(isZero(p3.distance(p1) - 3),"ERROR: distance between points is wrong");

    }
}