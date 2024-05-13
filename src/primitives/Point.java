package primitives;

/**
 * Represents a point in three-dimensional space.
 */
public class Point {

    /**
     * Represents the zero point.
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    protected final Double3 xyz;

    /**
     * Constructs a new Point with the given coordinates.
     *
     * @param xyz The coordinates of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Constructs a new Point with the given x, y, and z coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param z The z-coordinate.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Subtracts another point from this point, returning the resulting vector.
     *
     * @param p1 The point to subtract.
     * @return The vector representing the subtraction result.
     */
    public Vector subtract(Point p1) {
        return new Vector(this.xyz.subtract(p1.xyz));
    }

    /**
     * Adds a vector to this point, returning the resulting point.
     *
     * @param v1 The vector to add.
     * @return The resulting point after addition.
     */
    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    /**
     * Calculates the squared distance between this point and another point.
     *
     * @param p1 The other point.
     * @return The squared distance between the points.
     */
    public double distanceSquared(Point p1) {
        double dx = this.xyz.d1 - p1.xyz.d1;
        double dy = this.xyz.d2 - p1.xyz.d2;
        double dz = this.xyz.d3 - p1.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param p1 The other point.
     * @return The distance between the points.
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }


    @Override
    public String toString() {
        return xyz.toString();
    }
}
