package lighting;

import primitives.Color;

/**
 * The abstract class Light represents a light source with an intensity.
 * Subclasses of Light should define specific types of light sources.
 */
abstract class Light {
    /**
     * The intensity of the light source.
     */
    protected Color intensity;

    /**
     * Constructs a Light object with the specified intensity.
     *
     * @param intensity The intensity of the light source.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light source.
     *
     * @return The intensity of the light source.
     */
    public Color getIntensity() {
        return intensity;
    }
}
