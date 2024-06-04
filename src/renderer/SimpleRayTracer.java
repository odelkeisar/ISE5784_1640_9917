package renderer;
import primitives.Color;
import primitives.Point;
import primitives.Ray;

import java.util.List;

public class SimpleRayTracer extends RayTracerBase {

    public SimpleRayTracer(scene.Scene scene) {
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
