package sdu.student;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import worldofzuul.Game;
import worldofzuul.item.Item;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * The type Sub scene.
 */
public class SubScene implements Initializable {

    /**
     * The Model.
     */
    private final Game model;
    /**
     * The Market scene.
     */
    public Pane marketScene;
    /**
     * The Inventory view.
     */
    @FXML
    private TableView<Item> inventoryView;
    /**
     * The Market view.
     */
    @FXML
    private TableView<Item> marketView;

    /**
     * Instantiates a new Sub scene.
     *
     * @param game the game
     */
    public SubScene(Game game) {
        model = game;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventoryView.itemsProperty().bindBidirectional(model.getPlayer().getInventory().itemsProperty());
        marketView.itemsProperty().bindBidirectional(model.getMarket().stockProperty());
    }

    /**
     * Inventory clicked.
     */
    public void inventoryClicked() {
    }

    /**
     * Sell clicked.
     */
    public void sellClicked() {
        model.getMarket().sellItem(inventoryView.getSelectionModel().getSelectedItem(), model.getPlayer());

    }

    /**
     * Buy clicked.
     */
    public void buyClicked() {
        model.getMarket().purchaseItem(marketView.getSelectionModel().getSelectedItem(), model.getPlayer());
    }

    /**
     * Exit clicked.
     */
    public void exitClicked() {
        marketScene.setVisible(false);
    }
}
