package worldofzuul.item;
import worldofzuul.item.*;
public class pHNeutrilizers extends Item {
    private Double pH;

    public pHNeutrilizers(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }
    public pHNeutrilizers(String name, Double value, Double sellbackRate, Double pH){
        super(name,value,sellbackRate);
        this.pH = pH;
    }
}
