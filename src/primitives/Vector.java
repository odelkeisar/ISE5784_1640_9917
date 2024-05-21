package primitives;

/**
 * A Vector represents a mathematical vector in 3D space.
 * It extends the Point class and provides methods for vector operations.
 */
public class Vector extends Point {

    /**
     * Constructs a Vector with the specified x, y, and z coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @throws IllegalArgumentException If the vector is the zero vector.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector 0 cannot be defined");
    }

    /**
     * Constructs a Vector with the specified Double3 object.
     *
     * @param xyz The Double3 object representing the coordinates.
     * @throws IllegalArgumentException If the vector is the zero vector.
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector 0 cannot be defined");
    }

    /**
     * Adds another vector to this vector.
     *
     * @param vector The vector to add.
     * @return A new Vector representing the sum of this vector and the given vector.
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    /**
     * Scales this vector by a scalar value.
     *
     * @param scale The scaling factor.
     * @return A new Vector representing the scaled vector.
     */
    public Vector scale(double scale) {
        return new Vector(xyz.scale(scale));
    }

    /**
     * Performs a scalar multiplication between a vector and the vector received as a parameter
     *
     * @param vector The other vector.
     * @return The dot product of the two vectors.
     */
    public double dotProduct(Vector vector) {
        return this.xyz.d1 * vector.xyz.d1 + this.xyz.d2 * vector.xyz.d2 + this.xyz.d3 * vector.xyz.d3;
    }

    /**
     * Calculates a vector product of this vector and another vector.
     *
     * @param vector The other vector.
     * @return A new Vector representing the cross product.
     */
    public Vector crossProduct(Vector vector) {
        double newX = this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2;
        double newY = this.xyz.d3 * vector.xyz.d1 - this.xyz.d1 * vector.xyz.d3;
        double newZ = this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1;
        return new Vector(new Double3(newX, newY, newZ));
    }

    /**
     * Calculates the squared length of this vector.
     *
     * @return The squared length of the vector.
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * Calculates the length of this vector.
     *
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a new vector that is a normalized version of this vector.
     *
     * @return The normalized vector.
     */
    public Vector normalize() {
        return new Vector(this.scale(1 / this.length()).xyz);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector other && super.equals(other);

    }


    @Override
    public String toString() {
        return super.toString();
    }
}

