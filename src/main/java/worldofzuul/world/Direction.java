package worldofzuul.world;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;


    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    Direction() {
    }
}
