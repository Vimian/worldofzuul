package sdu.student.editor.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import worldofzuul.world.Door;

public class DoorModel extends GameObjectModel {

    private final Door door;
    private final StringProperty exit = new SimpleStringProperty();
    private final IntegerProperty xCoord = new SimpleIntegerProperty();
    private final IntegerProperty yCoord = new SimpleIntegerProperty();


    public DoorModel(Door door) {
        super(door);
        this.door = door;
        exit.set(door.getExit());
        xCoord.set(door.getLinkedLocation().getX());
        yCoord.set(door.getLinkedLocation().getY());

    }


    public Door getDoor() {
        return door;
    }

    public String getExit() {
        return exit.get();
    }

    public void setExit(String exit) {
        this.door.setExit(exit);
        this.exit.set(exit);
    }

    public StringProperty exitProperty() {
        return exit;
    }

    public int getxCoord() {
        return xCoord.get();
    }

    public void setxCoord(int xCoord) {
        this.door.getLinkedLocation().setX(xCoord);
        this.xCoord.set(xCoord);
    }

    public IntegerProperty xCoordProperty() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord.get();
    }

    public void setyCoord(int yCoord) {
        this.door.getLinkedLocation().setY(yCoord);
        this.yCoord.set(yCoord);
    }

    public IntegerProperty yCoordProperty() {
        return yCoord;
    }
}
