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
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Constructs a SimpleRayTracer with the specified scene.
     *
     * @param scene
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    public SimpleRayTracer(Scene scene, boolean antiA, boolean softShadows) {
        super(scene, antiA, softShadows);
    }

    public SimpleRayTracer setAntiA(boolean antiA) {
        this.antiA = antiA;
        return this;
    }

    public SimpleRayTracer setSoftShadows(boolean softShadows) {
        this.softShadows = softShadows;
        return this;
    }

    public SimpleRayTracer setCountBeam(int countBeam){
        this.countBeam=countBeam;
        return this;
    }

    /**
     * Traces a ray through the scene and calculates the color based on intersections.
     *
     * @param ray The ray to trace.
     * @return The color calculated at the intersection point.
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestGeoPoint = findClosestIntersection(ray);
        return closestGeoPoint == null ? scene.background : calcColor(closestGeoPoint, ray);
    }

    public Color traceBeamRay(List<Ray> rays) {
        Color color = Color.BLACK;
        for (Ray ray : rays) {
            Color temp = traceRay(ray);
            color = color.add(temp.scale(1.0 / rays.size()));
        }
        return color;
    }

    /**
     * Calculates the color at a given GeoPoint considering local and global effects.
     *
     * @param geoPoint The GeoPoint to calculate the color at.
     * @param ray      The ray that intersects with the geometry.
     * @return The calculated color.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {

        // Calculating the color at a point according to Phong Reflection Model
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Recursively calculates the color at a given GeoPoint considering local and global effects.
     *
     * @param geoPoint The GeoPoint to calculate the color at.
     * @param ray      The ray that intersects with the geometry.
     * @param level    The recursion level.
     * @param k        The attenuation factor.
     * @return The calculated color.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {

        Color color = calcLocalEffects(geoPoint, ray, k);

        return level == 1 ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Calculates the local effects of light at a given GeoPoint.
     *
     * @param geoPoint The GeoPoint at which to calculate the effects.
     * @param ray      The ray that intersects with the geometry.
     * @param k        The attenuation factor.
     * @return The color contribution from local effects.
     */

    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {

        Color color = geoPoint.geometry.getEmission();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv))
            return color;

        Material material = geoPoint.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Double3 ktr;
                if(softShadows) //if we want soft shadows
                    ktr = transparencyBeam(geoPoint,lightSource, n,nv);
                else
                    ktr = transparency(geoPoint, lightSource, l, n,nv);

                //Double3 ktr = transparency(geoPoint, lightSource, l, n, nv);
                if (ktr.greaterThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }


    /**
     * Calculates the global effects (reflection and refraction) at a given GeoPoint.
     *
     * @param gp    The GeoPoint to calculate the global effects at.
     * @param ray   The ray that intersects with the geometry.
     * @param level The recursion level.
     * @param k     The attenuation factor.
     * @return The color contribution from global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {

        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();


        return calcGlobalEffect(constructReflectedRay(gp.point, ray, n), material.kR, level, k)
                .add(calcGlobalEffect(constructRefractedRay(gp.point, ray, n), material.kT, level, k));
    }

    /**
     * Helper method to calculate the global effect of a single ray (either reflected or refracted).
     *
     * @param ray   The ray to trace.
     * @param kx    The reflection or refraction coefficient.
     * @param level The recursion level.
     * @param k     The attenuation factor.
     * @return The calculated color.
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    /**
     * Finds the closest intersection of a ray with the scene's geometries.
     *
     * @param ray The ray to find intersections for.
     * @return The closest intersection point, or null if no intersections are found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);

    }

    /**
     * Constructs a reflected ray from a point using the normal vector.
     *
     * @param point The point of intersection.
     * @param ray   The incoming ray.
     * @param n     The normal vector at the point of intersection.
     * @return The constructed reflected ray.
     */
    private Ray constructReflectedRay(Point point, Ray ray, Vector n) {
        Vector v = ray.getDirection();
        double vn = alignZero(v.dotProduct(n)); //v*n

        if (isZero(vn)) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn)).normalize();// n*2*vn
        return new Ray(point, r, n); //new Ray{point,v-2*(v*n)*n}
    }

    /**
     * Constructs a refracted ray from a point using the normal vector.
     *
     * @param point The point of intersection.
     * @param ray   The incoming ray.
     * @param n     The normal vector at the point of intersection.
     * @return The constructed refracted ray.
     */
    private Ray constructRefractedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, ray.getDirection(), n);
    }

    /**
     * Calculates the diffuse reflection color based on the material and angle.
     *
     * @param material The material of the geometry.
     * @param nl       The cosine of the angle between the normal and the light direction.
     * @return The diffuse reflection color.
     */

    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(nl > 0 ? nl : -nl);
    }

    /**
     * Calculates the specular reflection color based on the material, normal, light direction, and view direction.
     *
     * @param material The material of the geometry.
     * @param n        The normal vector to the geometry at the intersection point.
     * @param l        The direction vector from the intersection point to the light source.
     * @param nl       The cosine of the angle between the normal and the light direction.
     * @param v        The direction vector of the incoming ray.
     * @return The specular reflection color.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl)).normalize();
        double coefficient = -alignZero(v.dotProduct(r));
        coefficient = coefficient > 0 ? coefficient : 0;
        return material.kS.scale(Math.pow(coefficient, material.nShininess));
    }

    /**
     * Checks if a point is unshaded by other geometries.
     *
     * @param geoPoint    The GeoPoint to check.
     * @param l           The direction vector from the point to the light source.
     * @param n           The normal vector at the GeoPoint.
     * @param nv          The dot product of the normal vector and the view direction.
     * @param lightSource The light source to check against.
     * @return True if the point is unshaded, false otherwise.
     */
    private boolean unshaded(GeoPoint geoPoint, Vector l, Vector n, double nv, LightSource lightSource) {
        Vector lightDirection = l.scale(-1);
        Vector deltaVector = n.scale(nv < 0 ? DELTA : -DELTA);
        Point point = geoPoint.point.add(deltaVector);
        Ray ray = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return true;
        double lightDistance = lightSource.getDistance(geoPoint.point);
        for (GeoPoint intersection : intersections) {
            if (intersection.point.distance(geoPoint.point) < lightDistance
                    && intersection.geometry.getMaterial().kT == Double3.ZERO)
                return false;
        }

        return true;
    }

    /**
     * Calculates the transparency factor (ktr) of a GeoPoint.
     *
     * @param geoPoint The GeoPoint to calculate the transparency factor at.
     * @param light    The light source.
     * @param l        The direction vector from the point to the light source.
     * @param n        The normal vector at the GeoPoint.
     * @param nv       The dot product of the normal vector and the view direction.
     * @return The transparency factor (ktr).
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource light, Vector l, Vector n, double nv) {
        //from point to light source
        Vector lightDirection = l.scale(-1);

        // Refactored ray head move
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double maxDistance = light.getDistance(geoPoint.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, maxDistance);

        //if there are no intersections return true (there is no shadow)
        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;

        //for each intersection
        for (GeoPoint intersection : intersections) {
            ktr = ktr.product(intersection.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        return ktr;
    }

    private Double3 transparencyBeam(GeoPoint geoPoint, LightSource lightSource, Vector n, double nv) {
        Double3 tempKtr = Double3.ZERO;
        List<Vector> beamL = lightSource.getBeamL(geoPoint.point,countBeam);

        for (Vector vl : beamL) {
            tempKtr = tempKtr.add(transparency(geoPoint,lightSource,vl,n,nv));
        }
        tempKtr = tempKtr.reduce(beamL.size());

        return tempKtr;
    }
}
