package renderer;

import geometries.Intersectable.GeoPoint;
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

    /**
     * Traces a ray through the scene and calculates the color based on intersections.
     *
     * @param ray The ray to trace.
     * @return The color calculated at the intersection point.
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
        if (geoPoints == null)
            return scene.background;
        return calcColor(ray.findClosestGeoPoint(geoPoints));
    }

    /**
     * Calculates the color at a given GeoPoint.
     *
     * @param geoPoint The GeoPoint at which to calculate the color.
     * @return The color at the given GeoPoint.
     */
    private Color calcColor(GeoPoint geoPoint) {
        return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
    }
}
