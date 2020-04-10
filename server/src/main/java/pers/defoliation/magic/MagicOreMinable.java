package pers.defoliation.magic;

import common.defoliation.world.Minable;
import pers.defoliation.magic.block.Blocks;

public class MagicOreMinable implements Minable {
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
        return 6;
    }

    @Override
    public int getBlockId() {
        return 1;
    }

    @Override
    public boolean increasesWithDepth() {
        return false;
    }

    @Override
    public int frequency() {
        return 4;
    }
}
