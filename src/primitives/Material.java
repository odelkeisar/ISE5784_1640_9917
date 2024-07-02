package primitives;
/**
 * The Material class represents the material properties of an object in a 3D scene.
 */
public class Material {
    public Double3 kD=Double3.ZERO;
    public Double3 kS=Double3.ZERO;
    public int nShininess=0;

    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD the diffuse reflection coefficient as a {@link Double3} vector
     * @return the current Material object for chaining
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient of the material from a scalar value.
     *
     * @param kD the scalar value for diffuse reflection coefficient
     * @return the current Material object for chaining
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS the specular reflection coefficient as a {@link Double3} vector
     * @return the current Material object for chaining
     */
    public Material setKs(Double3 kS){
        this.kS=kS;
        return this;
    }
    /**
     * Sets the specular reflection coefficient of the material from a scalar value.
     *
     * @param kS the scalar value for specular reflection coefficient
     * @return the current Material object for chaining
     */
    public Material setKs(double kS){
        this.kS=new Double3(kS);
        return this;
    }
    /**
     * Sets the shininess exponent of the material.
     *
     * @param nShininess the shininess exponent
     * @return the current Material object for chaining
     */
    public Material setShininess(int nShininess){
        this.nShininess=nShininess;
        return this;
    }
}
