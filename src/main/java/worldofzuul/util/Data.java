package worldofzuul.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import worldofzuul.Game;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Data {

    public static String readConfigFile(String configFileName){
        try{
            List<String> strings = Files.readAllLines(Paths.get(configFileName));
            return String.join("", strings).trim();
        } catch (Exception e){
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static String gameToJson(Game game){
        try{
            ObjectWriter ow = new ObjectMapper().writer();
            return ow.writeValueAsString(game);

        } catch (Exception e){
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static Game jsonToGame(String configJson){
        try{

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(configJson, Game.class);

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            return null;
        }
    }


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
