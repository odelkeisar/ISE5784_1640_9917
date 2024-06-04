package renderer;

import primitives.*;

import java.util.MissingResourceException;

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
     * Private constructor to enforce usage of the builder.
     */
    private Camera() {
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
     * Constructs a ray through a given pixel on the view plane.
     *
     * @param nX number of horizontal pixels.
     * @param nY number of vertical pixels.
     * @param j  horizontal index of the pixel.
     * @param i  vertical index of the pixel.
     * @return the constructed ray.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance));  // The center of the view plane
        double rX = width / nX;  // The width of a single pixel
        double rY = height / nY;  // The height of a single pixel
        double xJ = (j - (nX - 1) / 2d) * rX;  // The offset on the x-axis
        double yI = -(i - (nY - 1) / 2d) * rY;  // The offset on the y-axis
        Point pIJ = pc;
        if (!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));  // Adjust the point for the x offset
        if (!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));  // Adjust the point for the y offset
        return new Ray(p0, pIJ.subtract(p0));  // Construct the ray from the camera to the point
    }

    /**
     * Renders the image by casting rays through each pixel and writing the resulting color to the image.
     * The method iterates through all the pixels in the image and calls the castRay method for each pixel.
     */
    public Camera renderImage() {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; i++)
            for (int j = 0; j < nX; j++)
                castRay(nX, nY, j, i);
        return this;
    }
    /**
     * Casts a ray through a certain pixel, follows the ray and writes the resulting color to the image using writePixel of the image maker.
     *
     * @param nX the number of pixels along the x-axis
     * @param nY the number of pixels along the y-axis
     * @param j the column index of the pixel
     * @param i the row index of the pixel
     */
    private void castRay(int nX, int nY, int j, int i){
        imageWriter.writePixel(j,i,rayTracer.traceRay(constructRay(nX, nY, j,i)));
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
                    imageWriter.writePixel(j,i, color);
        return this;
    }

    /**
     * Writes the image to a file using the ImageWriter.
     * If the ImageWriter is not set, throws a MissingResourceException.
     *
     * @throws MissingResourceException if the ImageWriter is not set
     */
    public void writeToImage(){
        if (imageWriter == null)
            throw new MissingResourceException("ImageWriter is not set", ImageWriter.class.getName(), "imageWriter");
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
         * Builds and returns the Camera instance.
         *
         * @return the built Camera instance.
         * @throws CloneNotSupportedException if the camera cannot be cloned.
         * @throws MissingResourceException   if any required field is missing.
         */
        String errorMessage = "Missing rendering data";

        public Camera build() throws CloneNotSupportedException {
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
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            return (Camera) camera.clone();
        }
    }
}
