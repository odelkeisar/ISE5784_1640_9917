package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a plane in 3D space defined by a point and a normal vector.
 */
public class Plane implements Geometry {


    private final Point p;

    private final Vector normal;

    /**
     * Constructs a new Plane using three points on the plane.
     *
     * @param p1 The first point on the plane.
     * @param p2 The second point on the plane.
     * @param p3 The third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.p = p1;
        Vector Vec1 = p3.subtract(p1);
        Vector Vec2 = p2.subtract(p1);
        this.normal =Vec2.crossProduct(Vec1).normalize();
    }

    /**
     * Constructs a new Plane with the specified base point and normal vector.
     *
     * @param p      The base point of the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point p, Vector normal) {
        this.p = p;
        this.normal = normal.normalize();
    }


    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return The normal vector of the plane.
     */
    public Vector getNormal() {
        return this.normal;
    }
}
