package worldofzuul.item;

public class Money extends Consumable {

    public Money(){}
    public Money(String name, float cash) {
        super(name);
        this.setRemaining(cash);
    }
}
