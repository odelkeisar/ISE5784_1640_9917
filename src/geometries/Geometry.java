package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Vector;
import primitives.Point;

/**
 * Represents a geometric shape in 3D space that can calculate its normal vector at a given point.
 */
public abstract class Geometry extends Intersectable {
    /**
     * The emission color of the geometry.
     */
    protected Color emission = Color.BLACK;
    private Material material = new Material();


    /**
     * Retrieves the emission color of the geometry.
     *
     * @return The emission color of the geometry.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Retrieves the material of the geometry.
     *
     * @return The material of the geometry.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The new emission color to set.
     * @return The current instance of the geometry (for method chaining).
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Sets the material of the geometry.
     *
     * @param material The new material to set.
     * @return The current instance of the geometry (for method chaining).
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Calculates the normal vector to the geometry at the specified point.
     *
     * @param p The point at which to calculate the normal vector.
     * @return The normal vector to the geometry at the given point.
     */

    public abstract Vector getNormal(Point p);
}
