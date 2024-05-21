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
    public Vector getNormal(Point point) {

        // Calculate the top and bottom points of the cylinder
        Point p1 = axis.getHead().add(axis.getDirection().scale(height));
        // Check if the point is on the top or bottom base of the cylinder
        if ((point.distance(axis.getHead()) < radius) || (point.distance(p1)) < radius)
            return axis.getDirection().normalize();

       //Calculation of the normal to the point on the side of the cylinder:
        return super.getNormal(point);
    }
}
