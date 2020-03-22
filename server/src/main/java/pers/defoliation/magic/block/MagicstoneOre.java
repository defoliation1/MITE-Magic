package pers.defoliation.magic.block;

import net.minecraft.*;
import pers.defoliation.magic.item.Items;

public class MagicstoneOre extends BlockOre {

    public MagicstoneOre(int par1, Material vein_material, int min_harvest_level) {
        super(par1, vein_material, min_harvest_level);
        d("magic:magicstone_ore").
        a(Block.k).
        c(3.0f).
        b(5.0f).
        c("magicstone_ore");
    }


    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        int metadata_dropped = 0;
        int quantity_dropped = 1;
        int id_dropped;

        id_dropped = Items.magicstoneShard.cv;

        boolean suppress_fortune = id_dropped == cF && BitHelper.isBitSet(info.getMetadata(), 1);
        if (id_dropped != -1 && info.getMetadata() == 0) {
            DedicatedServer.incrementTournamentScoringCounter(info.getResponsiblePlayer(), Item.getItem(id_dropped));
        }

        float chance = suppress_fortune ? 1.0F : 1.0F + (float) info.getHarvesterFortune() * 0.1F;
        return super.dropBlockAsEntityItem(info, id_dropped, metadata_dropped, quantity_dropped, chance);
    }
}
