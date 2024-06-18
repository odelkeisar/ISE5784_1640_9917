package scene;

import geometries.*;
import lighting.AmbientLight;
import org.w3c.dom.*;
import primitives.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * The SceneBuilder class provides methods to build a Scene object from an XML file.
 */
public class SceneBuilder {

    /**
     * Builds a Scene object from the specified XML file path.
     *
     * @param filePath the path to the XML file
     * @return the constructed Scene object
     */
    public static Scene fromXml(String filePath) {
        Scene scene = null;

        try {
            // Create a new file object from the file path
            File xmlFile = new File(filePath);

            // Set up the DocumentBuilder to parse the XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Get the root element of the XML document
            Element root = doc.getDocumentElement();

            // Parse the background color attribute and set it in the scene
            String backgroundColor = root.getAttribute("background-color");
            Color background = parseColor(backgroundColor);
            scene = new Scene("XML Test scene").setBackground(background);

            // Parse and set the ambient light if present
            NodeList ambientLightList = doc.getElementsByTagName("ambient-light");
            if (ambientLightList.getLength() > 0) {
                Element ambientLightElement = (Element) ambientLightList.item(0);
                String ambientLightColor = ambientLightElement.getAttribute("color");
                double k = Double.parseDouble(ambientLightElement.getAttribute("k"));
                AmbientLight ambientLight = new AmbientLight(parseColor(ambientLightColor), k);
                scene.setAmbientLight(ambientLight);
            }

            // Parse and add the geometries to the scene
            Geometries geometries = new Geometries();
            NodeList geometryList = doc.getElementsByTagName("geometries").item(0).getChildNodes();
            for (int i = 0; i < geometryList.getLength(); i++) {
                Node node = geometryList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element geometryElement = (Element) node;
                    switch (geometryElement.getTagName()) {
                        case "sphere":
                            Point center = parsePoint(geometryElement.getAttribute("center"));
                            double radius = Double.parseDouble(geometryElement.getAttribute("radius"));
                            geometries.add(new Sphere(center, radius));
                            break;
                        case "triangle":
                            Point p0 = parsePoint(geometryElement.getAttribute("p0"));
                            Point p1 = parsePoint(geometryElement.getAttribute("p1"));
                            Point p2 = parsePoint(geometryElement.getAttribute("p2"));
                            geometries.add(new Triangle(p0, p1, p2));
                            break;
                    }
                }
            }
            scene.setGeometries(geometries);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return scene;
    }

    /**
     * Parses a color string in the format "R G B" and returns a Color object.
     *
     * @param colorString the color string to parse
     * @return the parsed Color object
     */
    private static Color parseColor(String colorString) {
        String[] rgb = colorString.split(" ");
        return new Color(
                Integer.parseInt(rgb[0]),
                Integer.parseInt(rgb[1]),
                Integer.parseInt(rgb[2])
        );
    }

    /**
     * Parses a point string in the format "x y z" and returns a Point object.
     *
     * @param pointString the point string to parse
     * @return the parsed Point object
     */
    private static Point parsePoint(String pointString) {
        String[] coords = pointString.split(" ");
        return new Point(
                Double.parseDouble(coords[0]),
                Double.parseDouble(coords[1]),
                Double.parseDouble(coords[2])
        );
    }
}
