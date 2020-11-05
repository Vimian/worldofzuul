package worldofzuul;

public abstract class GameObject {
    public boolean colliding;

    public void uponEntry(){

    }
    public Command[] uponEntry(GameObject previousGameObject) {
        return null;
    }

    public void uponExit(){

    }
}
