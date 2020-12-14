package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.beans.property.*;
import sdu.student.FXMLController;
import worldofzuul.Player;
import worldofzuul.SpriteAnimation;

import java.util.Objects;


/**
 * The type Item.
 *
 * This abstract class determines the Item that can be used by the {@link worldofzuul.Player} to sold, purchased, turned in using a quest or interacted with upon a {@link worldofzuul.world.GameObject}.
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Seed.class, name = "Seed"),
        @JsonSubTypes.Type(value = Harvester.class, name = "Harvester"),
        @JsonSubTypes.Type(value = Fertilizer.class, name = "Fertilizer"),
        @JsonSubTypes.Type(value = Irrigator.class, name = "Irrigator"),
        @JsonSubTypes.Type(value = Plant.class, name = "Plant"),
        @JsonSubTypes.Type(value = pHNeutralizers.class, name = "pHNeutralizers")
})
public abstract class Item extends SpriteAnimation {
    /**
     * The Name.
     */
    private final StringProperty name = new SimpleStringProperty();
    /**
     * The Value.
     */
    private final DoubleProperty value = new SimpleDoubleProperty();
    /**
     * The Sell back rate.
     */
    private final DoubleProperty sellBackRate = new SimpleDoubleProperty(1);
    /**
     * The Remaining.
     */
    FloatProperty remaining = new SimpleFloatProperty(1);
    /**
     * The Capacity.
     */
    FloatProperty capacity = new SimpleFloatProperty(1);
    /**
     * The Consumption rate.
     *
     * Amount to consume by default using {@link Item#deplete()}.
     *
     */
    FloatProperty consumptionRate = new SimpleFloatProperty(0);

    /**
     * Instantiates a new Item.
     */
    public Item() {
    }

    /**
     * Instantiates a new Item.
     *
     * @param name the name
     */
    public Item(String name) {
        setName(name);
    }

    /**
     * Instantiates a new Item.
     *
     * @param name         the name
     * @param value        the value
     * @param sellBackRate the sell back rate
     */
    public Item(String name, double value, double sellBackRate) {
        this(name);
        this.value.setValue(value);
        this.sellBackRate.setValue(sellBackRate);
    }

    /**
     * Instantiates a new Item.
     *
     * @param name            the name
     * @param value           the value
     * @param sellBackRate    the sell back rate
     * @param remaining       the remaining
     * @param capacity        the capacity
     * @param consumptionRate the consumption rate
     */
    public Item(String name, double value, double sellBackRate, float remaining, float capacity, float consumptionRate) {
        this(name, consumptionRate, remaining, capacity);
        this.setValue(value);
        this.setSellBackRate(sellBackRate);


    }


    /**
     * Instantiates a new Item.
     *
     * @param name            the name
     * @param consumptionRate the consumption rate
     * @param remaining       the remaining
     * @param capacity        the capacity
     */
    public Item(String name, float consumptionRate, float remaining, float capacity) {
        this(name);
        this.remaining.setValue(remaining);
        this.capacity.setValue(capacity);
        this.consumptionRate.setValue(consumptionRate);
    }

    /**
     * Instantiates a new Item using a copy-constructor.
     *
     * @param other the other
     */
    public Item(Item other) {
        this(other.nameProperty().get(), other.getValue(), other.getSellBackRate(), other.getRemaining(), other.getCapacity(), other.getConsumptionRate());
    }

    /**
     * Creates a new Item and copy relevant values of attributes.
     *
     * @return the item
     */
    public abstract Item copyItem();

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Name property string property.
     *
     * @return the string property
     */
    @JsonIgnore
    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }


    /**
     * Gets value.
     *
     * @return the value
     */
    public double getValue() {
        return value.get();
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(double value) {
        this.value.set(value);
    }

    /**
     * Value property double property.
     *
     * @return the double property
     */
    @JsonIgnore
    public DoubleProperty valueProperty() {
        return value;
    }

    /**
     * Gets sell back rate.
     *
     * @return the sell back rate
     */
    public double getSellBackRate() {
        return sellBackRate.get();
    }

    /**
     * Sets sell back rate.
     *
     * @param sellBackRate the sell back rate
     */
    public void setSellBackRate(double sellBackRate) {
        this.sellBackRate.set(sellBackRate);
    }

    /**
     * Sell back rate property double property.
     *
     * @return the double property
     */
    @JsonIgnore
    public DoubleProperty sellBackRateProperty() {
        return sellBackRate;
    }

    /**
     * Gets consumption rate.
     *
     * @return the consumption rate
     */
    public float getConsumptionRate() {
        return consumptionRate.get();
    }

    /**
     * Sets consumption rate.
     *
     * @param value the value
     */
    public void setConsumptionRate(float value) {
        consumptionRate.set(value);
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public float getCapacity() {
        return capacity.get();
    }

    /**
     * Sets capacity.
     *
     * @param value the value
     */
    public void setCapacity(float value) {
        capacity.set(value);
    }

    /**
     * Gets remaining.
     *
     * @return the remaining
     */
    public Float getRemaining() {
        return remaining.get();
    }

    /**
     * Sets remaining.
     *
     * @param value the value
     */
    public void setRemaining(float value) {
        remaining.set(value);
    }

    /**
     * Remaining property float property.
     *
     * @return the float property
     */
    @JsonIgnore
    public FloatProperty remainingProperty() {
        return remaining;
    }

    /**
     * Capacity property float property.
     *
     * @return the float property
     */
    @JsonIgnore
    public FloatProperty capacityProperty() {
        return capacity;
    }

    /**
     * Consumption rate property float property.
     *
     * @return the float property
     */
    @JsonIgnore
    public FloatProperty consumptionRateProperty() {
        return consumptionRate;
    }

    /**
     * Depletes the amount in {@link Item#remaining} using {@link Item#consumptionRate}.
     *
     * If {@link Item#remaining} is smaller than {@link Item#consumptionRate} then subtract value until it reaches 0.
     *
     * @return value subtracted from {@link Item#remaining}
     */
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

    /**
     * Deplete by specified amount as long as amount it smaller than or equal to {@link Item#remaining}.
     *
     * @param amount the amount to deplete
     */
    @JsonIgnore
    public void deplete(float amount) {
        if (amount <= getRemaining()) {
            setRemaining(getRemaining() - amount);
        }
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
