package worldofzuul;

import worldofzuul.item.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Market {
   public HashMap<Item, Double> stock;
   public ArrayList<Item> marketplace;
   public Item price;
   public boolean Bought = false;
   public boolean Sold = false;

   public Market(HashMap<Item, Double> stock, ArrayList<Item> marketplace, Item price){
       this.stock = stock;
       this.marketplace = marketplace;
       this.price = price;
   }
    public Double purchase(Item item){
       return stock.remove(item);
   }
   public Double sell(Item item){
       return stock.put(item);
   }
   public HashMap getItems(Item item){
       return stock.get(item);
   }
   public HashMap<Item, Double> getRates(){
       return stock.get();
   }
}

