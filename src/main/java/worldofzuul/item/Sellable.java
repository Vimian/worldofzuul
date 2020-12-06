package worldofzuul.item;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


public abstract class Sellable extends Item {
    private final DoubleProperty value = new SimpleDoubleProperty();
    private final DoubleProperty sellBackRate = new SimpleDoubleProperty();

    public Sellable() {
        super();
    }

    public Sellable(String name) {
        super(name);
    }

    public Sellable(String name, double value, double sellBackRate){
        this(name);
        this.value.setValue(value);
        this.sellBackRate.setValue(sellBackRate);
    }

    public double getValue() {
        return value.get();
    }

    @JsonIgnore
    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public double getSellBackRate() {
        return sellBackRate.get();
    }
    @JsonIgnore
    public DoubleProperty sellBackRateProperty() {
        return sellBackRate;
    }

    public void setSellBackRate(double sellBackRate) {
        this.sellBackRate.set(sellBackRate);
    }
}
