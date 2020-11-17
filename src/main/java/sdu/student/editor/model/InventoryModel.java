package sdu.student.editor.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.Inventory;
import worldofzuul.item.Item;

import java.util.Collections;

public class InventoryModel {
    private final ListProperty<Item> items = new SimpleListProperty<>(
            FXCollections.observableArrayList());

    private final Inventory inventory;

    public InventoryModel(Inventory inventory) {
        this.inventory = inventory;
        items.addAll(inventory.getItems());


    }

    public void setSelectedItem(Item item) {
        if (items.contains(item)) {
            if (items.size() > 2) {
                Item selectedItem = inventory.getSelectedItem();
                if (selectedItem != item) {
                    Collections.swap(inventory.getItems(), inventory.getItems().indexOf(inventory.getSelectedItem()), inventory.getItems().indexOf(item));
                }
            }
        } else {
            items.add(item);
            setSelectedItem(item);
        }
    }
    public ObservableList<Item> getItems() {
        return items.get();
    }

    public ListProperty<Item> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<Item> items) {
        inventory.getItems().clear();
        items.forEach(item -> inventory.addItem(item));

        this.items.set(items);
    }
}
