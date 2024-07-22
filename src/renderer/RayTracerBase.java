package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

/**
 * The RayTracerBase class serves as an abstract base class for ray tracing operations.
 */
public abstract class RayTracerBase {

    protected Scene scene = null;
    protected boolean softShadows = false;
    protected int countBeam = 100;

    /**
     * Constructs a RayTracerBase with the specified scene.
     *
     * @param scene the {@link Scene} to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    public RayTracerBase(Scene scene, boolean softShadows) {
        this.scene = scene;
        this.softShadows = softShadows;
    }

    public RayTracerBase setSoftShadows(boolean softShadows) {
        this.softShadows = softShadows;
        return this;
    }

    public RayTracerBase setCountBeam(int countBeam) {
        this.countBeam = countBeam;
        return this;
    }


    /**
     * Traces a ray in the scene and calculates the color at the intersection point.
     *
     * @param ray The ray to be traced.
     * @return The color at the intersection point, or the background color if no intersection is found.
     */
    public abstract Color traceRay(Ray ray);

    /**
     * Traces a beam of rays through the scene and calculates the resulting color.
     *
     * @param rays The list of rays in the beam.
     * @return The color calculated from tracing the beam of rays.
     */
    public abstract Color traceBeamRay(List<Ray> rays);

    /**
     * Checks if soft shadows are enabled.
     *
     * @return true if soft shadows are enabled, false otherwise
     */
    public boolean isSoftShadows() {
        return softShadows;
    }
    }
