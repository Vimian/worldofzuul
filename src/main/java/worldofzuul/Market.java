package worldofzuul;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.item.*;
import worldofzuul.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * The type Market.
 *
 */
public class Market {
    /**
     * The Stock.
     */
    private final ListProperty<Item> stock = new SimpleListProperty<>(
            FXCollections.observableArrayList()
    );

    /**
     * Instantiates a new Market.
     */
    public Market() {

    }

    /**
     * Purchase item.
     *
     * Creates a copy of an {@link Item} in the {@link Market#stock} using {@link Item#copyItem()} and adds it to the player's {@link Inventory}.
     *
     * @param item   the item
     * @param player the player
     */
    public void purchaseItem(Item item, Player player) {
        if (item != null) {
            if (player.getBalance() >= item.getValue()) {
                player.setBalance(player.getBalance() - item.getValue());

                player.getInventory().addItem(item.copyItem());
            } else {
                System.out.println("Not enough money!");
            }
        }
    }

    /**
     * Sell item.
     *
     * Removes parameter item from the {@link Player}'s {@link Inventory} and increases their {@link Player#balanceProperty()} using the {@link Item#getValue()}.
     *
     * @param item   the item
     * @param player the player
     */
    @JsonIgnore
    public void sellItem(Item item, Player player) {
        if (player.getInventory().getItems().contains(item) && item != null) {
            player.setBalance(player.getBalance() + item.getValue() * item.getSellBackRate());
            item.deplete(1);

            if (item.getConsumptionRate() == 0 || item.getRemaining() <= 0) {
                player.getInventory().removeItem(item);
            }
        } else {
            System.out.println("Player does not have that item");
        }
    }

    /**
     * Gets items.
     *
     * @param item the item
     * @return the items
     */
    @JsonIgnore
    public Double getItems(Item item) {
        if (item != null) {
            for (Item value : stock) {
                System.out.println(value);
            }
            return item.getValue();
        } else {
            return 0d;
        }

    }

    /**
     * Gets stock.
     *
     * @return the stock
     */
    @JsonGetter
    public ObservableList<Item> getStock() {
        return stock.get();
    }

    /**
     * Sets stock.
     *
     * @param items the items
     */
    @JsonIgnore
    public void setStock(ObservableList<Item> items) {
        this.stock.set(items);
    }

    /**
     * Sets stock.
     *
     * Used for JSON deserialization.
     *
     * @param items the items
     */
    @JsonSetter
    public void setStock(LinkedList<Item> items) {
        ListProperty<Item> temp = new SimpleListProperty<>(
                FXCollections.observableArrayList());

        temp.addAll(items);

        setStock(temp);
    }

    /**
     * Stock property list property.
     *
     * @return the list property
     */
    @JsonIgnore
    public ListProperty<Item> stockProperty() {
        return stock;
    }

}
