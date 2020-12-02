package worldofzuul;

import worldofzuul.item.*;
import java.util.HashMap;

public class Market {
   public HashMap<Item, Double> stock = new HashMap<Item, Double>();
   public Inventory Market;


   public Item price;
   public boolean Bought = false;
   public boolean Sold = false;

   public Market(Item price){
       this.price = price;
       this.stock.put(harvester, 20.0);
   }
    Harvester harvester = new Harvester("Harvester");
    Fertilizer fertilizer = new Fertilizer("Fertilizer", 2);

    public void purchaseItem(Item item, Player player) {
        if (player.getBalance() >= item.getValue()) {
            player.setBalance(player.getBalance() - item.getValue());
            player.getInventory().addItem(item);
        } else {
            System.out.println("Not enough money!");
        }
    }

   public void sellItem(Item item, Player player){
        if(player.getInventory().doesContain(item)) {
            player.setBalance(player.getBalance() + (item.getValue() * item.getSellbackRate()));
            player.getInventory().removeItem(item);
        }
        else {
            System.out.println("Player does not have that item");
        }
    }
   public Double getItems(Item item){

        for(int i = 0; i< stock.keySet().toArray().length; i++){
            System.out.println(stock.keySet().toArray()[i]);
        }
       return stock.get(item);
   }
}
