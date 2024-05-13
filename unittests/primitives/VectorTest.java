package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the Vector class.
 */
class VectorTest {
    private final double DELTA = 0.000001;

    /** Test method for {@link primitives.Vector #Vector(double,double, double)}. */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================

        // TC01: Exception check for the zero vector
       assertThrows(IllegalArgumentException.class, () -> new Vector(0,0,0),"Failed constructing a correct vector");
       assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO),"Failed constructing a correct vector");

    }
    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {

    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        // TODO: Implement test cases for the scale method
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {

    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {

    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {

    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {

    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
     // ============ Equivalence Partitions Tests ==============
     // TC01: Simple test
        assertEquals(1d, n.lengthSquared(), 0.00001, "wrong normalized vector length");
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n),//
                "normalized vector is not in the same direction");
        assertEquals(new Vector(0, 0.6, 0.8), n, "wrong normalized vector");
    }
}