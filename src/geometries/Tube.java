package geometries;

import primitives.*;
/**
 * Represents a tube in 3D space, extending RadialGeometry, with a specified radius and axis.
 */

public class Tube extends RadialGeometry {
    /**
     * The axis of the tube (a ray representing its direction).
     */
    protected final Ray axis;
    /**
     * Constructs a new Tube with the specified radius and axis.
     *
     * @param radius The radius of the tube.
     * @param axis   The axis of the tube (a ray representing its direction).
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }


    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
