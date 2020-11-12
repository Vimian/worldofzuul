package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
    default Float getRemaining() {
        return remaining.get();
    }
    default void setRemaining(float value){
        remaining.set(value);
    }
    @JsonIgnore
    default FloatProperty remainingProperty(){
        return remaining;
    }

    @JsonIgnore
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
    @JsonIgnore
    default boolean deplete(float amount){
        if(amount >= getRemaining()){
            setRemaining(getRemaining() - amount);
            return true;
        } else{
            return false;
        }
    }
    default void refill() {
        setRemaining(getCapacity());
    }

}
