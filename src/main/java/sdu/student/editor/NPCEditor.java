package sdu.student.editor;


import worldofzuul.world.NPC;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Npc editor.
 */
public class NPCEditor extends GameObjectEditor {
    /**
     * Instantiates a new Npc editor.
     *
     * @param model the model
     */
    public NPCEditor(NPC model) {
        super(model);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindProperties();


    }
}
