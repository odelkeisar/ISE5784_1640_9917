package lighting;


import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents ambient light in a 3D scene.
 */
public class AmbientLight extends Light {


    /**
     * A static instance representing no ambient light.
     */
    public static AmbientLight NONE=new AmbientLight(Color.BLACK,0);

    /**
     * Constructs an AmbientLight object with the specified intensity and attenuation factor.
     *
     * @param Ia The intensity of the ambient light.
     * @param Ka The attenuation factor as a Double3.
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Constructs an AmbientLight object with the specified intensity and attenuation factor.
     *
     * @param Ia The intensity of the ambient light.
     * @param Ka The attenuation factor as a double.
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }


}
