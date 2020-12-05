package worldofzuul.item;

public class Fertilizer extends Item implements ISellable, IConsumable {

    public Fertilizer(){}
    public Fertilizer(String name, float amount) {
        super(name);
        this.setRemaining(amount);
    }

    public Fertilizer(String name, float amount, Double value, Double sellbackRate) {
        this(name, amount);
        setValue(value);
        setSellBackRate(sellbackRate);
    }

}
