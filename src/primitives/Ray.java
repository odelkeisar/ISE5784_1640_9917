package primitives;

import java.util.List;

import static primitives.Util.isZero;

import geometries.Intersectable.GeoPoint;

/**
 * Represents a ray in 3D space, defined by a head point and a direction vector.
 */
public class Ray {


    private final Point head;

    private final Vector direction;

    /**
     * Constructs a new Ray with the specified head point and direction vector.
     *
     * @param head      The head point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * returns the head point of the ray
     *
     * @return the head
     */
    public Point getHead() {
        return head;
    }

    /**
     * returns the vector(direction) of the ray
     *
     * @return direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * @param t is a scalar
     * @return A point on the beam at a given distance : p0+t*v
     */
    public Point getPoint(double t) {
        if (isZero(t))
            return head;
        return head.add(direction.scale(t));
    }

    /**
     * Finds the closest point to the ray's head point from a list of points.
     *
     * @param points The list of {@link Point} objects to search through.
     * @return The closest {@link Point} from the list to the ray's head point;
     * returns null if the list is null or empty.
     */
    public Point findClosestPoint(List<Point> points) {
        return
                points == null || points.isEmpty() ? null
                        : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closest GeoPoint to the ray's head point from a list of GeoPoints.
     *
     * @param geoPoints The list of {@link GeoPoint} objects to search through.
     * @return The closest {@link GeoPoint} from the list to the ray's head point;
     * returns null if the list is null or empty.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        if (geoPoints == null || geoPoints.isEmpty())
            return null;
        double distanceMin = Double.MAX_VALUE;
        GeoPoint closestGeoPoint
                = null;

        for (GeoPoint geoPoint : geoPoints) {
            double distance = head.distance(geoPoint.point);
            if (distance < distanceMin) {
                distanceMin = distance;
                closestGeoPoint = geoPoint;
            }
        }
        return closestGeoPoint;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }


    @Override
    public String toString() {
        return "head: " + head.toString() + "direction: " + direction.toString();
    }
}
