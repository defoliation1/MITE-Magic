package pers.defoliation.magic.curse;

import net.minecraft.Item;

public class BlasterCurse extends MagicCurse {

    public BlasterCurse() {
        super("blaster");
    }

    public static float getExplosiveSize(int level){
        return 0.6f + 0.4f*level;
    }

    @Override
    public boolean canCurse(Item item) {
        return true;
    }

}
