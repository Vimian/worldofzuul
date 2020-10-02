package worldofzuul;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The type Parser.
 */
public class Parser
{
    /**
     * The Commands.
     */
    private CommandWords commands;
    /**
     * The Reader.
     */
    private Scanner reader;

    /**
     * Instantiates a new Parser.
     */
    public Parser()
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * Reads inputLine for command
     *
     * @return the found command
     */
    public Command getCommand()
    {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> "); 

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next(); 
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }

    /**
     * Shows all commands.
     */
    public void showCommands()
    {
        commands.showAll();
    }
}
