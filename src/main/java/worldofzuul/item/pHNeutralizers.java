package worldofzuul.item;

public class pHNeutralizers extends Item {
    private Double pHChange;

    public pHNeutralizers(){}
    public pHNeutralizers(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }
    public pHNeutralizers(String name, Double value, Double sellbackRate, Double pHChange){
        super(name,value,sellbackRate);
        this.pHChange = pHChange;
    }
    public Double getpHChange(){
        return pHChange;
    }
}
