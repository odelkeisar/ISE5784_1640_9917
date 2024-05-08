package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
    private final Point p;
    private final Vector normal;

    public Plane(Point p1, Point p2, Point p3) {
        p = p1;
        normal = null;
    }

    public Plane(Point _p, Vector _normal) {
        p = _p;
        normal = _normal.normalize();
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    public Vector getNormal() {
        return normal;
    }
}
