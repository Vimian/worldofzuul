package worldofzuul;

import worldofzuul.Inventory;
import worldofzuul.item.Item;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

public class Market {
   public HashMap<Item, Double> stock2;
   public ArrayList<Market> stock;
   public Inventory stock;


   public Item price;
   public boolean Bought = false;
   public boolean Sold = false;

   public Market(ArrayList<Market> stock, Item price){
       this.stock = stock;
       this.price = price;
   }
    public Double purchaseItem(Market stock, Inventory item){
       if(Bought){
           Inventory I = new Inventory();
           I.addItem();
           this.stock.remove(stock);
       }
       return null;
   }

   public Double sellItem(Market item2, Inventory item){
       if(Sold){
           this.stock.add(item2);
           Inventory I = new Inventory();
           I.removeItem();
       }
           return null;
   }
    public ArrayList<Market> getStock(Market item2) {
        return stock;
    }
   public HashMap getItems(Item item){
       return stock.get(item);
   }
   public HashMap<Item, Double> getRates(){
       return stock.get();
   }
}

