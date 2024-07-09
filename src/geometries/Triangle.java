package geometries;
import primitives.*;

import java.util.List;
/**
 * Represents a triangle in 3D space, extending Polygon.
 */
/**
 * Represents a triangle in three-dimensional space, extending Polygon.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a new Triangle with the specified vertices.
     * @param p1 The first vertex of the triangle.
     * @param p2 The second vertex of the triangle.
     * @param p3 The third vertex of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * Finds the intersection points between the given ray and the triangle.
     *
     * @param ray the ray to check for intersections with the triangle.
     * @return a list containing the intersection points between the ray and the triangle.
     *         If there are no intersections, returns null.
     *         If the ray intersects the plane of the triangle but not the triangle itself, returns null.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {

        // Find intersection points with the plane of the triangle
        List<GeoPoint> result = plane.findGeoIntersections(ray,maxDistance);
        if (result == null) {
            return null;
        }

        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Vector v1 = p1.subtract(p0); // v1 = p1 - p0
        Vector v2 = p2.subtract(p0); // v2 = p2 - p0
        Vector v3 = p3.subtract(p0); // v3 = p3 - p0

        Vector n1 = v1.crossProduct(v2); // n1 = v1 x v2
        Vector n2 = v2.crossProduct(v3); // n2 = v2 x v3
        Vector n3 = v3.crossProduct(v1); // n3 = v3 x v1

        double s1 = n1.dotProduct(v); // s1 = n1 * v
        double s2 = n2.dotProduct(v); // s2 = n2 * v
        double s3 = n3.dotProduct(v); // s3 = n3 * v

        // Check if all signs are positive or all are negative
        if (s1 > 0 && s2 > 0 && s3 > 0 || s1 < 0 && s2 < 0 && s3 < 0)
            return result.stream().map(gp->new GeoPoint(this,gp.point)).toList();

        return null;
    }
}
