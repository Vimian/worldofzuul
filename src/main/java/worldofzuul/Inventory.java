package worldofzuul;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.item.Item;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The type Inventory.
 *
 * Manages the {@link Item} inventory system of the {@link Player}.
 *
 */
public class Inventory {
    /**
     * The Items.
     */
    private final ListProperty<Item> items = new SimpleListProperty<>(
            FXCollections.observableArrayList());
    /**
     * The Selected item.
     */
    private final Property<Item> selectedItem = new SimpleObjectProperty<>();
    /**
     * The Selected item name.
     */
    private final StringProperty selectedItemName = new SimpleStringProperty("Nothing");

    /**
     * Gets selected item.
     *
     * @return the selected item
     */
    @JsonIgnore
    public Item getSelectedItem() {
        if (selectedItem.getValue() != null) {
            return selectedItem.getValue();
        } else if (!getItems().isEmpty()) {
            setSelectedItem(getItems().stream().findFirst().orElseThrow());
            return getSelectedItem();
        } else {
            return null;
        }
    }

    /**
     * Sets selected item.
     *
     * @param item the item
     */
    @JsonIgnore
    public void setSelectedItem(Item item) {
        if (getItems().contains(item)) {
            if (getItems().size() > 2) {
                selectedItem.setValue(item);
                setSelectedItemName(getSelectedItem().getName());
            }
        } else if (item == null) {

            selectedItem.setValue(null);
            setSelectedItemName("Nothing");
        } else {
            addItem(item);
            setSelectedItem(item);
        }
    }

    /**
     * Unselect item.
     */
    public void unselectItem() {
        setSelectedItem(null);
    }

    /**
     * Selected item property property.
     *
     * @return the property
     */
    @JsonIgnore
    public Property<Item> selectedItemProperty() {
        return selectedItem;
    }

    /**
     * Gets selected item name.
     *
     * @return the selected item name
     */
    @JsonIgnore
    public String getSelectedItemName() {
        return selectedItemName.get();
    }

    /**
     * Sets selected item name.
     *
     * @param selectedItemName the selected item name
     */
    @JsonIgnore
    public void setSelectedItemName(String selectedItemName) {
        this.selectedItemName.set(selectedItemName);
    }

    /**
     * Selected item name property string property.
     *
     * @return the string property
     */
    @JsonIgnore
    public StringProperty selectedItemNameProperty() {
        return selectedItemName;
    }

    /**
     * Add item.
     *
     * @param item the item
     */
    public void addItem(Item item) {
        AtomicReference<Boolean> addItem = new AtomicReference<>(true);


        items.forEach(item1 -> {
            if (item1.equals(item)) {
                if (item1.getRemaining() < item1.getCapacity()) {
                    var newVal = item1.getRemaining() + item.getRemaining();
                    var newItemVal = newVal - item1.getCapacity();

                    if (newItemVal <= 0) {
                        item1.setRemaining(newVal);
                        addItem.set(false);
                    } else {
                        item.setRemaining(newVal - newItemVal);
                        item1.setRemaining(newItemVal);
                    }

                }
            }
        });

        if (addItem.get()) {
            this.getItems().add(item);
        }


    }

    /**
     * Remove item.
     *
     * @param item the item
     */
    public void removeItem(Item item) {
        this.getItems().remove(item);
    }

    /**
     * Remove item.
     *
     * @param index the index
     */
    public void removeItem(int index) {
        if (getItems().size() > 0 && getItems().size() > index) {
            this.getItems().remove(index);
        }
    }

    /**
     * Gets item.
     *
     * @param index the index
     * @return the item
     */
    public Item getItem(int index) {
        if (getItems().size() > 0 && getItems().size() > index) {
            return getItems().get(index);
        }
        return null;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    @JsonGetter
    public ObservableList<Item> getItems() {
        return items.get();
    }

    /**
     * Sets items.
     *
     * Used for JSON deserialization.
     *
     * @param items the items
     */
    @JsonSetter
    public void setItems(LinkedList<Item> items) {
        ListProperty<Item> temp = new SimpleListProperty<>(
                FXCollections.observableArrayList());

        temp.addAll(items);

        setItems(temp);
        setSelectedItem(temp.get(0));
    }

    /**
     * Sets items.
     *
     * @param items the items
     */
    @JsonIgnore
    public void setItems(ObservableList<Item> items) {
        this.items.set(items);
    }

    /**
     * Items property list property.
     *
     * @return the list property
     */
    @JsonIgnore
    public ListProperty<Item> itemsProperty() {
        return items;
    }

}
