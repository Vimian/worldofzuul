package worldofzuul.item;

public class Money extends Item implements IConsumable {

    public Money(){}
    public Money(String name, float cash) {
        super(name);
        this.setRemaining(cash);
    }
}
