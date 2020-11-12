package worldofzuul.util;

import javafx.scene.image.Image;
import sdu.student.FXMLController;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Data {

    /**
     * Fetches and loads images in a given directory
     * @param directory Directory located in the resources.sdu.student folder. Must be given without ending or starting slash-signs.
     * @param aClass The class to load from. Pass "getClass()" as the parameter
     * @return HashMap of images. The key value is the path following the given directory.
     */
    public static HashMap<String, Image> getImages(String directory, Class<? extends FXMLController> aClass) {
        URI uri = null;
        try {
            uri = aClass.getResource(directory + "/").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }


        HashMap<String, Image> imageHashMap = new HashMap<>();
        for (File file : Objects.requireNonNull(new File(uri).listFiles())) {
            String[] pathSplit = file.getAbsolutePath().split(directory + "\\\\");
            String name = pathSplit[pathSplit.length - 1];

            Image image = new Image(aClass.getResourceAsStream( directory + "/" + name));
            imageHashMap.put(name, image);
        }


        return imageHashMap;

    }
}
