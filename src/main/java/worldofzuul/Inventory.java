package worldofzuul;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.item.Item;

import java.util.LinkedList;

public class Inventory {
    private final ListProperty<Item> items = new SimpleListProperty<>(
            FXCollections.observableArrayList());
    private Item selectedItem;

    @JsonIgnore
    public Item getSelectedItem() {
        if (selectedItem != null) {
            return selectedItem;
        } else if (!getItems().isEmpty()) {
            setSelectedItem(getItems().stream().findFirst().orElseThrow());
            return getSelectedItem();
        } else {
            return null;
        }
    }

    public void setSelectedItem(Item item) {
        if (getItems().contains(item)) {
            if (getItems().size() > 2) {
                selectedItem = item;
            }
        } else {
            addItem(item);
            setSelectedItem(item);
        }
    }


    public void addItem(Item item) {
        this.getItems().add(item);
    }

    public void removeItem(Item item) {
        this.getItems().remove(item);
    }

    public void removeItem(int index) {
        if (getItems().size() > 0 && getItems().size() > index) {
            this.getItems().remove(index);
        }
    }

    public Item getItem(int index) {
        if (getItems().size() > 0 && getItems().size() > index) {
            return getItems().get(index);
        }
        return null;
    }

    @JsonGetter
    public ObservableList<Item> getItems() {
        return items.get();
    }

    @JsonSetter
    public void setItems(LinkedList<Item> items) {
        ListProperty<Item> temp = new SimpleListProperty<>(
                FXCollections.observableArrayList());

        temp.addAll(items);

        setItems(temp);
        setSelectedItem(temp.get(0));
    }

    @JsonIgnore
    public void setItems(ObservableList<Item> items) {
        this.items.set(items);
    }

    @JsonIgnore
    public ListProperty<Item> itemsProperty() {
        return items;
    }

}
