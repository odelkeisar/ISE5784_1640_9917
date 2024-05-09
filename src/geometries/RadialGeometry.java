package geometries;

/**
 * Represents a radial geometric shape in 3D space, extending Geometry, with a specified radius.
 */
public abstract class RadialGeometry implements Geometry {

    /**
     * The radius of the radial geometry.
     */
    protected double radius;

    /**
     * Constructs a new RadialGeometry with the specified radius.
     *
     * @param radius The radius of the radial geometry.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
