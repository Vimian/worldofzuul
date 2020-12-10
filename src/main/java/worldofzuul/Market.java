package worldofzuul;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.item.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Market {
    private final ListProperty<Item> stock = new SimpleListProperty<>(
            FXCollections.observableArrayList()
    );


    public Market() {

    }

   public Market(HashMap<Item, Double> stock){

       this.stock.addAll(Arrays.asList(
                fertilizer,
                harvester1,
                harvester2,
                harvester3,
                irrigator1,
                irrigator2,
                irrigator3,
                pHNeutralizers1,
                pHNeutralizers2,
                pHNeutralizers3,
                pHNeutralizers4,
                plant1,
                plant2,
                plant3,
                plant4,
                plant5,
                seed1,
                seed2,
                seed3,
                seed4,
                seed5
        ));




   }
   Harvester harvester1 = new Harvester("Cloves", 0.0, 0.0);
   Harvester harvester2 = new Harvester("Sickle", 230.0, 0.0);
   Harvester harvester3 = new Harvester("Scythe", 2000.0,0.0);
   Fertilizer fertilizer = new Fertilizer("Fertilizer", 2, 320.0, 0.975);
   Irrigator irrigator1 = new Irrigator("Bucket", 10,10, 320.0, 0.0);
   Irrigator irrigator2 = new Irrigator("Watering Can",10,50, 300.0, 0.0 );
   Irrigator irrigator3 = new Irrigator("Hose",100, 9999, 400.0, 0.0);
   pHNeutralizers pHNeutralizers1 = new pHNeutralizers("AgLimestone", 10.0,0.0);
   pHNeutralizers pHNeutralizers2 = new pHNeutralizers("HydratedLimestone",10.0,0.0);
   pHNeutralizers pHNeutralizers3 = new pHNeutralizers("Sulfur",10.0,0.0);
   pHNeutralizers pHNeutralizers4 = new pHNeutralizers("Aluminium sulfate",10.0,0.0);
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
        if(item != null){
            if (player.getBalance() >= item.getValue()) {
                player.setBalance(player.getBalance() - item.getValue());
                player.getInventory().addItem(item);
            } else {
                System.out.println("Not enough money!");
            }
        }
    }

    public void purchaseUpgradeToItems(Item item, Player player){
        if (item != null) {
            if (player.getInventory().getItems().contains(irrigator1)) {
                if (player.getBalance() >= item.getValue()) {
                    player.setBalance(player.getBalance() - item.getValue());
                    player.getInventory().addItem(irrigator2);
                    player.getInventory().removeItem(irrigator1);
                }
            }
            if (player.getInventory().getItems().contains(irrigator2)) {
                if (player.getBalance() >= item.getValue()) {
                    player.setBalance(player.getBalance() - item.getValue());
                    player.getInventory().addItem(irrigator3);
                    player.getInventory().removeItem(irrigator2);
                }
            }
            if (player.getInventory().getItems().contains(harvester2)) {
                if (player.getBalance() >= item.getValue()) {
                    player.setBalance(player.getBalance() - item.getValue());
                    player.getInventory().addItem(harvester2);
                    player.getInventory().removeItem(harvester1);
                }
            }
            if (player.getInventory().getItems().contains(harvester3)) {
                if (player.getBalance() >= item.getValue()) {
                    player.setBalance(player.getBalance() - item.getValue());
                    player.getInventory().addItem(harvester3);
                    player.getInventory().removeItem(harvester2);
                }
            } else {
                System.out.println("Cannot purchase that!!!");
            }
        } else {
            return;
        }
    }

    @JsonIgnore
   public void sellItem(Item item, Player player){
        if(player.getInventory().getItems().contains(item) && item != null) {
            player.setBalance(player.getBalance() + item.getValue() * item.getSellBackRate());
            player.getInventory().removeItem(item);
        }
        else {
            System.out.println("Player does not have that item");
        }
    }
    @JsonIgnore
   public Double getItems(Item item){
        if(item != null){
            for (Item value : stock) {
                System.out.println(value);
            }
            return  item.getValue();
        }
        else {
            return 0d;
        }

   }


    @JsonGetter
    public ObservableList<Item> getStock() {
        return stock.get();
    }

    @JsonIgnore
    public ListProperty<Item> stockProperty() {
        return stock;
    }

    @JsonIgnore
    public void setStock(ObservableList<Item> items) {
        this.stock.set(items);
    }

    @JsonSetter
    public void setStock(LinkedList<Item> items) {
        ListProperty<Item> temp = new SimpleListProperty<>(
                FXCollections.observableArrayList());

        temp.addAll(items);

        setStock(temp);
    }


   /* public void setStock(List<Item> stock) {
        this.stock = stock;
    }

    */
}
