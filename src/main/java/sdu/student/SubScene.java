package sdu.student;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import worldofzuul.Game;

import java.net.URL;
import java.util.ResourceBundle;


public class SubScene implements Initializable {
    public SubScene(Game game) {
        model = game;
    }

    @FXML
    private ListView inventoryView;

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
        fxmlController.playerItemsClicked(mouseEventInventory);
    }

}
