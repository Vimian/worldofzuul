package sdu.student.editor;


import worldofzuul.world.NPC;

import java.net.URL;
import java.util.ResourceBundle;

public class NPCEditor extends GameObjectEditor {
    public NPCEditor(NPC model) {
        super(model);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindProperties();


    }
}
