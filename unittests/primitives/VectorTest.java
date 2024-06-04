package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;


/**
  * Unit tests for {@link primitives.Vector} class.
 */
class VectorTest {

    public static final double DELTA = 0.00001;
    Vector v1 = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
    Vector v4 = new Vector(1, 2, 2);

    /**
     * Test method for {@link primitives.Vector #Vector(double,double, double)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v1, new Vector(1, 2, 3), "Constructor does not work correctly");

        // =============== Boundary Values Tests ==================
        // Exception check for the zero vector
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
                "Failed constructing a correct vector");
        assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO),
                "Failed constructing a correct vector");

    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v1Opposite, v1.add(v2), "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        // Exception check for the zero vector
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite),
                "ERROR: Vector + -itself does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v2, v1.scale(-2),"ERROR: scale() does not work correctly");

        // =============== Boundary Values Tests ==================
        // Exception check for the zero vector
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
                "ERROR: scale() with zero does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(-28,v1.dotProduct(v2) , "ERROR: dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        // Checking the correctness of the result for the Dot Product method when the vectors are perpendicular
        assertEquals(0d,v1.dotProduct(v3), "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);
        assertEquals(
                v1.length() * v3.length(),
                vr.length(),
                DELTA,
                "ERROR: crossProduct() wrong result length");

        // =============== Boundary Values Tests ==================
        // Checking the correctness of the result for CrossProduct method when the vectors are parallel
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");
        assertTrue(isZero(vr.dotProduct(v1)) && isZero(vr.dotProduct(v3)),
                "ERROR: crossProduct() result is not orthogonal to its operands");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(14,v1.lengthSquared(), "ERROR: lengthSquared() wrong value");
        assertEquals(9, v4.lengthSquared(), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals( Math.sqrt(14),v1.length() , "ERROR: length() wrong value");
        assertEquals(3,v4.length() , "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector vector = new Vector(0, 3, 4);
        Vector normalized = vector.normalize();
        // ============ Equivalence Partitions Tests ==============
        assertEquals(1d, normalized.lengthSquared(), DELTA, "wrong normalized vector length");
        assertThrows(IllegalArgumentException.class, () -> vector.crossProduct(normalized),//
                "normalized vector is not in the same direction");
        assertEquals(new Vector(0, 0.6, 0.8), normalized, "wrong normalized vector");
    }
}