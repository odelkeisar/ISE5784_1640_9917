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
        p = p1; // Assuming p1, p2, p3 are not collinear
        normal = null; // Calculate normal from p1, p2, p3
    }

    /**
     * Constructs a new Plane with the specified base point and normal vector.
     *
     * @param _p      The base point of the plane.
     * @param _normal The normal vector to the plane.
     */
    public Plane(Point _p, Vector _normal) {
        p = _p;
        normal = _normal.normalize();
    }


    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return The normal vector of the plane.
     */
    public Vector getNormal() {
        return normal;
    }
}
