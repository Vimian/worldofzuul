package worldofzuul;

import worldofzuul.item.Item;
import java.util.ArrayList;
import java.util.HashMap;

public class Market {
   public HashMap<Item, Double> stock2;
   public ArrayList<Market> stock;
   public Item price;
   public boolean Bought = false;
   public boolean Sold = false;

   public Market(ArrayList<Market> stock, Item price){
       this.stock = stock;
       this.price = price;
   }
    public Double purchaseItem(Market item, Inventory item2){
       if(Bought){
           this.stock.remove(item);
           item = item2;
           Inventory I = new Inventory();
           I.addItem(item2);
       }
       return null;
   }
    public ArrayList<Market> getStock(Market item) {
       return stock;
   }
   public Double sellItem(Market item, Inventory item2){
       if(Sold){
           Inventory I = new Inventory();
           I.removeItem(item2);
           this.item2 = item;
           Market MI = new Market();
           this.stock.add(item);
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

