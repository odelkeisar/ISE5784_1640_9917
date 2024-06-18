package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * SimpleRayTracer is a basic implementation of RayTracerBase that traces rays
 * through a scene and calculates the color based on intersections with objects in the scene.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructs a SimpleRayTracer with the specified scene.
     *
     * @param scene
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }


    @Override
    public Color traceRay(Ray ray) {
        List<Point> points = scene.geometries.findIntersections(ray);
        if (points == null)
            return scene.background;
        return calcColor(ray.findClosestPoint(points));
    }

    /**
     * Calculates the color at a given point.
     *
     * @param point the point to calculate
     * @return the color of the point
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
