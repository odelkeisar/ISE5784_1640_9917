package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The PointLight class represents a point light source in a 3D scene.
 * A point light source emits light in all directions from a specific position in space.
 */
public class PointLight extends Light implements LightSource {
    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * Constructs a PointLight object with the specified intensity and position.
     *
     * @param intensity the intensity of the light
     * @param position the position of the light source
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor
     * @return the current PointLight object for chaining
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor
     * @return the current PointLight object for chaining
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor
     * @return the current PointLight object for chaining
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Returns the intensity of the light at a given point.
     * The intensity is calculated based on the distance from the light source
     * and the attenuation factors.
     *
     * @param point the point at which to calculate the light intensity
     * @return the intensity of the light at the given point
     */
    @Override
    public Color getIntensity(Point point) {
        double distance = position.distance(point);
        double factor = kC + kL * distance + kQ * distance * distance;
        return super.getIntensity().scale(1 / factor);
    }

    /**
     * Returns the direction of the light at a given point.
     *
     * @param point the point at which to calculate the light direction
     * @return the direction of the light at the given point, or null if the point is at the light source position
     */
    @Override
    public Vector getL(Point point) {
        if (point.equals(position))
            return null;
        return point.subtract(position).normalize();
    }

    /**
     * Returns the distance from the light source to a given point.
     *
     * @param point the point at which to calculate the distance
     * @return the distance from the light source to the given point
     */
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
