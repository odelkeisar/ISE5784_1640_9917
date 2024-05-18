package geometries;

import primitives.*;

import static primitives.Util.isZero;

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
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();
        //t = v (P – P0)
        double t = p.subtract(p0).dotProduct(v);
        // O = P0 + t*v
        // if t is zero, the point is opposite the ray head, return the normalized vector from the point to the ray head
        if (!isZero(t))
        {
            Point o = p0.add(v.scale(t));
            return p.subtract(o).normalize();
        }
        else
            return p.subtract(p0).normalize();
    }
}
