package geometries;

import primitives.*;

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
        this.height = height;
    }


    public Vector getNormal(Point p) {
        return null;
    }
}
