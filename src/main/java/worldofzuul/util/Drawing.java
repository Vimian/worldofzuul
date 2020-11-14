package worldofzuul.util;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

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



}
