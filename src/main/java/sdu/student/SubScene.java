package sdu.student;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import worldofzuul.Game;

import java.net.URL;
import java.util.ResourceBundle;


public class SubScene implements Initializable {
    public Pane marketScene;

    public SubScene(Game game) {
        model = game;
    }

    @FXML
    private ListView inventoryView;
    //Wack

    @FXML
    private ListView marketView;

    Game model = new Game();
    private FXMLController fxmlController = new FXMLController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventoryView.itemsProperty().bindBidirectional(model.getPlayer().getInventory().itemsProperty());
        marketView.itemsProperty().bindBidirectional(model.getPlayer().getInventory().itemsProperty());


    }

    public void inventoryClicked(MouseEvent mouseEventInventory){
        //fxmlController.playerItemsClicked(mouseEventInventory);
    }

    public void sellClicked(MouseEvent mouseEvent) {
        inventoryView.getSelectionModel().getSelectedItem();
    }

    public void buyClicked(MouseEvent mouseEvent) {
        marketView.getSelectionModel().getSelectedItem();
    }

    public void exitClicked(MouseEvent mouseEvent) {
        marketScene.setVisible(false);
    }
}
