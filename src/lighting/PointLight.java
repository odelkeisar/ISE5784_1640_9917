package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

/**
 * The PointLight class represents a point light source in a 3D scene.
 * A point light source emits light in all directions from a specific position in space.
 */
public class PointLight extends Light implements LightSource {
    private static final Random RND = new Random();
    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;
    protected double radius = 12;

    /**
     * Constructs a PointLight object with the specified intensity and position.
     *
     * @param intensity the intensity of the light
     * @param position  the position of the light source
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

    public PointLight setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public double getRadius() {
        return radius;
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
     * Returns the distance from the light source to a given point.
     *
     * @param point the point at which to calculate the distance
     * @return the distance from the light source to the given point
     */
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
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
     * Generates a list of light beam directions from the light source to the given point.
     * The beams are randomly distributed within a circle centered at the light source.
     *
     * @param point     the target point to which the beams are directed
     * @param countBeam the number of beams to generate
     * @return a list of vectors representing the directions of the beams
     */
    @Override
    public List<Vector> getBeamL(Point point, int countBeam) {

        if (point.equals(position))
            return null;

        LinkedList<Vector> beam = new LinkedList<>();

        // From point light position to p point
        Vector v = this.getL(point);
        beam.add(v);
        if (countBeam <= 1)
            return beam;

        Vector normX, normY;

        // Determine two orthogonal vectors in the plane perpendicular to v
        if (isZero(v.getX()) && isZero(v.getY()))
            normX = new Vector(v.getZ() * -1, 0, 0).normalize();
        else
            normX = new Vector(v.getY() * -1, v.getX(), 0).normalize();

        normY = v.crossProduct(normX).normalize();

        // Generate additional beam directions within the specified radius
        for (int counter = 0; counter < countBeam; counter++) {
            double angle = 2 * Math.PI * RND.nextDouble();  // Random angle
            double r = radius * Math.sqrt(RND.nextDouble());  // Random radius within the circle

            double x = r * Math.cos(angle);  // X coordinate
            double y = r * Math.sin(angle);  // Y coordinate

            Vector offset = normX.scale(x).add(normY.scale(y));
            Point newPoint = position.add(offset); // New point within the radius

            beam.add(point.subtract(newPoint).normalize());
        }
        return beam;
    }
}
