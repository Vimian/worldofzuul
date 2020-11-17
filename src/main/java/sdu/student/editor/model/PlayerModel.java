package sdu.student.editor.model;

import worldofzuul.Player;

public class PlayerModel {
    private Player player;
    private InventoryModel inventoryModel;


    public PlayerModel(Player player) {
        this.player = player;

        inventoryModel = new InventoryModel(player.getInventory());
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public InventoryModel getInventoryModel() {
        return inventoryModel;
    }

    public void setInventoryModel(InventoryModel inventoryModel) {
        this.inventoryModel = inventoryModel;
    }
}
