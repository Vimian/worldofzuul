package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import worldofzuul.Sprite;


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
    private StringProperty name = new SimpleStringProperty();

    public Item(){}
    public Item(String name) {
        setName(name);
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


}
