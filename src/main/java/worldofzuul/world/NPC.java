package worldofzuul.world;

import worldofzuul.item.Item;
import worldofzuul.item.Plant;
import worldofzuul.parsing.Command;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class NPC extends GameObject {

    private final HashMap<String, Integer> turnInProgress = new LinkedHashMap<>();

    private HashMap<Item, HashMap<String, Integer>> itemTurnIns = new LinkedHashMap<>();
    private LinkedList<String> dialog = new LinkedList<>();

    @Override
    public Command[] interact() {
        return super.interact();
    }

    @Override
    public Command[] interact(Item item) {

        if(itemTurnIns.containsKey(item)){
            handleTurnIn(item, itemTurnIns.get(item));
        }




        return super.interact(item);
    }

    private void handleTurnIn(Item item, HashMap<String, Integer> integerStringHashMap) {
        int turnInLevel = 0;

        if (!turnInProgress.containsKey(item.getName())) {
            turnInProgress.put(item.getName(), turnInLevel);
        }

        if(item instanceof Plant){
        }


    }

    public HashMap<Item, HashMap<String, Integer>> getItemTurnIns() {
        return itemTurnIns;
    }

    public void setItemTurnIns(HashMap<Item, HashMap<String, Integer>> itemTurnIns) {
        this.itemTurnIns = itemTurnIns;
    }

    public LinkedList<String> getDialog() {
        return dialog;
    }

    public void setDialog(LinkedList<String> dialog) {
        this.dialog = dialog;
    }
}
