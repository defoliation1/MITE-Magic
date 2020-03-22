package pers.defoliation.magic.curse;

import common.defoliation.mod.mite.DimensionManager;
import net.minecraft.Item;
import net.minecraft.ItemStack;

public class SlothCurse extends MagicCurse {

    public SlothCurse() {
        super("sloth");
    }

    @Override
    public boolean canCurse(Item item) {
        return true;
    }

    public static boolean modifier(ItemStack itemStack) {
        boolean work = true;
        if (DimensionManager.getWorld(0).getHourOfDay() < 6 || DimensionManager.getWorld(0).getHourOfDay() > 18){
            work = false;
            Curses.setEnchWork(itemStack, false);
        }
        return work;
    }
}
