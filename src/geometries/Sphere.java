package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Represents a sphere in 3D space, extending RadialGeometry, with a specified center point and radius.
 */
public class Sphere extends RadialGeometry {


    private final Point center;

    /**
     * Constructs a new Sphere with the specified center point and radius.
     *
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Calculates the normal vector to the sphere at the specified point.
     *
     * @param point The point at which to calculate the normal.
     * @return The normal vector to the sphere at the given point .
     */
    @Override
    public Vector getNormal(Point point) {

        return (point.subtract(center)).normalize();
    }

    /**
     * Finds the intersection points between the given ray and the sphere.
     *
     * @param ray the ray to check for intersections with the sphere.
     * @return a list containing the intersection points between the ray and the sphere.
     *         If there are no intersections, returns null.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        Point head = ray.getHead();
        Vector direction = ray.getDirection();

        // If the ray starts from the center of the sphere
        if (head.equals(center)) {
            //center+(radius*direction)
            return List.of(new GeoPoint(this,ray.getPoint(radius)));
        }

        Vector u = center.subtract(head);
        //Tm=v*u
        double Tm=alignZero(direction.dotProduct(u));

        //d=squrt(|u|^2-tm^2)
        double d=alignZero(Math.sqrt(u.lengthSquared()-(Tm*Tm)));
        //Th=squrt(r^2-d^2)
        double Th=alignZero(Math.sqrt((radius*radius)-(d*d)));

        //the ray out of sphere
        if(radius<=d)
           return null;

        double t1=alignZero(Tm+Th);
        double t2=alignZero(Tm-Th);

        // Find the intersection points if t values are positive
        if (t1 > 0 && t2 > 0) {
            Point p1=ray.getPoint(t1); //p1=head+t1*v
            Point p2=ray.getPoint(t2); //p2=head+t2*v
            List<GeoPoint> list = null;

            double dis = p1.distance(ray.getHead());
            if (alignZero(dis - maxDistance) <= 0) { //if the distance is less than the maximum
                list = new LinkedList<>();
                list.add(new GeoPoint(this, p1));
            }

            dis = p2.distance(ray.getHead());
            if (alignZero(dis - maxDistance) <= 0) { //if the distance is less than the maximum
                if (list == null) list = new LinkedList<>();
                list.add(new GeoPoint(this, p2));

            }

            return list==null?null: list.stream().sorted(Comparator.comparingDouble(p -> p.point.distance(head)))
                    .toList();
            //return (List<GeoPoint>) List.of(new GeoPoint(this,p1),new GeoPoint(this,p2) ).stream()
               //     .sorted(Comparator.comparingDouble(p -> p.point.distance(head)))
               //     .toList();
        }
        if (t1 > 0 ) {
            Point p1=ray.getPoint(t1); //p1=head+t1*v
            double dis = p1.distance(ray.getHead());

            if (alignZero(dis - maxDistance) <= 0) { //if the distance is less than the maximum
                return List.of(new GeoPoint(this, p1));
            }
        }
        if (t2 >0 ) {
            Point p2=ray.getPoint(t2); //p2=head+t2*v
            double dis = p2.distance(ray.getHead());
            if (alignZero(dis - maxDistance) <= 0) { //if the distance is less than the maximum
                return List.of(new GeoPoint(this, p2));
            }
        }
        return null;
    }
}
