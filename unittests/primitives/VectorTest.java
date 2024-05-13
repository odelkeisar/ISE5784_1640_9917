package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;


/**
 * Unit tests for the Vector class.
 */
class VectorTest {

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
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
                "ERROR: scale() with zero does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");
        // =============== Boundary Values Tests ==================
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.length() - v1.length() * v3.length()),
                "ERROR: crossProduct() wrong result length");

        // =============== Boundary Values Tests ==================
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
        assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
        assertTrue(isZero(v4.lengthSquared() - 9), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(isZero(v1.length() - Math.sqrt(14)), "ERROR: length() wrong value");
        assertTrue(isZero(v4.length() - 3), "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        assertEquals(1d, n.lengthSquared(), 0.00001, "wrong normalized vector length");
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n),//
                "normalized vector is not in the same direction");
        assertEquals(new Vector(0, 0.6, 0.8), n, "wrong normalized vector");
    }
}