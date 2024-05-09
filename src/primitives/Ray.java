package primitives;

/**
 * Represents a ray in 3D space, defined by a head point and a direction vector.
 */
public class Ray {


    private final Point head;


    private final Vector direction;

    /**
     * Constructs a new Ray with the specified head point and direction vector.
     *
     * @param head      The head point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }


    @Override
    public String toString() {
        return "head: " + head.toString() + "direction: " + direction.toString();
    }
}
