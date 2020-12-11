package sdu.student.editor;


import worldofzuul.world.Block;

import java.net.URL;
import java.util.ResourceBundle;


public class BlockEditor extends GameObjectEditor {

    public BlockEditor(Block model) {
        super(model);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindProperties();
    }

}
