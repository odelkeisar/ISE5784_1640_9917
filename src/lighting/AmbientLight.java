package lighting;


import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents ambient light in a 3D scene.
 */
public class AmbientLight {

    private final Color intensity;
    /**
     * A static instance representing no ambient light.
     */
    public static AmbientLight NONE=new AmbientLight(Color.BLACK,0);

    /**
     * Constructs an AmbientLight with a given intensity and attenuation factor.
     * @param Ia the intensity of the ambient light as a Color.
     * @param Ka the attenuation factor as a Double3.
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        intensity=Ia.scale(Ka);
    }

    /**
     * Constructs an AmbientLight with a given intensity and attenuation factor.
     * @param Ia the intensity of the ambient light as a Color.
     * @param Ka the attenuation factor as a double.
     */
    public AmbientLight(Color Ia, double Ka) {
        intensity=Ia.scale(Ka);
    }
    public Color getIntensity(){return intensity;}
}
