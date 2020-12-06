package sdu.student;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import worldofzuul.world.Field;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class FieldInfoBarController implements Initializable {

    public Pane infoBarContainer;
    public ProgressBar waterInfoBar;
    public ProgressBar nutritionInfoBar;
    private Field field;
    private Timer timer;
    private final static int timeOutDelay = 12000;

    public FieldInfoBarController(Field field) {
        this.field = field;
    }

    private void resetTimer(){
        infoBarContainer.setVisible(true);

        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> infoBarContainer.setVisible(false));
            }
        }, timeOutDelay);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setWaterProgress();
        setNutritionProgress();


        field.waterProperty().addListener((observable, oldValue, newValue) -> {
           setWaterProgress();
           resetTimer();
        });
        field.nutritionProperty().addListener((observable, oldValue, newValue) -> {
            setNutritionProgress();
            resetTimer();
        });

        infoBarContainer.setVisible(false);
    }

    private void setWaterProgress()
    {
        waterInfoBar.setProgress(field.getWater() / field.getMaxWater());

    }

    private void setNutritionProgress(){
        nutritionInfoBar.setProgress(field.getNutrition() / field.getMaxNutrition());
    }
    
    
    
}
