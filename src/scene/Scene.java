package scene;

import geometries.*;
import lighting.AmbientLight;
import primitives.*;

import java.awt.*;

/**
 * The Scene class represents a 3D scene containing geometries, ambient light, and a background color.
 */
public class Scene {
    public String name;
    public primitives.Color background = new primitives.Color(java.awt.Color.BLACK);
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();

    /**
     * Constructs a Scene with the specified name.
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background the background color as a {@link primitives.Color} object
     * @return the current Scene object for chaining
     */
    public Scene setBackground(primitives.Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the ambient light as an {@link lighting.AmbientLight} object
     * @return the current Scene object for chaining
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries the geometries as a {@link geometries.Geometries} object
     * @return the current Scene object for chaining
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}