package pers.defoliation.magic;

import common.defoliation.event.EventHandler;
import common.defoliation.event.world.WorldGenMinableEvent;
import common.defoliation.mod.mite.entity.MITEEntity;
import common.defoliation.mod.mite.event.EntityTargetEvent;
import common.defoliation.mod.mite.event.ItemTooltipEvent;
import common.defoliation.world.Minable;
import net.minecraft.EntityPigZombie;
import net.minecraft.LocaleI18n;
import pers.defoliation.magic.block.Blocks;
import pers.defoliation.magic.curse.CurseManager;
import pers.defoliation.magic.entity.PigZombieLord;

import java.util.List;

public class Listener {

    @EventHandler
    public void onWorldMinableGen(WorldGenMinableEvent worldGenMinableEvent) {
        if (worldGenMinableEvent.getWorld().getName().equals("Overworld")) {
            WorldGenMinableEvent.GenMinable removeMithril = null;
            for (WorldGenMinableEvent.GenMinable genMinable : worldGenMinableEvent.genGenMinables()) {
                if (genMinable.getMinable().getOreId() == 202) {
                    removeMithril = genMinable;
                    break;
                }
            }
            worldGenMinableEvent.genGenMinables().remove(removeMithril);
        } else if (worldGenMinableEvent.getWorld().getName().equals("Underworld")) {
            WorldGenMinableEvent.GenMinable removeMithril = null;
            for (WorldGenMinableEvent.GenMinable genMinable : worldGenMinableEvent.genGenMinables()) {
                //钻石
                if (removeMithril == null && genMinable.getMinable().getOreId() == 56) {
                    removeMithril = genMinable;
                    continue;
                }
                //艾德曼
                if (genMinable.getMinable().getBlockId() == 203) {
                    genMinable.setFrequency(genMinable.getFrequency() * 2);
                }
            }
            worldGenMinableEvent.genGenMinables().remove(removeMithril);
            worldGenMinableEvent.genGenMinables().add(new WorldGenMinableEvent.GenMinable(20, new Minable() {
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
            }, false));
        } else if (worldGenMinableEvent.getWorld().getName().equals("Nether")) {
            for (WorldGenMinableEvent.GenMinable genMinable : worldGenMinableEvent.genGenMinables()) {
                //石英
                if (genMinable.getMinable().getOreId() == 153) {
                    genMinable.setFrequency(4);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void itemTooltipEvent(ItemTooltipEvent event) {
        List<String> list = event.toolTip;
        CurseManager.INSTANCE.getCursesFromItemStack(event.itemStack).forEach(curseLevel ->
                list.add("§c" + curseLevel.curse.getTranslateName() + " " + (curseLevel.curse.getMaxLevel() > 1 ? LocaleI18n.a("enchantment.level." + curseLevel.level) : "")));
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent entityTargetEvent) {
        if (((MITEEntity) entityTargetEvent.getEntity()).getEntity() instanceof EntityPigZombie && entityTargetEvent.getTarget() instanceof PigZombieLord) {
            entityTargetEvent.setCancelled(true);
        }
    }

}
