package worldofzuul.world;

/**
 * The enum Direction.
 */
public enum Direction {
    /**
     * North direction.
     */
    NORTH,
    /**
     * South direction.
     */
    SOUTH,
    /**
     * East direction.
     */
    EAST,
    /**
     * West direction.
     */
    WEST;


    /**
     * Instantiates a new Direction.
     */
    Direction() {
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
