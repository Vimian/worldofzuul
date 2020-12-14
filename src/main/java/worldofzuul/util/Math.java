package worldofzuul.util;

import worldofzuul.world.Direction;

/**
 * The type Math.
 *
 * Contains static methods used to assist in mathematical operations or parsing.
 *
 */
public class Math {
    /**
     * Try parse int.
     *
     * Tries to parse an integer using a string value.
     *
     * @param value      the value to be parsed
     * @param defaultVal value to return if integer {@param value} could not be parsed.
     * @return the int
     */
    public static int tryParse(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    /**
     * Vector direction.
     *
     * Determines the direction in which the vector is going from the {@param oldOne} to {@param newOne}
     *
     * @param oldVector the old vector
     * @param newVector the new vector
     * @return the direction
     */
    public static Direction vectorDirection(Vector oldVector, Vector newVector) {

        int x = newVector.getX() - oldVector.getX();
        int y = newVector.getY() - oldVector.getY();

        if (y != 0) {

            if (y > 0) {
                return Direction.SOUTH;
            } else {
                return Direction.NORTH;
            }

        } else if (x != 0) {
            if (x < 0) {
                return Direction.EAST;

            } else {
                return Direction.WEST;
            }
        }
        return null;
    }

    /**
     * Vector difference int.
     *
     * Returns the distance between two sets of coordinates.
     * The difference will always be positive.
     *
     * @param vector1 the vector 1
     * @param vector2 the vector 2
     * @return the int
     */
    public static int vectorDifference(Vector vector1, Vector vector2) {
        return java.lang.Math.abs(vector1.getX() - vector2.getX()) + java.lang.Math.abs(vector1.getY() - vector2.getY());
    }

    /**
     * Position clicked on pane vector.
     *
     * Determines the position on a tilemap a set of coordinates corresponds to.
     *
     * @param tileHeight the tile height
     * @param tileWidth  the tile width
     * @param x          the x
     * @param y          the y
     * @return the vector
     */
    public static Vector positionClickedOnPane(double tileHeight, double tileWidth, double x, double y) {

        if (x > 0) {
            x = java.lang.Math.floor(x / tileWidth);
        }
        if (y > 0) {
            y = java.lang.Math.floor(y / tileHeight);
        }

        return new Vector((int) x, (int) y);
    }
}