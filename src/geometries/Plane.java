package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
        this.normal = Vec2.crossProduct(Vec1).normalize();
    }

    /**
     * Constructs a new Plane with the specified base point and normal vector.
     *
     * @param point  The base point of the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point point, Vector normal) {
        this.p = point;
        this.normal = normal.normalize();
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

    /**
     * Finds the intersection points between the given ray and the plane.
     *
     * @param ray the ray to check for intersections with the plane.
     * @return a list containing the intersection point between the ray and the plane.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        // Check if the ray starts from a point on the plane
        if (p.equals(p0)) {
            return null;
        }

        // Calculate the numerator of the t parameter (n * (p - p0))
        double t_numerator = alignZero(normal.dotProduct(p.subtract(p0)));
        if (isZero(t_numerator)) {
            return null;
        }

        // Calculate the denominator of the t parameter (n * v)
        double t_denominator = alignZero(normal.dotProduct(v));
        if (isZero(t_denominator)) {
            return null;
        }

        // Calculate the t parameter (t = (n * (p - p0)) / (n * v))
        double t = alignZero(t_numerator / t_denominator);
        if (t <= 0) {
            return null;
        }

        // Calculate the intersection point (p = p0 + t * v)
        return List.of(ray.getPoint(t));
    }

}
