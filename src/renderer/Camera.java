package renderer;

import primitives.*;
import primitives.Vector;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Camera class represents a camera in a 3D scene. It is used to construct rays through a view plane.
 */
public class Camera implements Cloneable {
    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double height = 0;
    private double width = 0;
    private double distance = 0;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int countThread = 0;
    private int numberRaysAdaptive = 20;
    private boolean adaptive = false;
    private boolean antiA = false;


    /**
     * Private constructor to enforce usage of the builder.
     */
    private Camera() {
    }

    /**
     * Gets the camera location.
     *
     * @return the camera location.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Gets the direction vector to the view plane.
     *
     * @return the direction vector to the view plane.
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Gets the up direction vector.
     *
     * @return the up direction vector.
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Gets the right direction vector.
     *
     * @return the right direction vector.
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Gets the view plane height.
     *
     * @return the view plane height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the view plane width.
     *
     * @return the view plane width.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the distance from the camera to the view plane.
     *
     * @return the distance from the camera to the view plane.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Gets the number of threads used for rendering.
     *
     * @return the number of threads.
     */
    public double getCountTread() {
        return countThread;
    }

    /**
     * Checks if anti-aliasing is enabled.
     *
     * @return true if anti-aliasing is enabled, false otherwise.
     */
    public boolean getAntiA() {
        return antiA;
    }

