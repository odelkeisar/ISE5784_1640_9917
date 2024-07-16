package renderer;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.*;
import static java.awt.Color.GRAY;

public class MP2tests {


    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    public MP2tests() {

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(
                //ראש
                new Sphere(new Point(30, 35, 0), 15d).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                //גוף
                new Sphere(new Point(30, 0, 0), 25d).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(30, -50, 0), 40d).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                //עיינים
                new Sphere(new Point(33, 35, 7), 8d).setEmission(new Color(BLUE))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0.3).setKR(0)),
                new Sphere(new Point(27, 35, 7), 8d).setEmission(new Color(BLUE))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0.3).setKR(0)),
                //אישונים
                new Sphere(new Point(35, 35, 12.5), 3d).setEmission(new Color(BLACK))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(25, 35, 12.5), 3d).setEmission(new Color(BLACK))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0).setKR(0)),

                //כדור קישוט מעל המראה:
                new Sphere(new Point(-50, 70, 0), 12d)
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0.7).setKR(0.7)),

                //מראה:
                new Triangle(new Point(-20, -90, -40), new Point(-80, -90, 20), new Point(-20, 50, -40))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0.7).setKT(0)),
                new Triangle(new Point(-80, -90, 20), new Point(-80, 50, 20), new Point(-20, 50, -40))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0.7).setKT(0)),
                //מסגרת:
                new Triangle(new Point(-10, -100, -60), new Point(-90, -100, 20), new Point(-10, 60, -60))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0))
                        .setEmission(new Color(GRAY)),
                new Triangle(new Point(-90, -100, 20), new Point(-90, 60, 20), new Point(-10, 60, -60))
                        .setEmission(new Color(GRAY))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0)),

                //ריצפה:
                new Plane(new Point(0, -110, 0), new Point(1, -110, 0), new Point(0, -110, 1))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),

                // ידיים:
                new Triangle(new Point(50, 4, 0), new Point(50, 0, 4), new Point(90, 8, 2))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0))
                        .setEmission(new Color(139,69,0)),
                new Triangle(new Point(10, 4, 2), new Point(10, 0, 2), new Point(-30, 8, 6))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0))
                        .setEmission(new Color(139,69,0)),
                new Triangle(new Point(70, 4, 1), new Point(73, 5, 1), new Point(76, 14, 0))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0))
                        .setEmission(new Color(139,69,0)),

                //כפתורים
                new Sphere(new Point(30, 2, 22), 4).setEmission(new Color(GRAY))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(31, 2, 25), 1).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(29, 2, 25), 1).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),

                new Sphere(new Point(30, 10, 19.5), 4).setEmission(new Color(GRAY))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(31, 11, 22.4), 1).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(29, 11, 22.4), 1).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),

                new Sphere(new Point(30, -7, 21), 4).setEmission(new Color(GRAY))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(31, -8, 23.9), 1).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(29, -8, 23.9), 1).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.5).setShininess(100).setKT(0).setKR(0)),

                //אף בולט
                new Sphere(new Point(30, 30, 15), 2).setEmission(new Color(ORANGE))
                        .setMaterial(new Material().setKd(0.2).setKs(0).setShininess(0)),
                new Sphere(new Point(29.2, 29.5, 16.5), 1.5).setEmission(new Color(ORANGE))
                        .setMaterial(new Material().setKd(0.2).setKs(0).setShininess(0)),
                new Sphere(new Point(28.7, 29, 17.5), 1.5).setEmission(new Color(ORANGE))
                        .setMaterial(new Material().setKd(0.2).setKs(0).setShininess(0)),
                new Sphere(new Point(28, 28.2, 19), 1.2).setEmission(new Color(ORANGE))
                        .setMaterial(new Material().setKd(0.2).setKs(0).setShininess(0)),
                new Sphere(new Point(27.4, 27.5, 20), 1).setEmission(new Color(ORANGE))
                        .setMaterial(new Material().setKd(0.2).setKs(0).setShininess(0))

        );


        scene.lights.add(new SpotLight(new Color(500, 500, 400), new Point(0, 200, 200), new Vector(1, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(500, 300, 300), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(500, 500, 400), new Point(10, 60, 0), new Vector(45, -25, 100)) //
                .setKl(4E-5).setKq(2E-7));
    }


    /**
     * Test method to render the scene without any improvements.
     */
    @Test
    public void megaTest2() throws CloneNotSupportedException {

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
                .setImageWriter(new ImageWriter("image_MP2_slow", 1000, 1000)).build().renderImage().writeToImage();
    }
}