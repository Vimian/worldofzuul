package sdu.student.editor;


import worldofzuul.world.Block;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * The type Block editor.
 */
public class BlockEditor extends GameObjectEditor {

    /**
     * Instantiates a new Block editor.
     *
     * @param model the model
     */
    public BlockEditor(Block model) {
        super(model);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindProperties();
    }

}
