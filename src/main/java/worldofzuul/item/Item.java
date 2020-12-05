package worldofzuul.item;

import worldofzuul.Sprite;

public abstract class Item {
    private String name;
    private Sprite sprite;
    private Double value;
    private Double sellbackRate;

    public Item(String name, Double value, Double sellbackRate) {
        this.name = name;
        this.value = value;
        this.sellbackRate = sellbackRate;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    public Double getSellbackRate() {
        return sellbackRate;
    }
}
