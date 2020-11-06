package worldofzuul;

import java.util.Collections;
import java.util.LinkedList;

public class Inventory {
    private LinkedList<Item> items = new LinkedList<>();

    public Item getSelectedItem() {
        return items.getFirst();
    }
    public void setSelectedItem(Item item) {
        Collections.swap(items, items.indexOf(getSelectedItem()), items.indexOf(item));
    }
    public LinkedList<Item> getItems() {
        return items;
    }
    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }
}
