package renderer;

import scene.*;
import primitives.*;
import renderer.*;
import lighting.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import static java.awt.Color.*;

/**
 * MPtests class for testing the rendering of a scene with multiple geometries, lights, and materials.
 * Contains two test methods:
 * 1. megaTest - Renders the scene without any improvements.
 * 2. megaTestWithImprovements - Renders the scene with specified improvements.
 */

public class MPtests {
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    public MPtests() {

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(
                new Sphere(new Point(30, 35, 0), 15d).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(30, 0, 0), 25d).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(30, -50, 0), 40d).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(33, 35, 7), 8d).setEmission(new Color(BLUE))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0.3).setKR(0)),
                new Sphere(new Point(27, 35, 7), 8d).setEmission(new Color(BLUE))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0.3).setKR(0)),
                new Sphere(new Point(35, 35, 12.5), 3d).setEmission(new Color(BLACK))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(25, 35, 12.5), 3d).setEmission(new Color(BLACK))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0).setKR(0)),
                new Triangle(new Point(27, 27, 15), new Point(33, 27, 15), new Point(30, 36, 15))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0)).setEmission(new Color(ORANGE)),
                //דשא:
                //new Triangle(new Point(60, -90, 20), new Point(65, -90, 22), new Point(62.5, -80, 24))
                // .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0)).setEmission(new Color(GREEN)),
                //new Triangle(new Point(63, -90, 22), new Point(68, -90, 20), new Point(65.5, -70, 24))
                //  .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0)).setEmission(new Color(GREEN)),
                //new Triangle(new Point(66, -90, 20), new Point(71, -90, 22), new Point(68.5, -80, 24))
                // .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0)).setEmission(new Color(GREEN)),

                //כדור מעל המראה:
                new Sphere(new Point(-50, 70, 0), 12d)
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0.7).setKR(0.7)),

                //מראה:
                new Triangle(new Point(-20, -90, -40), new Point(-80, -90, 20), new Point(-20, 50, -40))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0.7).setKT(0)),
                new Triangle(new Point(-80, -90, 20), new Point(-80, 50, 20), new Point(-20, 50, -40))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0.7).setKT(0)),
                //מסגרת:
                new Triangle(new Point(-10, -100, -60), new Point(-90, -100, 19), new Point(-10, 60, -60))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0))
                        .setEmission(new Color(GRAY)),
                new Triangle(new Point(-90, -100, 19), new Point(-90, 60, 19), new Point(-10, 60, -60))
                        .setEmission(new Color(GRAY))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0)),

                //ריצפה:
                new Plane(new Point(0, -110, 0), new Point(1, -110, 0), new Point(0, -110, 1))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))

        );


        scene.lights.add(new SpotLight(new Color(500, 500, 400), new Point(0, 200, 200), new Vector(1, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(500, 300, 300), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
    }


    /**
     * Test method to render the scene without any improvements.
     */
    @Test
    public void megaTest() throws CloneNotSupportedException {

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
                .setImageWriter(new ImageWriter("image_without_improvements", 1000, 1000)).build().renderImage().writeToImage();
    }

    /**
     * Test method for processing the scene with improvements of Anti aliasing and soft shadows.
     */

    @Test
    public void megaTestWithImprovements() throws CloneNotSupportedException {

        cameraBuilder.setRayTracer((new SimpleRayTracer(scene, true, true))).setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
                .setImageWriter(new ImageWriter("image_with_improvements", 1000, 1000)).build().renderImage().writeToImage();

    }
}
