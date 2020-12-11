package worldofzuul.util;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sdu.student.FieldInfoBarController;
import worldofzuul.world.*;

import java.io.IOException;
import java.util.HashMap;

public class Drawing {

    public static TranslateTransition translate(Node node, double x, double y, double z, int translationTime){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(translationTime), node);

        translateTransition.setByX(x);
        translateTransition.setByY(y);
        translateTransition.setByZ(z);

        translateTransition.setCycleCount(1);

        return translateTransition;
    }

    public static void drawGrid(Pane pane, double rowCount){

        double cubeDim = (pane.getMinWidth() / rowCount);
        for (int i = 0; i < rowCount + 1; i++) {
            Line hLine = new Line(0, i * cubeDim, pane.getMinWidth(), i * cubeDim);
            Line vLine = new Line(i * cubeDim, 0, i * cubeDim, pane.getMinWidth());

            pane.getChildren().add(hLine);
            pane.getChildren().add(vLine);
        }

    }

    public static void setNodePositionToVectorCorner(Node node, Vector position, double tileDim){
        node.setTranslateX(position.getX() * tileDim + tileDim / 3);
        node.setTranslateY(position.getY() * tileDim - tileDim / 3);
    }

    public static void drawGameObjects(Room room, HashMap<String, Image> loadedImages, Pane roomPane, double backgroundTileDim, Class<?> callerController, Vector clickedPos, boolean highlight) {
        for (int i = 0; i < room.getRoomGrid().length; i++) {
            for (int j = 0; j < room.getRoomGrid().length; j++) {

                var rect = new Rectangle(j * backgroundTileDim, i * backgroundTileDim, backgroundTileDim, backgroundTileDim);
                GameObject object = room.getGridGameObject(new Vector(j,i));
                drawGameObjectImage(loadedImages, roomPane, rect, object);


                if (object instanceof Block) {
                    if (object.isColliding()) {
                        rect.setStroke(Color.RED);
                    } else {
                        continue;
                    }
                } else if (object instanceof Door) {

                    if (object.isColliding()) {
                        rect.setStroke(Color.CYAN);
                    } else {
                        rect.setStroke(Color.BLUE);
                    }

                } else if (object instanceof Field) {
                    appendFieldInfoBar((Field) object, roomPane, new Vector(j, i), backgroundTileDim, callerController);
                    if (object.isColliding()) {
                        rect.setStroke(Color.YELLOW);
                    } else {
                        rect.setStroke(Color.GREEN);
                    }
                } else if (object instanceof NPC) {
                    if (object.isColliding()) {
                        rect.setStroke(Color.MAGENTA);
                    } else {
                        rect.setStroke(Color.DARKMAGENTA);
                    }
                }
                else {
                    continue;
                }

                if(highlight){
                    rect.setStrokeWidth(4);
                    rect.setFill(Color.TRANSPARENT);
                    roomPane.getChildren().add(rect);
                }
            }
        }

        if(clickedPos != null && highlight){
            Rectangle rect = new Rectangle(clickedPos.getX() * backgroundTileDim,
                    clickedPos.getY() * backgroundTileDim,
                    backgroundTileDim,
                    backgroundTileDim);
            rect.setStrokeWidth(4);
            rect.setFill(Color.TRANSPARENT);
            rect.setStroke(Color.PINK);
            roomPane.getChildren().add(rect);

        }

    }


    private static void appendFieldInfoBar(Field field, Pane pane, Vector position, double tileDim, Class<?> callerController) {
        Node infoBar = loadFieldInfoBar(field, callerController);
        if (infoBar != null) {
            pane.getChildren().add(infoBar);
            setNodePositionToVectorCorner(infoBar, position, tileDim);
        }
    }

    private static void drawGameObjectImage(HashMap<String, Image> loadedImages, Pane roomPane, Rectangle rect, GameObject object) {
        if (object.getImage() != null || object.getDefaultImageFile() != null && loadedImages.containsKey(object.getDefaultImageFile())) {
            ImageView imageView;
            if (object.getImageView() == null || loadedImages.get(object.getDefaultImageFile()) != object.getImage()) {
                imageView = new ImageView(loadedImages.get(object.getDefaultImageFile()));
                imageView.setX(rect.getX());
                imageView.setY(rect.getY());
                imageView.setFitHeight(rect.getHeight());
                imageView.setFitWidth(rect.getWidth());

                object.setImageView(imageView);
            } else {
                imageView = object.getImageView();
            }

            if (object.getImage() != null && object.getImage() != imageView.getImage()) {
                imageView.setImage(object.getImage());
            }

            object.display();
            roomPane.getChildren().add(imageView);
        }
    }

    private static Node loadFieldInfoBar(Field field, Class<?> callerController){
        FXMLLoader loader = new FXMLLoader();

        //Defines our controller
        loader.setControllerFactory(aClass -> new FieldInfoBarController(field));
        //Defines the FXML file
        loader.setLocation(callerController.getResource("fieldInfoBar.fxml"));

        try {
            return loader.load();


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
