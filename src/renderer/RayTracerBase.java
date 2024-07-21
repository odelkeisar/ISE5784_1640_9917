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

//    /**
//     * Abstract method for adaptive super-sampling.
//     * This method is intended to be overridden by subclasses to implement the adaptive super-sampling algorithm.
//     * The algorithm recursively divides a given segment into smaller sub-segments to determine the color by tracing
//     * rays through the scene. The goal is to optimize rendering by reducing the number of rays traced while maintaining
//     * image quality.
//     *
//     * @param centerP    The center point of the current segment.
//     * @param Width      The width of the current segment.
//     * @param Height     The height of the current segment.
//     * @param minWidth   The minimum width for a segment to stop further subdivision.
//     * @param minHeight  The minimum height for a segment to stop further subdivision.
//     * @param cameraLoc  The location of the camera.
//     * @param vRight     The right vector from the camera.
//     * @param Vup        The up vector from the camera.
//     * @param prePoints  The list of previously processed points to avoid redundant calculations.
//     * @return The color of the segment after adaptive super-sampling.
//     */
//    public abstract Color AdaptiveSuperSamplingHelper(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector vRight, Vector Vup, List<Point> prePoints);

    }
