package lighting;

import primitives.*;

import java.util.List;

public interface LightSource {

    /**
     * Returns the intensity of the light at a given point.
     *
     * @param p The point at which to calculate the light intensity.
     * @return The intensity of the light at the given point.
     */
    public Color getIntensity(Point p);
    /**
     * Returns the direction of the light at given point
     * @param point the point
     * @return Vector object
     */
    public Vector getL(Point point);
    public List<Vector> getBeamL(Point point, int countBeam);
    /**
     * Returns the distance from the light source to a given point.
     *
     * @param point The point at which to calculate the distance.
     * @return The distance from the light source to the given point.
     */
    public double getDistance(Point point);

}
