package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;

public abstract class Consumable extends Sellable {

    FloatProperty remaining = new SimpleFloatProperty();
    FloatProperty capacity = new SimpleFloatProperty();
    FloatProperty consumptionRate = new SimpleFloatProperty(1);
    DoubleProperty sellValue = new SimpleDoubleProperty();


    public Consumable() {
    }

    public Consumable(String name) {
        super(name);
    }

    public Consumable(String name, double value, double sellBackRate) {
        super(name, value, sellBackRate);
    }

    public Consumable(String name, float remaining, float capacity, float consumptionRate) {
        this(name, 0, 0, remaining, capacity, consumptionRate);
    }

    public Consumable(String name, double value, double sellBackRate, float remaining, float capacity, float consumptionRate) {
        this(name);
        this.remaining.setValue(remaining);
        this.capacity.setValue(capacity);
        this.consumptionRate.setValue(consumptionRate);
        this.sellValue.setValue(value*sellBackRate);
    }


    public float getConsumptionRate(){
        return consumptionRate.get();
    }
    public void setConsumptionRate(float value){
        consumptionRate.set(value);
    }
    public float getCapacity(){
        return capacity.get();
    }
    public void setCapacity(float value){
        capacity.set(value);
    }
    public Float getRemaining() {
        return remaining.get();
    }
    public void setRemaining(float value) {remaining.set(value); }
    public void setSellValue(double value) {sellValue.set(value);}
    public double getSellValue() {return sellValue.get();}

    @JsonIgnore
    public FloatProperty remainingProperty() {
        return remaining;
    }
    @JsonIgnore
    public FloatProperty capacityProperty() {
        return capacity;
    }
    @JsonIgnore
    public FloatProperty consumptionRateProperty() {
        return consumptionRate;
    }

    @JsonIgnore
    public float deplete() {
        float depletionAmount = 0;
        if (getRemaining() > getConsumptionRate()) {
            setRemaining(getRemaining() - getConsumptionRate());
            depletionAmount = getConsumptionRate();
        } else if (getRemaining() > 0) {
            depletionAmount = getRemaining();
            setRemaining(0);
        }

        return depletionAmount;
    }
    @JsonIgnore
    public boolean deplete(float amount){
        if(amount >= getRemaining()){
            setRemaining(getRemaining() - amount);
            return true;
        } else{
            return false;
        }
    }
    public void refill() {
        setRemaining(getCapacity());
    }

}
