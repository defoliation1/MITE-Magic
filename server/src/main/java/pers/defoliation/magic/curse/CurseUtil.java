package pers.defoliation.magic.curse;

import common.defoliation.mod.mite.nbt.MITENBTTagCompound;
import net.minecraft.ItemStack;

public class CurseUtil {

    public static void setEnchantmentWork(ItemStack itemStack, boolean b) {
        itemStack.q().a("enchWork", b);
    }

    public static boolean isEnchantmentWork(ItemStack itemStack) {
        if (itemStack.q() != null) {
            MITENBTTagCompound nbtTagCompound = new MITENBTTagCompound(itemStack.q());
            if (nbtTagCompound != null)
                if (nbtTagCompound.hasKey("enchWork") && !nbtTagCompound.getBoolean("enchWork"))
                    return false;
        }
        return true;
    }

}
