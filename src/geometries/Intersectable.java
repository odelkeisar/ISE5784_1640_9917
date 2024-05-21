package geometries;

import primitives.*;

import java.util.List;

/**
 * The Intersectable interface defines a contract for geometrical objects
 * that can be intersected by a ray. Any class implementing this interface
 * should provide an implementation for finding the intersections between
 * a given ray and the geometry.
 */
public interface Intersectable {

    /**
     * Finds all the intersection points between the given ray and the geometry.
     *
     * @param ray the ray to check for intersections with the geometry.
     * @return a list of intersection points where the ray intersects the geometry.
     *         If there are no intersections, an empty list is returned.
     */
    List<Point> findIntersections(Ray ray);
}
