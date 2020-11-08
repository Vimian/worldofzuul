package worldofzuul;

import worldofzuul.item.Item;

import java.util.Collections;
import java.util.LinkedList;

public class Inventory {
    private LinkedList<Item> items = new LinkedList<>();

    public Item getSelectedItem() {
        if (!items.isEmpty()) {
            return items.getFirst();
        } else {
            return null;
        }
    }

    public void setSelectedItem(Item item) {
        if (items.contains(item)) {
            if (items.size() > 2) {
                Item selectedItem = getSelectedItem();
                if (selectedItem != item) {
                    Collections.swap(items, items.indexOf(getSelectedItem()), items.indexOf(item));
                }
            }
        } else {
            addItem(item);
            setSelectedItem(item);
        }
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public void removeItem(int index) {
        if (items.size() > 0 && items.size() > index) {
            this.items.remove(index);
        }
    }

    public Item getItem(int index){
        if (items.size() > 0 && items.size() > index) {
            return items.get(index);
        }
        return null;
    }

}
