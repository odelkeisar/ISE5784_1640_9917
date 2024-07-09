package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable interface defines a contract for geometrical objects
 * that can be intersected by a ray. Any class implementing this interface
 * should provide an implementation for finding the intersections between
 * a given ray and the geometry.
 */
public abstract class Intersectable {
    /**
     * A helper class to represent a point on a geometry along with the geometry itself.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructs a new GeoPoint with the specified geometry and point.
         *
         * @param geometry The geometry on which the point lies.
         * @param point    The specific point on the geometry.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * Checks if this GeoPoint is equal to another object.
         * Two GeoPoints are considered equal if they have the same geometry and the same point.
         *
         * @param object The object to compare with this GeoPoint.
         * @return true if the specified object is equal to this GeoPoint, false otherwise.
         */
        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) object;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        /**
         * Returns a string representation of this GeoPoint.
         * The string representation includes the geometry and the point of this GeoPoint.
         *
         * @return A string representation of this GeoPoint.
         */
        @Override
        public String toString() {
            return "GeoPoint: " +
                    "geometry= " + geometry +
                    ", point= " + point +
                    '.';
        }

    }

    /**
     * Finds all the intersection points between the given ray and the geometry.
     *
     * @param ray the ray to check for intersections with the geometry.
     * @return a list of intersection points where the ray intersects the geometry.
     * If there are no intersections, an empty list is returned.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds all the GeoPoints where the given ray intersects the geometry.
     *
     * @param ray the ray to check for intersections with the geometry.
     * @return a list of GeoPoints where the ray intersects the geometry.
     * If there are no intersections, returns null.
     */
   /** public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }*/

    /**
     * Helper method to find all the GeoPoints where the given ray intersects the geometry.
     * This method should be implemented by subclasses to provide the actual intersection logic.
     *
     * @param ray the ray to check for intersections with the geometry.
     * @return a list of GeoPoints where the ray intersects the geometry.
     * If there are no intersections, returns null.
     */
    //protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
    /**
     * find all GeoPoints that intersect with a ray
     *
     * @param ray to find intersections with
     * @return list of intersection points
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
   }

    /**
     * find all GeoPoints that intersect with a ray limited by max distance
     *
     * @param ray         to find intersections with
     * @param maxDistance the given distance
     * @return list of intersection points
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * helper for the findGeoIntersections function that finds all points in a
     * geometry that intersect with a ray while ignoring the points that are further
     * than a given distance
     *
     * @param ray         to find intersections with
     * @param maxDistance the given distance
     * @return list of intersection points
     */
    abstract protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

}
