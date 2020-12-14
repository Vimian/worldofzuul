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

/**
 * The type Field info bar controller.
 */
public class FieldInfoBarController implements Initializable {

    /**
     * The constant timeOutDelay for the node.
     */
    private final static int timeOutDelay = 12000;
    /**
     * The Field which values are to be displayed.
     */
    private final Field field;
    /**
     * The Info bar container.
     */
    public Pane infoBarContainer;
    /**
     * The Water info bar displaying the {@link FieldInfoBarController#field}'s water property.
     */
    public ProgressBar waterInfoBar;
    /**
     * The Nutrition info bar displaying the {@link FieldInfoBarController#field}'s nutrition property.
     */
    public ProgressBar nutritionInfoBar;
    /**
     * The Timer for hiding the node.
     */
    private Timer timer;

    /**
     * Instantiates a new Field info bar controller.
     *
     * @param field the field
     */
    public FieldInfoBarController(Field field) {
        this.field = field;
    }

    /**
     * Reset {@link FieldInfoBarController#timer}.
     */
    private void resetTimer() {
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

    /**
     * Sets {@link FieldInfoBarController#waterInfoBar} progress.
     */
    private void setWaterProgress() {
        waterInfoBar.setProgress(field.getWater() / field.getMaxWater());

    }

    /**
     * Sets {@link FieldInfoBarController#nutritionInfoBar} progress.
     */
    private void setNutritionProgress() {
        nutritionInfoBar.setProgress(field.getNutrition() / field.getMaxNutrition());
    }


}
