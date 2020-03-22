package pers.defoliation.magic;

import common.defoliation.event.mod.PreInitEvent;
import common.defoliation.mod.Mod;
import common.defoliation.mod.ModId;
import pers.defoliation.magic.block.Blocks;
import pers.defoliation.magic.block.tile.Tiles;
import pers.defoliation.magic.inventory.BaseGuiHandler;
import pers.defoliation.magic.item.Items;

import java.util.Random;

@ModId(id="magic")
public class Main {

    public static final Random random = new Random(System.currentTimeMillis());

    public static Mod mod;


    public void preInit(PreInitEvent event) {
        mod = event.getMod();
        new BaseGuiHandler();
        Tiles.registerTile();
        Items.registerItems();
        Blocks.registerBlocks();
    }

}
