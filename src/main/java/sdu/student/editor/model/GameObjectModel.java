package sdu.student.editor.model;

import javafx.beans.property.*;
import worldofzuul.world.GameObject;

public abstract class GameObjectModel {

    private final BooleanProperty colliding = new SimpleBooleanProperty();
    private final StringProperty defaultImageFile = new SimpleStringProperty();
    private final IntegerProperty animationCycleLengthMillis = new SimpleIntegerProperty();
    private GameObject gameObject;


    public GameObjectModel(GameObject gameObject) {
        this.gameObject = gameObject;
        colliding.set(gameObject.isColliding());
        defaultImageFile.set(gameObject.getDefaultImageFile());
        animationCycleLengthMillis.set(gameObject.getAnimationCycleLengthMillis());
    }


    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public final Boolean getColliding() {
        return colliding.get();
    }

    public void setColliding(boolean colliding) {
        this.gameObject.setColliding(colliding);
        this.colliding.set(colliding);
    }

    public BooleanProperty collidingProperty() {
        return colliding;
    }

    public final String getDefaultImageFile() {
        return defaultImageFile.get();
    }

    public final void setDefaultImageFile(Boolean value) {
        gameObject.setColliding(value);
        colliding.set(value);
    }

    public final void setDefaultImageFile(String value) {
        gameObject.setDefaultImageFile(value);
        defaultImageFile.set(value);
    }

    public StringProperty defaultImageFileProperty() {
        return defaultImageFile;
    }

    public final Integer getAnimationCycleLengthMillis() {
        return animationCycleLengthMillis.get();
    }

    public final void setAnimationCycleLengthMillis(Integer value) {
        gameObject.setAnimationCycleLengthMillis(value);
        animationCycleLengthMillis.set(value);
    }

    public IntegerProperty animationCycleLengthMillisProperty() {
        return animationCycleLengthMillis;
    }
}
