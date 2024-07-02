package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
        GeoPoint closestGeoPoint=ray.findClosestGeoPoint(geoPoints);
        return calcColor(closestGeoPoint,ray);
    }

    /**
     * Calculates the color at a given GeoPoint.
     *
     * @param geoPoint The GeoPoint at which to calculate the color.
     * @return The color at the given GeoPoint.
     */
    private Color calcColor(GeoPoint geoPoint,Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * Calculates the local effects of light at a given GeoPoint.
     *
     * @param geoPoint The GeoPoint at which to calculate the effects.
     * @param ray      The ray that intersects with the geometry.
     * @return The color contribution from local effects.
     */

    private  Color calcLocalEffects(GeoPoint geoPoint, Ray ray){

        Color color = geoPoint.geometry.getEmission();
        Vector n= geoPoint.geometry.getNormal(geoPoint.point);
        Vector v=ray.getDirection();
        double nv=alignZero(n.dotProduct(v));
        if(isZero(nv)) return color;

        Material material=geoPoint.geometry.getMaterial();
        for(LightSource lightSource: scene.lights) {
            Vector l=lightSource.getL(geoPoint.point);
            double nl=alignZero(n.dotProduct(l));
            if(nl*nv>0){
                Color iL=lightSource.getIntensity(geoPoint.point);
                color=color.add(iL.scale(calcDiffusive(material,nl).add(calcSpecular(material,n,l,nl,v))));
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse reflection color based on the material and angle.
     *
     * @param material  The material of the geometry.
     * @param nl  The cosine of the angle between the normal and the light direction.
     * @return The diffuse reflection color.
     */

    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(nl > 0 ? nl : -nl);
    }
    /**
     * Calculates the specular reflection color based on the material, normal, light direction, and view direction.
     *
     * @param material  The material of the geometry.
     * @param n    The normal vector to the geometry at the intersection point.
     * @param l  The direction vector from the intersection point to the light source.
     * @param nl  The cosine of the angle between the normal and the light direction.
     * @param v    The direction vector of the incoming ray.
     * @return The specular reflection color.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl)).normalize();
        double coefficient = -alignZero(v.dotProduct(r));
        coefficient = coefficient > 0 ? coefficient : 0;
        return material.kS.scale(Math.pow(coefficient, material.nShininess));
    }
}
