package worldofzuul.item;

public class pHNeutralizers extends Item {
    private Double pHChange;

    public pHNeutralizers() {
    }

    public pHNeutralizers(pHNeutralizers other) {
        super(other);
        this.pHChange = other.pHChange;
    }

    public pHNeutralizers(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }

    public pHNeutralizers(String name, Double value, Double sellbackRate, Double pHChange) {
        super(name, value, sellbackRate);
        this.pHChange = pHChange;
    }

    @Override
    public Item copyItem() {
        return new pHNeutralizers(this);
    }

    public Double getpHChange() {
        return pHChange;
    }
}
