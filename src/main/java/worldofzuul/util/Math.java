package worldofzuul.util;

import worldofzuul.world.Direction;

public class Math {
    public static int tryParse(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static Direction vectorDirection(Vector oldVector, Vector newVector) {

        int x = newVector.getX() - oldVector.getX();
        int y = newVector.getY() - oldVector.getY();

        if (y != 0) {

            if (y > 0) {//SOUTH
                return Direction.SOUTH;
            } else {//NORTH
                return Direction.NORTH;
            }

        } else if (x != 0) {
            if (x < 0) {//EAST
                return Direction.EAST;

            } else {//WEST
                return Direction.WEST;
            }
        }
        return null;
    }

    public static int vectorDifference(Vector vector1, Vector vector2) {
        return java.lang.Math.abs(vector1.getX() - vector2.getX()) + java.lang.Math.abs(vector1.getY() - vector2.getY());

    }

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