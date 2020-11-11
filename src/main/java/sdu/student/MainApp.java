package sdu.student;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import worldofzuul.Game;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {


        try{
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            Game game = new Game();
            String json = ow.writeValueAsString(game);
            String what = json;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }









        launch(args);

    }
}
