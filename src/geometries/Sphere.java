package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in 3D space, extending RadialGeometry, with a specified center point and radius.
 */
public class Sphere extends RadialGeometry {


    private final Point center;

    /**
     * Constructs a new Sphere with the specified center point and radius.
     *
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Calculates the normal vector to the sphere at the specified point.
     *
     * @param p The point at which to calculate the normal.
     * @return The normal vector to the sphere at the given point .
     */
    @Override
    public Vector getNormal(Point p) {

        return (p.subtract(center)).normalize();
    }
}
