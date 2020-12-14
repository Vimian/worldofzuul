package worldofzuul.util;

import worldofzuul.parsing.CommandWord;

/**
 * The type Message helper.
 *
 * Contains static functions used for defining messages that are to be given to the player.
 *
 */
public class MessageHelper {
    /**
     * Print new line.
     *
     * @param string the string
     */
    private static void printNewLine(String string) {
        Command.line();
        System.out.println(string);
        Command.userInputArrow();
    }

    /**
     * Capitalize string.
     *
     * @param string the string
     * @return the string
     */
    private static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    /**
     * The type Item.
     */
    public static class Item {
        /**
         * Harvested.
         *
         * @param plant the plant
         */
        public static void harvested(String plant) {
            System.out.println("You harvested " + plant + ".");
        }

        /**
         * Unripe plant.
         */
        public static void unripePlant() {
            System.out.println("Plant is not ripe.");
        }

        /**
         * No plant on field.
         */
        public static void noPlantOnField() {
            System.out.println("There is not a plant in the field.");
        }

        /**
         * Increasedp h.
         *
         * @param pH the p h
         */
        public static void increasedpH(String pH) {
            System.out.println("You increased the field pH to " + pH + ".");
        }

        /**
         * Decreasedp h.
         *
         * @param pH the p h
         */
        public static void decreasedpH(String pH) {
            System.out.println("You decreased the field pH to " + pH + ".");
        }


        /**
         * Cant use item.
         *
         * @param item the item
         */
        public static void cantUseItem(String item) {
            System.out.println("You can't use that " + item + " here.");
        }

        /**
         * Used item.
         *
         * @param item the item
         */
        public static void usedItem(String item) {
            System.out.println("You used a " + item + ".");
        }

        /**
         * Used item on.
         *
         * @param item       the item
         * @param secondItem the second item
         */
        public static void usedItemOn(String item, String secondItem) {
            System.out.println("You used a " + item + " on the " + secondItem + ".");
        }

        /**
         * Already planted.
         */
        public static void alreadyPlanted() {
            System.out.println("A plant already exists here.");
        }

    }

    /**
     * The type Command.
     */
    public static class Command {

        /**
         * Unknown action.
         */
        public static void unknownAction() {
            System.out.println("What are you trying to do?");
        }

        /**
         * Unknown command.
         */
        public static void unknownCommand() {
            System.out.println("I don't know what you mean...");
        }


        /**
         * Unknown argument where.
         *
         * @param message the message
         */
        public static void unknownArgumentWhere(String message) {
            System.out.println(capitalize(message) + " where?");
        }

        /**
         * Unknown argument what.
         *
         * @param message the message
         */
        public static void unknownArgumentWhat(String message) {
            System.out.println(capitalize(message) + " what?");
        }

        /**
         * Incorrect exit.
         */
        public static void incorrectExit() {
            System.out.println("There is no door!");
        }

        /**
         * Room description.
         *
         * @param roomDescription the room description
         */
        public static void roomDescription(String roomDescription) {
            System.out.println(roomDescription);
        }

        /**
         * Move command.
         *
         * @param direction the direction
         */
        public static void moveCommand(String direction) {
            System.out.println("You walked " + direction + ".");
        }

        /**
         * Teleported.
         *
         * @param destination the destination
         */
        public static void teleported(Vector destination) {
            System.out.println("You were teleported to X: " + destination.getX() + " Y: " + destination.getY() + ".");
        }

        /**
         * Position exceeds map.
         */
        public static void positionExceedsMap() {
            System.out.println("You can't walk there.");
        }

        /**
         * Object is collidable.
         */
        public static void objectIsCollidable() {
            System.out.println("You can't walk through that.");
        }

        /**
         * Invalid item index.
         */
        public static void invalidItemIndex() {
            System.out.println("No item exists at that location in your inventory.");
        }

        /**
         * Selected item.
         *
         * @param string the string
         */
        public static void selectedItem(String string) {
            System.out.println("You selected " + string + ".");
        }

        /**
         * String space.
         *
         * @param string the string
         */
        public static void stringSpace(String string) {
            System.out.print(string + "  ");
        }

        /**
         * Line.
         */
        public static void line() {
            System.out.println();
        }

        /**
         * User input arrow.
         */
        public static void userInputArrow() {
            System.out.print("> ");
        }
    }

    /**
     * The type Info.
     */
    public static class Info {

        /**
         * Rain started.
         */
        public static void rainStarted() {
            printNewLine("It began to rain.");
        }

        /**
         * Rain stopped.
         */
        public static void rainStopped() {
            printNewLine("The rain suddenly stopped.");
        }

        /**
         * Night started.
         */
        public static void nightStarted() {
            printNewLine("It became night.");
        }

        /**
         * Night ended.
         */
        public static void nightEnded() {
            printNewLine("The sun began to rise.");
        }

        /**
         * Exit message.
         */
        public static void exitMessage() {
            System.out.println("Thank you for playing.  Good bye.");
        }

        /**
         * Welcome message.
         *
         * @param roomDescription the room description
         */
        public static void welcomeMessage(String roomDescription) {
            System.out.println();
            System.out.println("Welcome to the World of Zuul!");
            System.out.println("World of Zuul is a new, incredibly boring adventure game.");
            System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
            System.out.println();
            System.out.println(roomDescription);
        }

        /**
         * Help commands.
         */
        public static void helpCommands() {
            System.out.println("You are lost. You are alone. You wander");
            System.out.println("around at the university.");
            System.out.println();
            System.out.println("Your command words are:");
        }

        /**
         * Plant became ripe.
         *
         * @param plant the plant
         */
        public static void plantBecameRipe(String plant) {
            System.out.println(plant + " became ripe!");
        }

        /**
         * Println.
         *
         * @param string the string
         */
        public static void println(String string) {
            System.out.println(string);
        }
    }

}
