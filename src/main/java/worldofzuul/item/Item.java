package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.beans.property.*;
import worldofzuul.Sprite;

import java.util.Objects;


@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.WRAPPER_OBJECT, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=Seed.class, name="Seed"),
        @JsonSubTypes.Type(value=Harvester.class, name="Harvester"),
        @JsonSubTypes.Type(value=Fertilizer.class, name="Fertilizer"),
        @JsonSubTypes.Type(value=Irrigator.class, name="Irrigator"),
        @JsonSubTypes.Type(value=Money.class, name="Money"),
        @JsonSubTypes.Type(value=Plant.class, name="Plant"),
})
public abstract class Item extends Sprite {
    private final StringProperty name = new SimpleStringProperty();
    private final DoubleProperty value = new SimpleDoubleProperty();
    private final DoubleProperty sellBackRate = new SimpleDoubleProperty(1);
    FloatProperty remaining = new SimpleFloatProperty(1);
    FloatProperty capacity = new SimpleFloatProperty(1);
    FloatProperty consumptionRate = new SimpleFloatProperty(0);

    public Item(){}
    public Item(String name) {
        setName(name);
    }

    public Item(String name, double value, double sellBackRate){
        this(name);
        this.value.setValue(value);
        this.sellBackRate.setValue(sellBackRate);
    }

    public Item(String name, double value, double sellBackRate, float remaining, float capacity, float consumptionRate) {
        this(name, consumptionRate, remaining, capacity);
        this.setValue(value);
        this.setSellBackRate(sellBackRate);


    }

    public Item(String name, float consumptionRate, float remaining, float capacity) {
        this(name);
        this.remaining.setValue(remaining);
        this.capacity.setValue(capacity);
        this.consumptionRate.setValue(consumptionRate);
    }


    public String getName() {
        return name.get();
    }

    @JsonIgnore
    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String toString() {
        return getName();
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
    public void setRemaining(float value) {
        remaining.set(value);
    }

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
        if(amount <= getRemaining()){
            setRemaining(getRemaining() - amount);
            return true;
        } else{
            return false;
        }
    }
    public void refill() {
        setRemaining(getCapacity());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(name.get(), item.name.get()) &&
                Objects.equals(value.get(), item.value.get()) &&
                Objects.equals(sellBackRate.get(), item.sellBackRate.get()) &&
                Objects.equals(capacity.get(), item.capacity.get()) &&
                Objects.equals(consumptionRate.get(), item.consumptionRate.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, sellBackRate, capacity, consumptionRate);
    }
}
