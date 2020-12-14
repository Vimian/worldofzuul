package worldofzuul.world;

import javafx.scene.image.Image;
import worldofzuul.item.Item;
import worldofzuul.parsing.Command;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * The type NPC.
 *
 * Manages quest giving and player dialogue-interaction.
 *
 */
public class NPC extends GameObject {

    /**
     * The Quests.
     */
    private LinkedList<Quest> quests = new LinkedList<>();
    /**
     * The Dialog.
     */
    private LinkedList<String> dialog = new LinkedList<>();

    /**
     * Instantiates a new Npc.
     */
    public NPC() {
    }

    /**
     * Interact.
     *
     * Cycles through the {@link NPC#dialog} one by one in an ordered manner.
     *
     * @return null
     */
    @Override
    public Command[] interact() {

        if (dialog.size() > 0) {
            String dialogText = dialog.getFirst();
            System.out.println(dialogText);

            dialog.remove(dialogText);
            dialog.add(dialogText);
        }

        return null;
    }

    /**
     * Interact with an item.
     *
     * Tries to turn in an item for the current quest defined in {@link NPC#quests}.
     *
     * @param item the item to turn in
     * @return quest turn in commands
     */
    @Override
    public Command[] interact(Item item) {

        if (quests.size() > 0) {
            Quest currentQuest = quests.getFirst();

            currentQuest.questCompleteProperty().removeListener((o, oV, nV) -> quests.remove(currentQuest));
            currentQuest.questCompleteProperty().addListener((o, oV, nV) -> quests.remove(currentQuest));

            if (currentQuest != null) {
                return currentQuest.turnIn(item);
            }
        }

        return super.interact(item);
    }

    /**
     * Gets quests.
     *
     * @return the quests
     */
    public LinkedList<Quest> getQuests() {
        return quests;
    }

    /**
     * Sets quests.
     *
     * @param quests the quests
     */
    public void setQuests(LinkedList<Quest> quests) {
        this.quests = quests;
    }

    /**
     * Gets dialog.
     *
     * @return the dialog
     */
    public LinkedList<String> getDialog() {
        return dialog;
    }

    /**
     * Sets dialog.
     *
     * @param dialog the dialog
     */
    public void setDialog(LinkedList<String> dialog) {
        this.dialog = dialog;
    }

    @Override
    public void configureImages(HashMap<String, Image> images) {
        super.configureImages(images);

        for (Quest quest : quests) {
            quest.configureImages(images);
        }

    }
}
