package worldofzuul.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import worldofzuul.item.Item;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Quest.
 *
 */
public class Quest {
    /**
     * The Quest complete.
     */
    private final BooleanProperty questComplete = new SimpleBooleanProperty(false);
    /**
     * The Rewards.
     *
     * List of Items to grant the player upon quest completion.
     *
     */
    private List<Item> rewards = new LinkedList<>();
    /**
     * The Quest title.
     */
    private String questTitle;
    /**
     * The Introduction message.
     */
    private String introductionMessage;
    /**
     * The Completion message.
     */
    private String completionMessage;
    /**
     * The Turn in class.
     *
     * The type of Item to be turned in.
     * This would be represented as a string using the following example in the JSON configuration, "worldofzuul.item.Seed".
     *
     */
    private Class<?> turnInClass;
    /**
     * The Turn in quantity.
     */
    private Float turnInQuantity;

    /**
     * Instantiates a new Quest.
     */
    public Quest() {
    }

    /**
     * Turn in item.
     *
     * Tries to turn in an item, upon quest completion it will return {@link Quest#completeQuest()}.
     *
     * @param item the item
     * @return commands for removal of turned in items and adding of {@link Quest#rewards}
     */
    public Command[] turnIn(Item item) {
        List<Command> commands = new LinkedList<>();

        if (turnInClass.isInstance(item)) {

            if (item.getRemaining() >= turnInQuantity) {
                item.deplete(turnInQuantity);
                commands.addAll(completeQuest());
            } else if (item.getRemaining() > 0) {
                float amountTurnedIn = item.getRemaining();
                System.out.println("You turned in " + amountTurnedIn + " " + item.getName() + "s.");
                item.deplete(amountTurnedIn);
                turnInQuantity -= amountTurnedIn;


            } else {
                System.out.println("You can't turn in nothing!");
            }

            if (item.getRemaining() <= 0) {
                commands.add(new Command(CommandWord.REMOVE_ITEM, null, item));
            }

        } else {
            System.out.println("Bring me " + turnInQuantity + " " + turnInClass.getSimpleName() + "s instead.");
        }

        return commands.toArray(new Command[0]);
    }

    /**
     * Complete quest list.
     *
     * @return list of commands to add all items defined in {@link Quest#rewards}.
     */
    private List<Command> completeQuest() {
        turnInQuantity = 0f;
        questComplete.set(true);

        List<Command> commands = new LinkedList<>();

        for (Item reward : rewards) {
            commands.add(new Command(CommandWord.ADD_ITEM, null, reward));
        }

        System.out.println(completionMessage);

        return commands;
    }


    /**
     * Gets rewards.
     *
     * @return the rewards
     */
    public List<Item> getRewards() {
        return rewards;
    }

    /**
     * Sets rewards.
     *
     * @param rewards the rewards
     */
    public void setRewards(List<Item> rewards) {
        this.rewards = rewards;
    }

    /**
     * Gets quest title.
     *
     * @return the quest title
     */
    public String getQuestTitle() {
        return questTitle;
    }

    /**
     * Sets quest title.
     *
     * @param questTitle the quest title
     */
    public void setQuestTitle(String questTitle) {
        this.questTitle = questTitle;
    }

    /**
     * Gets introduction message.
     *
     * @return the introduction message
     */
    public String getIntroductionMessage() {
        return introductionMessage;
    }

    /**
     * Sets introduction message.
     *
     * @param introductionMessage the introduction message
     */
    public void setIntroductionMessage(String introductionMessage) {
        this.introductionMessage = introductionMessage;
    }

    /**
     * Gets completion message.
     *
     * @return the completion message
     */
    public String getCompletionMessage() {
        return completionMessage;
    }

    /**
     * Sets completion message.
     *
     * @param completionMessage the completion message
     */
    public void setCompletionMessage(String completionMessage) {
        this.completionMessage = completionMessage;
    }

    /**
     * Gets turn in class.
     *
     * @return the turn in class
     */
    @JsonIgnore
    public Class<?> getTurnInClass() {
        return turnInClass;
    }

    /**
     * Sets turn in class.
     *
     * @param turnInClass the turn in class
     */
    @JsonIgnore
    public void setTurnInClass(Class<?> turnInClass) {
        this.turnInClass = turnInClass;
    }

    /**
     * Gets turn in type.
     *
     * @return the turn in type
     */
    public String getTurnInType() {
        return turnInClass.getName();
    }

    /**
     * Sets turn in type.
     *
     * @param turnInType the turn in type
     */
    public void setTurnInType(String turnInType) {
        try {
            turnInClass = Class.forName(turnInType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets turn in quantity.
     *
     * @return the turn in quantity
     */
    public Float getTurnInQuantity() {
        return turnInQuantity;
    }

    /**
     * Sets turn in quantity.
     *
     * @param turnInQuantity the turn in quantity
     */
    public void setTurnInQuantity(Float turnInQuantity) {
        this.turnInQuantity = turnInQuantity;
    }

    /**
     * Is quest complete boolean.
     *
     * @return the boolean
     */
    @JsonIgnore
    public boolean isQuestComplete() {
        return questComplete.get();
    }

    /**
     * Sets quest complete.
     *
     * @param questComplete the quest complete
     */
    @JsonIgnore
    public void setQuestComplete(boolean questComplete) {
        this.questComplete.set(questComplete);
    }

    /**
     * Quest complete property boolean property.
     *
     * @return the boolean property
     */
    @JsonIgnore
    public BooleanProperty questCompleteProperty() {
        return questComplete;
    }

    /**
     * Configure images.
     *
     * @param images the images
     */
    public void configureImages(HashMap<String, Image> images) {
        for (Item reward : rewards) {
            reward.configureImages(images);
        }
    }


}
