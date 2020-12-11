package sdu.student;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import worldofzuul.Game;
import worldofzuul.item.Item;

import java.net.URL;
import java.util.ResourceBundle;


public class SubScene implements Initializable {

    private final Game model;
    public Pane marketScene;
    @FXML
    private TableView<Item> inventoryView;
    @FXML
    private TableView<Item> marketView;

    public SubScene(Game game) {
        model = game;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventoryView.itemsProperty().bindBidirectional(model.getPlayer().getInventory().itemsProperty());
        marketView.itemsProperty().bindBidirectional(model.getMarket().stockProperty());
    }

    public void inventoryClicked() {
    }

    public void sellClicked() {
        model.getMarket().sellItem(inventoryView.getSelectionModel().getSelectedItem(), model.getPlayer());

    }

    public void buyClicked() {
        model.getMarket().purchaseItem(marketView.getSelectionModel().getSelectedItem(), model.getPlayer());
    }

    public void exitClicked() {
        marketScene.setVisible(false);
    }
}
