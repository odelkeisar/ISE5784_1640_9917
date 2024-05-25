package primitives;

import static primitives.Util.isZero;

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
    /**
     * returns the head point of the ray
     *
     * @return the head
     */
    public Point getHead() {
        return head;
    }

    /**
     * returns the vector(direction) of the ray
     *
     * @return direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     *
     * @param t is a scalar
     * @return A point on the beam at a given distance : p0+t*v
     */
    public Point getPoint(double t){
        if (isZero(t))
            return head;
        return head.add(direction.scale(t));
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
