package pers.defoliation.magic.liar;

import common.defoliation.mod.mite.nbt.MITENBTTagCompound;
import net.minecraft.EnchantmentManager;
import net.minecraft.ItemStack;
import net.minecraft.NBTTagCompound;
import net.minecraft.NBTTagList;
import pers.defoliation.magic.curse.Curses;
import team.unknowndomain.liar.annotation.Liar;

@Liar(EnchantmentManager.class)
public class EnchantmentManagerLiar {

    public static int a(int par0, ItemStack par1ItemStack) {
        return getEnchantmentLevel(par0,par1ItemStack, Curses.isDamnation(par0));
    }

    public static int getEnchantmentLevel(int par0, ItemStack par1ItemStack, boolean isDamnation) {
        if (par1ItemStack == null) {
            return 0;
        }
        if (!isDamnation) {
            MITENBTTagCompound nbtTagCompound = new MITENBTTagCompound(par1ItemStack.q());
            if (nbtTagCompound.hasKey("enchWork") && !nbtTagCompound.getBoolean("enchWork"))
                return 0;
        }
        NBTTagList var2 = par1ItemStack.r();
        if (var2 == null) {
            return 0;
        }
        for (int var3 = 0; var3 < var2.c(); var3++) {
            short var4 = ((NBTTagCompound) var2.b(var3)).d("id");
            short var5 = ((NBTTagCompound) var2.b(var3)).d("lvl");
            if (var4 == par0) {
                return var5;
            }
        }
        return 0;
    }

}
