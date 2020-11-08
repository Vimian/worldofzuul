package worldofzuul.util;

import worldofzuul.parsing.CommandWord;

public class MessageHelper {
    public static class Item {
        public static void harvested(String plant){
            System.out.println("You harvested " + plant + ".");
        }
        public static void unripePlant(){
            System.out.println("Plant is not ripe.");
        }
        public static void noPlantOnField(){
            System.out.println("Plant is not ripe.");
        }


        public static void cantUseItem(String item){
            System.out.println("You can't use that "+ item +" here.");
        }

        public static void usedItem(String item){
            System.out.println("You used a "+ item +".");
        }

        public static void usedItemOn(String item, String secondItem){
            System.out.println("You used a "+ item +" on the "+ secondItem +".");
        }
    }
    public static class Command {

        public static void unknownAction(){
            System.out.println("What are you trying to do?");
        }

        public static void unknownCommand(){
            System.out.println("I don't know what you mean...");
        }

        public static void unknownArgument(String message){
            System.out.println(capitalize(message) + " where?");
        }

        public static void incorrectExit(){
            System.out.println("There is no door!");
        }

        public static void roomDescription(String roomDescription){
            System.out.println(roomDescription);
        }

        public static void moveCommand(String direction){
            System.out.println("You walked " + direction + ".");
        }

        public static void teleported(Vector destination){
            System.out.println("You were teleported to X: " + destination.x + " Y: " + destination.y + ".");
        }

        public static void positionExceedsMap(){
            System.out.println("You can't walk there.");
        }

        public static void objectIsCollidable(){
            System.out.println("You can't walk there.");
        }

        public static void stringSpace(String string){
            System.out.print(string + "  ");
        }
        public static void line(){
            System.out.println();
        }
    }
    public static class Info {

        public static void exitMessage(){
            System.out.println("Thank you for playing.  Good bye.");
        }

        public static void welcomeMessage(String roomDescription){
            System.out.println();
            System.out.println("Welcome to the World of Zuul!");
            System.out.println("World of Zuul is a new, incredibly boring adventure game.");
            System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
            System.out.println();
            System.out.println(roomDescription);
        }

        public static void helpCommands(){
            System.out.println("You are lost. You are alone. You wander");
            System.out.println("around at the university.");
            System.out.println();
            System.out.println("Your command words are:");
        }
    }

    private static String capitalize(String string){
        return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
    }

}
