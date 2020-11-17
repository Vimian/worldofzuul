package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import worldofzuul.Sprite;
import worldofzuul.SpriteAnimation;


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
    private String name;
    private Double value;
    private Double sellbackRate;

    public Item(){}
    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