    /**
     * Returns a new builder for the Camera.
     *
     * @return a new Camera builder.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Renders the image by casting rays through each pixel and determining the color for each pixel.
     * If multi-threading is enabled, the rendering process will utilize multiple threads.
     *
     * @return the current Camera instance for method chaining.
     */
    public Camera renderImage() {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        if (countThread == 0) {
            for (int i = 0; i < nY; i++)
                for (int j = 0; j < nX; j++)
                    castRay(nX, nY, j, i);
        } else {
            ExecutorService executor = Executors.newFixedThreadPool(countThread);

            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    final int pixelI = i;
                    final int pixelJ = j;
                    executor.execute(() -> castRay(nX, nY, pixelJ, pixelI));
                }
            }
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return this;
    }


    /**
     * Casts a ray through a specific pixel and determines the color for that pixel.
     * The method supports adaptive super-sampling and anti-aliasing.
     *
     * @param nX number of horizontal pixels
     * @param nY number of vertical pixels
     * @param j  horizontal index of the pixel
     * @param i  vertical index of the pixel
     */
    private void castRay(int nX, int nY, int j, int i) {
        Color color = Color.BLACK;

        if (adaptive && antiA) {
            color = AdaptiveSuperSampling(nX, nY, j, i, numberRaysAdaptive);
        } else if (antiA) {
            List<Ray> rays = constructRays(nX, nY, j, i);
            color = rayTracer.traceBeamRay(rays);
        } else {
            Ray ray = constructRay(nX, nY, j, i);
            color = rayTracer.traceRay(ray);
        }
        imageWriter.writePixel(j, i, color);
        //System.out.printf("Numbers: %d, %d%n", j, i);
    }

    /**
     * Gets the center point of a specific pixel on the view plane.
     *
     * @param nX number of horizontal pixels
     * @param nY number of vertical pixels
     * @param j  horizontal index of the pixel
     * @param i  vertical index of the pixel
     * @return the center point of the specified pixel
     */
    private Point getCenterOfPixel(int nX, int nY, double j, double i) {
        Point pc = p0.add(vTo.scale(distance));  // The center of the view plane
        double rX = width / nX;  // The width of a single pixel
        double rY = height / nY;  // The height of a single pixel
        double xJ = (j - (nX - 1) / 2d) * rX;  // The offset on the x-axis
        double yI = -(i - (nY - 1) / 2d) * rY;  // The offset on the y-axis
        Point pIJ = pc;
        if (!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));  // Adjust the point for the x offset
        if (!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));
        return pIJ;
    }

    /**
     * Applies adaptive super sampling to a specific pixel.
     *
     * @param nX        number of horizontal pixels
     * @param nY        number of vertical pixels
     * @param j         horizontal index of the pixel
     * @param i         vertical index of the pixel
     * @param numOfRays number of rays to shoot per pixel for super sampling
     * @return the calculated color after applying adaptive super sampling
     */
    private Color AdaptiveSuperSampling(int nX, int nY, int j, int i, int numOfRays) {
        Point pIJ = getCenterOfPixel(nX, nY, j, i);
        double rY = alignZero(height / nY); // The height of the pixel
        double rX = alignZero(width / nX);  // The width of the pixel
        double PRy = rY / numOfRays;
        double PRx = rX / numOfRays;
        return AdaptiveSuperSamplingHelper(pIJ, rX, rY, PRx, PRy,new HashMap<Point, Color>());
    }



    /**
     * Helper method for adaptive super sampling, performing the recursive process.
     *
     * @param centerP   the center point of the current segment
     * @param width     the width of the current segment
     * @param height    the height of the current segment
     * @param minWidth  the minimum width of the segment to stop recursion
     * @param minHeight the minimum height of the segment to stop recursion
     * @param prePoints a map of pre-calculated points and their colors
     * @return the calculated color after applying adaptive super sampling
     */
    public Color AdaptiveSuperSamplingHelper(Point centerP, double width, double height, double minWidth, double minHeight, HashMap<Point, Color> prePoints) {
        // If we got to the minimum segment - return the ray
      if (width < minWidth * 2 || height < minHeight * 2)
          return rayTracer.traceRay(new Ray(p0, centerP.subtract(p0)));

        // Divide the current segment into 4 sub-segments
        List<Point> nextCenterPList = new LinkedList<>(); // The next centers for the next iteration
        HashMap<Point, Color> cornersList = new HashMap<Point, Color>(); // The current points
        List<Color> colorList = new LinkedList<>(); // The colors of the current points

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                Point tempCorner = centerP.add(vRight.scale(i * width / 2)).add(vUp.scale(j * height / 2));
                nextCenterPList.add(centerP.add(vRight.scale(i * width / 4)).add(vUp.scale(j * height / 4)));

                // Check if the point is already in prePoints
                Color color = prePoints.get(tempCorner);
                if (color == null) {
                    color = rayTracer.traceRay(new Ray(p0, tempCorner.subtract(p0)));
                    colorList.add(color);
                    cornersList.put(tempCorner, color);
                } else {
                    cornersList.put(tempCorner, color);
                    colorList.add(color);
                }
            }
        }

        // Check if the colors are similar enough
        boolean isAllEquals = colorList.stream().allMatch(colorList.get(0)::isAlmostEquals);
        if (isAllEquals)
            return colorList.get(0);

        // Continue to the next iteration of the recursion for each part of the grid
        Color tempColor = Color.BLACK;
        for (Point center : nextCenterPList) {
            tempColor = tempColor.add(AdaptiveSuperSamplingHelper(center, width / 2, height / 2, minWidth, minHeight, cornersList));
        }
        return tempColor.reduce(nextCenterPList.size());
    }


    /**
     * Constructs a ray through a given pixel on the view plane.
     *
     * @param nX number of horizontal pixels.
     * @param nY number of vertical pixels.
     * @param j  horizontal index of the pixel.
     * @param i  vertical index of the pixel.
     * @return the constructed ray.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        Point pIJ = getCenterOfPixel(nX, nY, j, i);
        return new Ray(p0, pIJ.subtract(p0));  // Construct the ray from the camera to the point
    }

    /**
     * Constructs multiple rays through a given pixel on the view plane to simulate anti-aliasing.
     *
     * @param nX number of horizontal pixels.
     * @param nY number of vertical pixels.
     * @param j  horizontal index of the pixel.
     * @param i  vertical index of the pixel.
     * @return a list of constructed rays.
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i) {

        Point Pc = getCenterOfPixel(nX, nY, j, i);

        List<Ray> rays = new ArrayList<>();
        rays.add(new Ray(p0, Pc.subtract(p0)));

        double rX = width / nX;
        double rY = height / nY;

        /**
         * creating Ry*Rx rays for each pixel.
         */
        Point point = new Point(Pc.getX() - rX / 2, Pc.getY() + rY / 2, Pc.getZ());

        for (double t = point.getY(); t > point.getY() - rY; t -= 0.01) {
            for (double k = point.getX(); k < point.getX() + rX; k += 0.01) {
                rays.add(new Ray(p0, new Point(k, t, Pc.getZ()).subtract(p0)));
            }
        }

        return rays;
    }

    /**
     * Draws a grid on the image with the specified interval and color.
     *
     * @param interval the interval between grid lines, in pixels
     * @param color    the Color of the grid lines
     */
    public Camera printGrid(int interval, Color color) {

        for (int i = 0; i < imageWriter.getNy(); i++)
            for (int j = 0; j < imageWriter.getNx(); j++)
                if (j % interval == 0 || i % interval == 0)
                    imageWriter.writePixel(j, i, color);
        return this;
    }

    /**
     * Writes the image to a file using the ImageWriter.
     * If the ImageWriter is not set, throws a MissingResourceException.
     *
     * @throws MissingResourceException if the ImageWriter is not set
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * Builder class for constructing a Camera instance.
     */
    public static class Builder {

        private final Camera camera = new Camera();

        /**
         * Sets the camera location.
         *
         * @param point the camera location.
         * @return the builder instance.
         */
        public Builder setLocation(Point point) {
            camera.p0 = point;
            return this;
        }

        /**
         * Sets the number of threads for rendering.
         *
         * @param countThread The number of threads.
         * @return The Builder instance.
         */
        public Builder setCountThread(int countThread) {
            camera.countThread = countThread;
            return this;
        }

        /**
         * Sets the number of rays for adaptive super-sampling.
         *
         * @param numberRaysAdaptive The number of rays.
         * @return The Builder instance.
         */
        public Builder setNumberRaysAdaptive(int numberRaysAdaptive) {
            camera.numberRaysAdaptive = numberRaysAdaptive;
            return this;
        }

        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance the distance from the camera to the view plane.
         * @return the builder instance.
         */
        public Builder setVpDistance(double distance) {
            camera.distance = distance;
            if (distance < 0)
                throw new IllegalArgumentException("distance should be greater than zero");
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         *
         * @param vTo the direction vector to the view plane.
         * @param vUp the up direction vector.
         * @return the builder instance.
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!isZero(vTo.dotProduct(vUp)))
                throw new IllegalArgumentException("The vectors should be perpendicular to each other");
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param width  the view plane width.
         * @param height the view plane height.
         * @return the builder instance.
         */
        public Builder setVpSize(double width, double height) {
            camera.width = width;
            camera.height = height;
            if (width <= 0 || height <= 0)
                throw new IllegalArgumentException("width and height should be greater than zero");
            return this;
        }

        /**
         * Sets the ImageWriter for the camera.
         *
         * @param imageWriter the ImageWriter to be set for the camera
         * @return the current Builder instance for method chaining.
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the RayTracer for the camera.
         *
         * @param rayTracer the RayTracerBase to be set for the camera
         * @return the current Builder instance for method chaining
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * set the adaptive
         *
         * @return the Camera object
         */
        public Builder setAdaptive(boolean adaptive) {
            camera.adaptive = adaptive;
            return this;
        }
        /**
         * Enables or disables anti-aliasing.
         *
         * @param antiA true to enable, false to disable
         * @return the builder instance
         */
        public Builder setAntiA(boolean antiA) {
            camera.antiA = antiA;
            return this;
        }

        /**
         * Builds and returns the Camera instance.
         *
         * @return the built Camera instance.
         * @throws CloneNotSupportedException if the camera cannot be cloned.
         * @throws MissingResourceException   if any required field is missing.
         */
        public Camera build() throws CloneNotSupportedException {
            String errorMessage = "Missing rendering data";
            if (camera.vTo == null)
                throw new MissingResourceException(errorMessage, Camera.class.getName(), "vTo");
            if (camera.vUp == null)
                throw new MissingResourceException(errorMessage, Camera.class.getName(), "vUp");
            if (camera.width == 0)
                throw new MissingResourceException(errorMessage, Camera.class.getName(), "width");
            if (camera.height == 0)
                throw new MissingResourceException(errorMessage, Camera.class.getName(), "height");
            if (camera.distance == 0)
                throw new MissingResourceException(errorMessage, Camera.class.getName(), "distance");
            if (camera.p0 == null)
                throw new MissingResourceException(errorMessage, Camera.class.getName(), "p0");
            if (camera.imageWriter == null)
                throw new MissingResourceException(errorMessage, Camera.class.getName(), "imageWriter");
            if (camera.rayTracer == null)
                throw new MissingResourceException(errorMessage, Camera.class.getName(), "rayTracer");
            if (camera.countThread < 0)
                throw new MissingResourceException("Incorrect data", Camera.class.getName(), "countThread");
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            return (Camera) camera.clone();
        }
    }
}
