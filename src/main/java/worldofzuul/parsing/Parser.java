package worldofzuul.parsing;

import worldofzuul.util.MessageHelper;

import java.util.Scanner;

/**
 * The type Parser.
 */
public class Parser {
    /**
     * The Commands.
     */
    private final CommandWords commands;
    /**
     * The Reader.
     */
    private final Scanner reader;

    /**
     * Instantiates a new Parser.
     */
    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        String inputLine;
        String word1 = null;
        String word2 = null;

        MessageHelper.Command.userInputArrow();

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }

    /**
     * Show commands.
     */
    public void showCommands() {
        commands.showAll();
    }
}
