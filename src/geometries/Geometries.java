package geometries;
import primitives.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a collection of intersectable geometries.
 */
public class Geometries extends Intersectable{

    protected List<Intersectable> geometries =new LinkedList<>();

    /**
     * default constructor
     */
    public Geometries() {}

    /**
     * Constructor that adds given geometries to the collection.
     *
     * @param geometries The intersectable geometries to add.
     */
    public Geometries(Intersectable... geometries){
      add(geometries);
    }

    /**
     * Adds the given geometries to the collection.
     *
     * @param geometries The geometries to add.
     */
    public void add(Intersectable... geometries){
        Collections.addAll(this.geometries,geometries);
    }

    /**
     * Finds all the intersection points between the given ray and all geometries in the collection.
     *
     * @param ray the ray to check for intersections with the geometries.
     * @return a list containing all the intersection points between the ray and the geometries.
     *         If there are no intersections, returns null.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        List<GeoPoint> intersectionsResult = null;
        for (Intersectable geometry : geometries)
        {
            var intersections = geometry.findGeoIntersections(ray,maxDistance);
            if (intersections != null)
            {
                if (intersectionsResult == null)
                    intersectionsResult = new LinkedList<>();
                intersectionsResult.addAll(intersections);
            }
        }
        return intersectionsResult;
    }
}
