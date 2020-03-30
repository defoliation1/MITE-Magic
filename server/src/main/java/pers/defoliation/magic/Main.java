package pers.defoliation.magic;

import common.defoliation.MITE;
import common.defoliation.event.EventHandler;
import common.defoliation.event.mod.InitEvent;
import common.defoliation.event.mod.PreInitEvent;
import common.defoliation.mod.Mod;
import common.defoliation.mod.ModId;
import common.defoliation.mod.mite.event.ItemTooltipEvent;
import net.minecraft.LocaleI18n;
import pers.defoliation.magic.block.Blocks;
import pers.defoliation.magic.block.tile.Tiles;
import pers.defoliation.magic.curse.CurseManager;
import pers.defoliation.magic.curse.Curses;
import pers.defoliation.magic.inventory.BaseGuiHandler;
import pers.defoliation.magic.item.Items;

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
        MITE.getMITE().registerEventHandler(mod, this);
        Curses.registerAll();
    }

    public void init(InitEvent event) {
    }

    @EventHandler
    public void itemTooltipEvent(ItemTooltipEvent event) {
        List<String> list = event.toolTip;
        CurseManager.INSTANCE.getCursesFromItemStack(event.itemStack).forEach(curseLevel ->
                list.add("§c" + curseLevel.curse.getTranslateName() + " " + LocaleI18n.a("enchantment.level." + curseLevel.level)));
    }


}
