package sdu.student.editor.model;

import worldofzuul.world.Block;

public class BlockModel extends GameObjectModel {

    private final Block block;

    public BlockModel(Block block) {
        super(block);
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

}
