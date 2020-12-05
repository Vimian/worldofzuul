package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public interface ISellable {
    DoubleProperty value = new SimpleDoubleProperty();
    DoubleProperty sellBackRate = new SimpleDoubleProperty();



    default Double getValue() {
        return value.getValue();
    }
    default void setValue(Double newValue){
        value.setValue(newValue);
    }
    @JsonIgnore
    default DoubleProperty valueProperty(){
        return value;
    }
    default Double getSellBackRate() {
        return sellBackRate.getValue();
    }
    default void setSellBackRate(Double newValue){
        sellBackRate.setValue(newValue);
    }
    @JsonIgnore
    default DoubleProperty sellBackRateProperty(){
        return sellBackRate;
    }


}
