package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {
    private Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    @Override
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    @Override
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }
    public SpotLight setRadius(double radius){
        this.radius=radius;
        return this;
    }
    @Override
    public Color getIntensity(Point point) {
        double max = Math.max(0, direction.dotProduct(getL(point)));
        return super.getIntensity(point).scale(max);
    }
}
