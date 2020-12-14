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

/**
 * The type Data.
 *
 * Contains static functions used for file IO.
 *
 */
public class Data {

    /**
     * Reads all lines in file specified by the pathname param configFileName.
     *
     * @param configFileName the config file name
     * @return the file data
     */
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

    /**
     * Serializes instance of game to JSON.
     *
     * @param game the game
     * @return the serialized data of param game
     */
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


    /**
     * Deserializes JSON to instance of game.
     *
     * @param configJson serialized data of {@link Game}
     * @return the deserialized game
     */
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

    /**
     * Json to room game.
     *
     * Not used
     *
     * @param configJson the config json
     * @return the game
     */
    public static Game jsonToRoom(String configJson) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(configJson, Game.class);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Json to market game.
     *
     * Not used
     *
     * @param configJson the config json
     * @return the game
     */
    public static Game jsonToMarket(String configJson) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(configJson, Game.class);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Json to player game.
     *
     * Not used
     *
     * @param configJson the config json
     * @return the game
     */
    public static Game jsonToPlayer(String configJson) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(configJson, Game.class);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Gets images.
     *
     * Loads all images in all subdirectories given the directory path.
     *
     * @param directory   the directory path
     * @param CallerClass the JavaFX caller controller class, used to load image files as resources into {@link Image}
     * @return Hashmap of strings and images, the string keys are the path, from the given directory path,  to the the image value
     */
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


    /**
     * Cut sprites list.
     *
     * Cuts an image into a list of image arrays.
     * Can be seen of as cutting the a sprite sheet into a grid using the the given dimensions.
     *
     * @param image     the sprite-sheet to cut
     * @param dimension the sprite dimensions
     * @return list of image arrays
     */
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

    /**
     * Adds a single image to an array of image arrays.
     *
     * @param image the image
     * @return the array of image arrays
     */
    public static Image[][] singleImageToArrays(Image image) {
        LinkedList<Image[]> images = new LinkedList<>();
        images.add(new Image[]{image});
        return images.toArray(new Image[1][1]);

    }
}
