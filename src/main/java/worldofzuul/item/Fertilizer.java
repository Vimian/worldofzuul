package worldofzuul.item;


public class Fertilizer extends Item {


    public Fertilizer(){}
    public Fertilizer(String name, float amount) {
        super(name);
        this.setRemaining(amount);
    }

    public Fertilizer(String name, float amount, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }




}
