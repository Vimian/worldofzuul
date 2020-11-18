package worldofzuul.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import worldofzuul.Game;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Data {

    public static String readConfigFile(String configFileName) {
        try {
            List<String> strings = Files.readAllLines(Paths.get(configFileName));
            return String.join("", strings).trim();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String gameToJson(Game game) {
        try {
            ObjectWriter ow = new ObjectMapper().writer();
            return ow.writeValueAsString(game);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Game jsonToGame(String configJson) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(configJson, Game.class);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static HashMap<String, Image> getImages(String directory, Class<? extends Initializable> CallerClass) {
        URI uri;
        try {
            uri = CallerClass.getResource(directory + "/").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }


        HashMap<String, Image> imageHashMap = new HashMap<>();
        for (File file : Objects.requireNonNull(new File(uri).listFiles())) {
            String[] pathSplit = file.getAbsolutePath().replace("\\", "/").split(directory + "/");
            String name = directory + "/" + pathSplit[pathSplit.length - 1];

            if (file.isDirectory()) {
                imageHashMap.putAll(getImages(name, CallerClass));
                continue;
            }

            Image image = new Image(CallerClass.getResourceAsStream(name));
            imageHashMap.put(name, image);
        }


        return imageHashMap;

    }


    public static List<Image[]> cutSprites(Image image, int dimension) {

        List<Image[]> cutSprites = new ArrayList<>();
        for (int y = 0; y < (image.getHeight() / dimension); y++) {
            List<Image> imageRows = new ArrayList<>();
            for (int x = 0; x < (image.getWidth() / dimension); x++) {
                Image cutImage = new WritableImage(image.getPixelReader(), x * dimension, y * dimension, dimension, dimension);
                imageRows.add(cutImage);
            }
            Image[] imageArray = imageRows.toArray(new Image[0]);
            cutSprites.add(imageArray);
        }
        return cutSprites;
    }
    public static Image[][] singleImageToArrays(Image image){
        LinkedList<Image[]> images = new LinkedList<Image[]>();
        images.add(new Image[]{image});
        return images.toArray(new Image[1][1]);

    }
}
