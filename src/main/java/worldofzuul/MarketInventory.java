package worldofzuul;

import worldofzuul.item.Item;
import worldofzuul.item.Money;
import java.util.ArrayList;
import java.util.Collection;

public class MarketInventory {
    public ArrayList<MarketInventory> Items;
    public Money price;
    public Item items;

    public ArrayList<MarketInventory> removeItems() {
        return this.Items.remove(items);
    }
    public ArrayList<MarketInventory> addItems() {
        return this.Items.add(items);
    }

    public ArrayList<MarketInventory> getItems(Item item) {
        return Items;
    }

    public Money getPrice(Item item) {
        return price;
    }
}

