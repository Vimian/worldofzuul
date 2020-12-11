package worldofzuul.world;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;


    Direction() {
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
