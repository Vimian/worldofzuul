package worldofzuul;

import worldofzuul.item.*;
import java.util.HashMap;

public class Market {
   public HashMap<Item, Double> stock = new HashMap<Item, Double>();

   public Market(HashMap<Item, Double> stock){
       this.stock.put(fertilizer, 320.0);
       this.stock.put(harvester1, 0.0);
       this.stock.put(harvester2,230.0);
       this.stock.put(harvester3, 4000.0);
       this.stock.put(irrigator1, 320.0);
       this.stock.put(irrigator2, 300.0);
       this.stock.put(irrigator3, 300.0);
       this.stock.put(plant1, 2.0);
       this.stock.put(plant2, 2.0);
       this.stock.put(plant3, 4.0);
       this.stock.put(plant4, 10.0);
       this.stock.put(plant5, 20.0);
       this.stock.put(seed1, 13.0);
       this.stock.put(seed2, 32.0);
       this.stock.put(seed3, 42.0);
       this.stock.put(seed4, 93.0);
       this.stock.put(seed5, 10.0);
   }
   Harvester harvester1 = new Harvester("Hands", 0.0, 0.0);
   Harvester harvester2 = new Harvester("Sickle", 230.0, 0.0);
   Harvester harvester3 = new Harvester("Scythe", 2000.0,0.0);
    Fertilizer fertilizer = new Fertilizer("Fertilizer", 2, 320.0, 0.975);
    Irrigator irrigator1 = new Irrigator("Bucket", 320.0, 0.0);
    Irrigator irrigator2 = new Irrigator("Watering Can", 300.0, 0.0 );
    Irrigator irrigator3 = new Irrigator("Hose", 400.0, 0.0);
    Plant plant1 = new Plant("Corn", 2.0, 0.30);
    Plant plant2 = new Plant("Cashew", 2.0, 0.40);
    Plant plant3 = new Plant("Rice", 4.0, 0.20);
    Plant plant4 = new Plant("Mango", 10.0, 0.70);
    Plant plant5 = new Plant("Cowpea", 20.0, 0.01);
    Seed seed1 = new Seed("CornSeeds",5, 13.0, 0.98);
    Seed seed2 = new Seed("CashewSeeds", 5, 32.0, 0.98);
    Seed seed3 = new Seed("RiceSeeds" , 5, 42.0 , 0.98);
    Seed seed4 = new Seed("MangoSeeds", 5,93.0,0.98);
    Seed seed5 = new Seed("CowpeaSeeds",5,10.0, 0.98);

    public void purchaseItem(Item item, Player player) {
        if (player.getBalance() >= item.getValue()) {
            player.setBalance(player.getBalance() - item.getValue());
            player.getInventory().addItem(item);
        } else {
            System.out.println("Not enough money!");
        }
    }

    public void purchaseUpgradeToItems(Item item, Player player){
        if(player.getInventory().doesContain(irrigator1)){
            if (player.getBalance() >= item.getValue()){
                player.setBalance(player.getBalance() - item.getValue());
                player.getInventory().addItem(irrigator2);
                player.getInventory().removeItem(irrigator1);
            }
        }
        if(player.getInventory().doesContain(irrigator2)){
            if(player.getBalance() >= item.getValue()){
                player.setBalance(player.getBalance() - item.getValue());
                player.getInventory().addItem(irrigator3);
                player.getInventory().removeItem(irrigator2);
            }
        }
        if(player.getInventory().doesContain(harvester2)) {
            if (player.getBalance() >= item.getValue()) {
                player.setBalance(player.getBalance() - item.getValue());
                player.getInventory().addItem(harvester2);
                player.getInventory().removeItem(harvester1);
            }
        }
        if(player.getInventory().doesContain(harvester3)){
            if(player.getBalance() >= item.getValue()) {
                player.setBalance(player.getBalance() - item.getValue());
                player.getInventory().addItem(harvester3);
                player.getInventory().removeItem(harvester2);
            }
        }
        else {
            System.out.println("Cannot purchase that!!!");
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