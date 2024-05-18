package geometries;

import primitives.*;

import static primitives.Util.isZero;

/**
 * Represents a cylinder in 3D space, extending from a tube along its axis with a specified height.
 */
public class Cylinder extends Tube {


    private final double height;

    /**
     * Constructs a new Cylinder with the specified radius, axis, and height.
     *
     * @param radius The radius of the cylinder.
     * @param axis   The axis of the cylinder (a ray representing its direction).
     * @param height The height of the cylinder.
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis); // Calls the constructor of the superclass Tube
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0.");
        }
        this.height = height;
    }


    @Override
    public Vector getNormal(Point p) {

        // Calculate the top and bottom points of the cylinder
        Point p1=axis.getHead().add(axis.getDirection().scale(height));
       // Check if the point is on the top or bottom base of the cylinder
        if ((p.distance(axis.getHead()) < radius) || (p.distance(p1)) < radius)
            return axis.getDirection().normalize();

        Point p0 = axis.getHead();
        Vector v = axis.getDirection();
        //t = v (P – P0)
        double t = p.subtract(p0).dotProduct(v);
        // O = P0 + tv
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
