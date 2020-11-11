package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import worldofzuul.Sprite;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public abstract class Item {
    private String name;
    private Sprite sprite;
    private Double value;
    private Double sellbackRate;


    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
