package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * The DirectionalLight class represents a light source that has a fixed direction and infinite distance.
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * Constructs a DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity the intensity of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Returns the intensity of the light at a given point.
     * For directional light, the intensity is the same at all points.
     *
     * @param point the point at which to calculate the light intensity
     * @return the intensity of the light
     */
    @Override
    public Color getIntensity(Point point) {
        return super.getIntensity();
    }

    /**
     * Returns the direction of the light at a given point.
     * For directional light, the direction is the same at all points.
     *
     * @param point the point at which to calculate the light direction
     * @return the direction of the light
     */
    @Override
    public Vector getL(Point point) {
        return direction;
    }

    /**
     * Returns the distance from the light source to a given point.
     * For directional light, the distance is considered to be infinite.
     *
     * @param point the point at which to calculate the distance
     * @return Double.POSITIVE_INFINITY since the light source is considered to be at an infinite distance
     */
    @Override
    public double getDistance(Point point){
        return Double.POSITIVE_INFINITY;
    }
    @Override
    public List<Vector> getBeamL(Point point, int countBeam){
        return List.of(new Vector(new Double3(direction.getX(),direction.getY(),direction.getZ())));

    }
}
