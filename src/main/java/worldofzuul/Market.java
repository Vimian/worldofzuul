package worldofzuul;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.item.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Market {
    private final ListProperty<Item> stock = new SimpleListProperty<>(
            FXCollections.observableArrayList()
    );

    public Market() {

    }

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

    @JsonGetter
    public ObservableList<Item> getStock() {
        return stock.get();
    }

    @JsonIgnore
    public void setStock(ObservableList<Item> items) {
        this.stock.set(items);
    }

    @JsonSetter
    public void setStock(LinkedList<Item> items) {
        ListProperty<Item> temp = new SimpleListProperty<>(
                FXCollections.observableArrayList());

        temp.addAll(items);

        setStock(temp);
    }

    @JsonIgnore
    public ListProperty<Item> stockProperty() {
        return stock;
    }

}
