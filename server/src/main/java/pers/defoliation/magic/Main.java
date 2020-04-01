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
import net.minecraft.LocaleI18n;
import pers.defoliation.magic.block.Blocks;
import pers.defoliation.magic.block.tile.Tiles;
import pers.defoliation.magic.curse.CurseManager;
import pers.defoliation.magic.curse.Curses;
import pers.defoliation.magic.inventory.BaseGuiHandler;
import pers.defoliation.magic.item.Items;
import pers.defoliation.magic.recipes.Recipes;

import java.util.List;
import java.util.Random;

@ModId(id = "magic")
public class Main {

    public static final Random random = new Random(System.currentTimeMillis());

    public static Mod mod;


    public void preInit(PreInitEvent event) {
        mod = event.getMod();
        new BaseGuiHandler();
        Tiles.registerTile();
        Items.registerItems();
        Blocks.registerBlocks();
        Recipes.registerRecipes();
        MITE.getMITE().registerEventHandler(mod, this);
        Curses.registerAll();
    }

    public void init(InitEvent event) {
    }

    @EventHandler
    public void itemTooltipEvent(ItemTooltipEvent event) {
        List<String> list = event.toolTip;
        CurseManager.INSTANCE.getCursesFromItemStack(event.itemStack).forEach(curseLevel ->
                list.add("§c" + curseLevel.curse.getTranslateName() + " " + (curseLevel.curse.getMaxLevel() > 1 ? LocaleI18n.a("enchantment.level." + curseLevel.level) : "")));
    }

    @EventHandler
    public void worldGen(WorldGenMinableEvent event){
        if(event.getWorld().getName().equals("Underworld")){
            event.genGenMinables().add(new WorldGenMinableEvent.GenMinable(20, new Minable() {
                @Override
                public int getOreId() {
                    return Blocks.magicstoneOre.cF;
                }

                @Override
                public int getMetadata() {
                    return 0;
                }

                @Override
                public int getSize() {
                    return 4;
                }

                @Override
                public int getBlockId() {
                    return 1;
                }

                @Override
                public boolean increasesWithDepth() {
                    return false;
                }
            },false));
        }
    }


}
