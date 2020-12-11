package worldofzuul.item;


public class Fertilizer extends Item {


    public Fertilizer(){}

    public Fertilizer(Fertilizer fertilizer) {
        super(fertilizer);
    }

    @Override
    public Item copyItem() {
        return new Fertilizer(this);
    }

    public Fertilizer(String name, float amount) {
        super(name);
        this.setRemaining(amount);
    }

    public Fertilizer(String name, float amount, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }





}
