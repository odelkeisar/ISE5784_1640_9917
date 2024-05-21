package geometries;
import primitives.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a collection of intersectable geometries.
 */
public class Geometries implements Intersectable{

    protected List<Intersectable> geometries=new LinkedList<>();

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
     * Adds intersectable geometries to the collection.
     *
     * @param geometries The intersectable geometries to add.
     */
    public void add(Intersectable... geometries){
        Collections.addAll(this.geometries,geometries);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
