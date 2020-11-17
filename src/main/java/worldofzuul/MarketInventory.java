package worldofzuul;

import java.util.ArrayList;
import java.util.Collection;

public class MarketInventory {
    private ArrayList<MarketInventory> stock = new ArrayList<>();

    public void removeItemsFromStock(MarketInventory item) { this.stock.remove(item); }
    public void addItemsToStock(MarketInventory item) {  this.stock.add(item); }

    public ArrayList<MarketInventory> getStock(MarketInventory item) { return stock; }
}

