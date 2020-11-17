package worldofzuul;

import worldofzuul.item.Item;
import worldofzuul.MarketInventory;
import java.util.ArrayList;
import java.util.HashMap;

public class Market {
   public HashMap<Item, Double> stock2;
   public ArrayList<MarketInventory> stock;
   public Item price;
   public boolean Bought = false;
   public boolean Sold = false;

   public Market(ArrayList<MarketInventory> stock, Item price){
       this.stock = stock;
       this.price = price;
   }
    public Double purchaseItem(MarketInventory item){
       if(Bought){
           MarketInventory MI = new MarketInventory();
           MI.removeItemFromStock(item);
           Inventory I = new Inventory();
           I.addItem(item);
       }
       return null;
   }
   public Double sellItem(MarketInventory item){
       if(Sold){
           Inventory I = new Inventory();
           I.removeItem(item);
           MarketInventory MI = new MarketInventory();
           MI.addItemToStock(item);
       }
           return null;
   }
   public HashMap getItems(Item item){
       return stock.get(item);
   }
   public HashMap<Item, Double> getRates(){
       return stock.get();
   }
}

