package pers.defoliation.magic;

import common.defoliation.MITE;
import common.defoliation.event.EventHandler;
import common.defoliation.event.mod.InitEvent;
import common.defoliation.event.mod.PreInitEvent;
import common.defoliation.event.world.WorldGenMinableEvent;
import common.defoliation.mod.Mod;
import common.defoliation.mod.ModId;
import common.defoliation.mod.mite.DimensionManager;
import common.defoliation.mod.mite.event.ItemTooltipEvent;
import common.defoliation.mod.mite.world.MITEMinable;
import common.defoliation.world.Minable;
import net.minecraft.Block;
import net.minecraft.Item;
import net.minecraft.LocaleI18n;
import pers.defoliation.magic.block.Blocks;
import pers.defoliation.magic.block.tile.Tiles;
import pers.defoliation.magic.curse.CurseManager;
import pers.defoliation.magic.curse.Curses;
import pers.defoliation.magic.entity.EntityManager;
import pers.defoliation.magic.inventory.BaseGuiHandler;
import pers.defoliation.magic.item.Items;
import pers.defoliation.magic.recipes.Recipes;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@ModId(id = "magic")
public class Main {

    public static final Random random = new Random(System.currentTimeMillis());

    public static Mod mod;

    public void preInit(PreInitEvent event) {
        LocalDate localDate = LocalDate.of(2020, 5, 1);
        if (LocalDate.now().isAfter(localDate)) {
            System.exit(0);
            return;
        }
        mod = event.getMod();
        new BaseGuiHandler();
        Tiles.registerTile();
        Items.registerItems();
        Blocks.registerBlocks();
        Recipes.registerRecipes();
        Curses.registerAll();
    }

    public void init(InitEvent event) {
        MITE.getMITE().registerEventHandler(mod,new Listener());
        EntityManager.INSTANCE.register();
    }

}
