package worldofzuul.item;

public class Fertilizer extends Item {
    private Double amount;

    public Fertilizer(String name, double amount) {
        super(name);
        this.amount = amount;
    }

    public double getAmount() { return this.amount; }

    public void setAmount(double amount) { this.amount = amount; }
}
