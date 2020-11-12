package worldofzuul.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import worldofzuul.Game;

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

        } catch (Exception e){
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            return null;
        }
    }
}
