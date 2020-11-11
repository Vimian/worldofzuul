package worldofzuul.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import worldofzuul.Game;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Data {
    private static final String configFileName = "gameConfig.txt";

    public static String readConfigFile(){
        try{
            List<String> strings = Files.readAllLines(Paths.get(configFileName));
            return strings.stream().findFirst().get();
        } catch (Exception e){
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static String gameToJson(Game game){
        try{
            ObjectWriter ow = new ObjectMapper().writer();
            String json = ow.writeValueAsString(game);

            return json;
        } catch (Exception e){
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static Game jsonToGame(String configJson){
        try{

            ObjectMapper mapper = new ObjectMapper();
            Game game = mapper.readValue(configJson, Game.class);

            return game;
        } catch (Exception e){
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            return null;
        }
    }
}
