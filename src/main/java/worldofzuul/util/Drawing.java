package worldofzuul.util;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import worldofzuul.world.*;

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

    public static TranslateTransition translateNow(Node node, double x, double y, double z, int translationTime){

        TranslateTransition translateTransition = translate(node,x,y,z,translationTime);
        translateTransition.play();

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

    public static void drawGameObjects(Room room, HashMap<String, Image> loadedImages, Pane roomPane, double backgroundTileDim) {
        for (int i = 0; i < room.getRoomGrid().length; i++) {
            for (int j = 0; j < room.getRoomGrid().length; j++) {
                var rect = new Rectangle(j * backgroundTileDim, i * backgroundTileDim, backgroundTileDim, backgroundTileDim);

                GameObject object = room.getGridGameObject(new Vector(j, i));

                //Draw img
                //TODO: Refactor & Optimize
                drawGameObjectImage(loadedImages, roomPane, rect, object);


                //Draw border
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
                    if (object.isColliding()) {
                        rect.setStroke(Color.YELLOW);
                    } else {
                        rect.setStroke(Color.GREEN);
                    }
                } else {
                    continue;
                }


                rect.setStrokeWidth(4);
                rect.setFill(Color.TRANSPARENT);
                roomPane.getChildren().add(rect);
            }
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


            roomPane.getChildren().add(imageView);
        }
    }


}
