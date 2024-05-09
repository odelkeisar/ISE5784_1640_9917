package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * Represents a geometric shape in 3D space that can calculate its normal vector at a given point.
 */
public interface Geometry {

    /**
     * Calculates the normal vector to the geometry at the specified point.
     *
     * @param p The point at which to calculate the normal vector.
     * @return The normal vector to the geometry at the given point.
     */
    public Vector getNormal(Point p);
}
