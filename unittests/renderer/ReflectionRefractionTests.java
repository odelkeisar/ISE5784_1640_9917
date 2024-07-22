/**
 * 
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import lighting.DirectionalLight;
import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   /** Scene for the tests */
   private final Scene          scene         = new Scene("Test scene");
   /** Camera builder for the tests with triangles */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()
      .setDirection(new Vector(0, 0, -1), new Vector(0,1,0))
      .setRayTracer(new SimpleRayTracer(scene));

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() throws CloneNotSupportedException {
      scene.geometries.add(
                           new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKT(0.3)),
                           new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add(
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                          .setKl(0.0004).setKq(0.0000006));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(150, 150)
         .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() throws CloneNotSupportedException {
      scene.geometries.add(
                           new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100))
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKT(new Double3(0.5, 0, 0))),
                           new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setKR(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
         .setKl(0.00001).setKq(0.000005));

      cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
         .setVpSize(2500, 2500)
         .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() throws CloneNotSupportedException {
      scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150))
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                           new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKT(0.6)));
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.lights.add(
                       new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                          .setKl(4E-5).setKq(2E-7));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(200, 200)
         .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
         .build()
         .renderImage()
         .writeToImage();
   }


   /**
    * Mega geometry combination test including all features
    */
   @Test
   public void megaTest() throws CloneNotSupportedException {


      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      scene.geometries.add(
              new Sphere( new Point(-40, 30, 0),30d)
                      .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKT(0.5).setKR(0.5)),
              new Sphere(new Point(60, 0, 0),35d)
                      .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKR(0.4).setKT(0.2))
                      .setEmission(new Color(252, 148, 3)),
              new Sphere(new Point(-15, -40, 10),25d)
                      .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0.7).setKT(0.2))
                      .setEmission(new Color(252, 3, 252)),

              new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, -75, -150)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
              new Triangle(new Point(-150, -150, -115), new Point(-70, -70, -140), new Point(75, -75, -150)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)) //

      );

      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
              .setKl(4E-5).setKq(2E-7));
      scene.lights.add(new PointLight(new Color(100, 200, 200), new Point(60, 50, 100)) //
              .setKl(4E-5).setKq(2E-7));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
              .setImageWriter(new ImageWriter("mega_image", 600, 600)).build().renderImage().writeToImage();
   }

}
