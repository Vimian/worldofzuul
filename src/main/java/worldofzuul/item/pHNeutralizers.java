package worldofzuul.item;

public class pHNeutralizers extends Item {
    private Double pH;

    public pHNeutralizers(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }
    public pHNeutralizers(String name, Double value, Double sellbackRate, Double pH){
        super(name,value,sellbackRate);
        this.pH = pH;
    }
}
