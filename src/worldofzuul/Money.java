package worldofzuul;

public class Money extends Item {
    private float cash;

    public Money(String name, float cash) {
        super(name);
        this.cash = cash;
    }

    public float getCash() {
        return this.cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }
}
