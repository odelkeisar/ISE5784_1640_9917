package geometries;


import primitives.*;

public class Cylinder extends Tube {

    private final double height;

    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    public Vector getNormal(Point p) {

        return null;
    }
}
