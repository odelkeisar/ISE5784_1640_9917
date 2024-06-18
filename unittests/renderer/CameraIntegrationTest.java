package renderer;

import geometries.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for Camera with various geometries.
 */
@Nested
class CameraIntegrationTest {

    Camera camera = Camera.getBuilder().setLocation(new Point(0, 0, 0.5))
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0)).setVpDistance(1)
            .setVpSize(3, 3)
            .setRayTracer(new SimpleRayTracer( new Scene("Test scene")))
            .setImageWriter(new ImageWriter("base render test", 1000, 1000)).build();

    CameraIntegrationTest() throws CloneNotSupportedException {
    }

    /**
     * Counts the number of intersection points between rays from the camera and a given geometry.
     *
     * @param camera the camera instance.
     * @param geometry the geometry to test against.
     * @param nY
     * @param nX1
     * @return the number of intersection points.
     */
    private int countIntersections(Camera camera, Geometry geometry, int nY, int nX) {
        List<Point> points = new LinkedList<>();

        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                List<Point> intersection = geometry.findIntersections(camera.constructRay(nX, nY, j, i));
                if (intersection != null) {
                    points.addAll(intersection);
                }
            }
        }
        return points.size();
    }

    /**
     * Test integration of Camera rays with Sphere geometries.
     */
    @Test
    public void cameraRaySphereIntegration() {
        int nX = 3, nY = 3;
        // TC01: Small Sphere 2 points
        Sphere sphere1 = new Sphere(new Point(0, 0, -2.5), 1);
        assertEquals(2, countIntersections(camera, sphere1, nY, nX), "The number of intersection points is incorrect");

        // TC02: Big Sphere 18 points
        Sphere sphere2 = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(18, countIntersections(camera, sphere2, nY, nX), "The number of intersection points is incorrect");

        // TC03: Medium Sphere 10 points
        Sphere sphere3 = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, countIntersections(camera, sphere3, nY, nX), "The number of intersection points is incorrect");

        // TC04: The camera is inside a big ball 9 points
        Sphere sphere4 = new Sphere(new Point(0, 0, -1), 4);
        assertEquals(9, countIntersections(camera, sphere4, nY, nX), "The number of intersection points is incorrect");

        // TC05: Beyond Sphere 0 points
        Sphere sphere5 = new Sphere(new Point(0, 0, 2), 1);
        assertEquals(0, countIntersections(camera, sphere5, nY, nX), "The number of intersection points is incorrect");
    }

    /**
     * Test integration of Camera rays with Plane geometries.
     */
    @Test
    public void cameraRayPlaneIntegration() {
        int nX = 3, nY = 3;
        // TC01: Plane against camera 9 points
        Plane plane1 = new Plane(new Point(0, 0, -2), new Vector(0, 0, -1));
        assertEquals(9, countIntersections(camera, plane1, nY, nX), "The number of intersection points is incorrect");

        // TC02: Plane with small angle 9 points
        Plane plane2 = new Plane(new Point(0, 0, -2), new Vector(0, -0.2, 1.5));
        assertEquals(9, countIntersections(camera, plane2, nY, nX), "The number of intersection points is incorrect");

        // TC03: Plane parallel to lower rays 6 points
        Plane plane3 = new Plane(new Point(0, 0, -4.5), new Vector(0, 1, 1));
        assertEquals(6, countIntersections(camera, plane3, nY, nX), "The number of intersection points is incorrect");

        // TC04: The plane is behind the camera 0 points
        Plane plane4 = new Plane(new Point(0, 0, 3), new Vector(0, 0, 1));
        assertEquals(0, countIntersections(camera, plane4, nY, nX), "The number of intersection points is incorrect");
    }

    /**
     * Test integration of Camera rays with Triangle geometries.
     */
    @Test
    public void cameraRayTriangleIntegration() {
        int nX = 3, nY = 3;
        // TC01: The triangle is small = 1 point
        Triangle triangle1 = new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -1, -2));
        assertEquals(1, countIntersections(camera, triangle1, nY, nX), "The number of intersection points is incorrect");

        // TC02: The triangle is medium triangle 2 points
        Triangle triangle2 = new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -20, -2));
        assertEquals(2, countIntersections(camera, triangle2, nY, nX), "The number of intersection points is incorrect");
    }
}
