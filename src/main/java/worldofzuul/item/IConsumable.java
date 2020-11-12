package worldofzuul.item;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

public interface IConsumable {

    FloatProperty remaining = new SimpleFloatProperty();
    FloatProperty capacity = new SimpleFloatProperty();
    FloatProperty consumptionRate = new SimpleFloatProperty();

    default float getConsumptionRate(){
        return consumptionRate.get();
    }
    default void setConsumptionRate(float value){
        consumptionRate.set(value);
    }
    default float getCapacity(){
        return capacity.get();
    }
    default void setCapacity(float value){
        capacity.set(value);
    }

    default void refill() {
        setRemaining(getCapacity());
    }
    default float deplete() {
        float depletionAmount = 0;
        if (getRemaining() > getConsumptionRate()) {
            setRemaining(getRemaining() - getConsumptionRate());
            depletionAmount = getConsumptionRate();
        } else if (getRemaining() > 0) {
            depletionAmount  = getRemaining();
            setRemaining(0);
        }

        return depletionAmount;
    }
    default boolean deplete(float amount){
        if(amount >= getRemaining()){
            setRemaining(getRemaining() - amount);
            return true;
        } else{
            return false;
        }
    }

    default Float getRemaining() {
        return remaining.get();
    }
    default void setRemaining(float value){
        remaining.set(value);
    }
    default FloatProperty remainingProperty(){
        return remaining;
    }
}
