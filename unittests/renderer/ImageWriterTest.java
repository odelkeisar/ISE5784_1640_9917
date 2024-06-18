package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {
    /**
     * Test method for ImageWriter class
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("yellow", 800, 500);
        for (int i = 0; i < imageWriter.getNy(); i++)
            for (int j = 0; j < imageWriter.getNx(); j++) {
                //Coloring each pixel:
                if (j % 50 == 0 || i % 50 == 0)
                    imageWriter.writePixel(j, i, new Color(java.awt.Color.BLACK));
                else
                    imageWriter.writePixel(j, i, new Color(java.awt.Color.YELLOW));
            }

        imageWriter.writeToImage();
    }
}