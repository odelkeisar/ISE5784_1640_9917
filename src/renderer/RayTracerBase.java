package renderer;
import primitives.Color;
import primitives.Ray;
import scene.Scene;
/**
 * The RayTracerBase class serves as an abstract base class for ray tracing operations.
 */
public abstract class RayTracerBase {

    protected Scene scene = null;

    /**
     * Constructs a RayTracerBase with the specified scene.
     * @param scene the {@link Scene} to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray in the scene and calculates the color at the intersection point.
     * @param ray The ray to be traced.
     * @return The color at the intersection point, or the background color if no intersection is found.
     */
    public abstract Color traceRay(Ray ray);
}
