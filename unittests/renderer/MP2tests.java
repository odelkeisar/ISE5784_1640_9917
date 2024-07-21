package renderer;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.concurrent.TimeUnit;

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
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(27, 35, 7), 8d).setEmission(new Color(BLUE))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0).setKR(0)),
                //אישונים
                new Sphere(new Point(35, 35, 12.5), 3d).setEmission(new Color(BLACK))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0).setKR(0)),
                new Sphere(new Point(25, 35, 12.5), 3d).setEmission(new Color(BLACK))//עיינים
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setKT(0).setKR(0)),

                //כדור קישוט מעל המראה:
                new Sphere(new Point(-50, 70.5, 0), 12d)
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
                        .setEmission(new Color(139, 69, 0)),
                new Triangle(new Point(10, 4, 2), new Point(10, 0, 2), new Point(-30, 8, 10))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0))
                        .setEmission(new Color(139, 69, 0)),
                //אצבע:
                new Triangle(new Point(70, 4, 1), new Point(73, 5, 1), new Point(76, 14, 0))
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0).setKT(0))
                        .setEmission(new Color(139, 69, 0)),

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
        scene.lights.add(new SpotLight(new Color(500, 400, 300), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(500, 500, 400), new Point(10, 60, 0), new Vector(45, -25, 100)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(ORANGE), new Point(0, 50, 10), new Vector(90, -42, -8)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(500, 300, 200), new Point(0, 20, 10), new Vector(40, -90, 50)) //
                .setKl(4E-5).setKq(2E-7));

    }


    /**
     * Test method to render the scene without any improvements.
     */
    @Test
    public void megaTest2() throws CloneNotSupportedException {
        long startTime = System.currentTimeMillis(); // זמן התחלה
        cameraBuilder
                .setRayTracer((new SimpleRayTracer(scene, true).setCountBeam(20)))
                .setAntiA(true)
                .setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("image_MP2_slow_0", 1000, 1000))
                .build().renderImage().writeToImage();

        long endTime = System.currentTimeMillis(); // זמן סיום
        long durationMillis = endTime - startTime; // חישוב משך הזמן במילישניות
        long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis); // המרת מילישניות לדקות

        System.out.println("Runtime without speed improvement: " + durationMinutes + " minutes");
    }

    /**
     * Test method to render the scene with soft shadows (fast).
     */
    @Test
    public void megaTest2_with_softShadow_fast() throws CloneNotSupportedException {
        long startTime = System.currentTimeMillis(); // זמן התחלה
        cameraBuilder
                .setRayTracer((new SimpleRayTracer(scene, true).setCountBeam(50)))
                .setCountThread(6)
                .setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("softShadow_fast", 1000, 1000))
                .build().renderImage().writeToImage();

        long endTime = System.currentTimeMillis(); // זמן סיום
        long durationMillis = endTime - startTime; // חישוב משך הזמן במילישניות
        long durationSeconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis);
        System.out.println("Runtime with soft shadows (fast): " + durationSeconds + " seconds");
    }

    /**
     * Test method to render the scene with soft shadows (slow).
     */
    @Test
    public void megaTest2_with_softShadow_slow() throws CloneNotSupportedException {
        long startTime = System.currentTimeMillis(); // זמן התחלה
        cameraBuilder
                .setRayTracer((new SimpleRayTracer(scene, true).setCountBeam(50)))
                .setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("softShadow_slow", 1000, 1000))
                .build().renderImage().writeToImage();

        long endTime = System.currentTimeMillis(); // זמן סיום
        long durationMillis = endTime - startTime; // חישוב משך הזמן במילישניות
        long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis); // המרת מילישניות לדקות

        System.out.println("Runtime with soft shadows (slow): " + durationMinutes + " minutes");
    }

    /**
     * Test method to render the scene with anti-aliasing (fast).
     */
    @Test
    public void megaTest2_with_AntiA_fast() throws CloneNotSupportedException {
        long startTime = System.currentTimeMillis(); // זמן התחלה
        cameraBuilder
                .setRayTracer((new SimpleRayTracer(scene)))
                .setAntiA(true)
                .setAdaptive(true)
                .setCountThread(6)
                .setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("AntiA_fast", 1000, 1000))
                .build().renderImage().writeToImage();

        long endTime = System.currentTimeMillis(); // זמן סיום
        long durationMillis = endTime - startTime; // חישוב משך הזמן במילישניות
        long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis); // המרת מילישניות לדקות

        System.out.println("Runtime with anti-aliasing (fast): " + durationMinutes + " minutes");
    }

    /**
     * Test method to render the scene with anti-aliasing (slow).
     */
    @Test
    public void megaTest2_with_AntiA_slow() throws CloneNotSupportedException {
        long startTime = System.currentTimeMillis(); // זמן התחלה
        cameraBuilder
                .setRayTracer((new SimpleRayTracer(scene)))
                .setAntiA(true)
                .setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("AntiA_slow", 1000, 1000))
                .build().renderImage().writeToImage();

        long endTime = System.currentTimeMillis(); // זמן סיום
        long durationMillis = endTime - startTime; // חישוב משך הזמן במילישניות
        long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis); // המרת מילישניות לדקות

        System.out.println("Runtime with anti-aliasing (slow): " + durationMinutes + " minutes");
    }

    /**
     * A test method for processing the scene with two improvements: multipass and adaptive supersampling.
     */
    @Test
    public void megaTest2_with_two_Improvements() throws CloneNotSupportedException {

        long startTime = System.currentTimeMillis(); // זמן התחלה

        cameraBuilder
                .setRayTracer((new SimpleRayTracer(scene, true).setCountBeam(15)))
                .setAntiA(true)
                .setAdaptive(true)
                .setCountThread(6)
                .setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("image_MP2_fast_1", 1000, 1000))
                .build().renderImage().writeToImage();

        long endTime = System.currentTimeMillis(); // זמן סיום
        long durationMillis = endTime - startTime; // חישוב משך הזמן במילישניות
        long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis); // המרת מילישניות לדקות
        long durationHours = TimeUnit.MILLISECONDS.toHours(durationMillis); // המרת מילישניות לשעות
        System.out.println("Runtime with two improvements: " + durationHours + " hours and " + (durationMinutes % 60) + " minutes");
    }
}