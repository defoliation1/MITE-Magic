package pers.defoliation.magic.liar;

import net.minecraft.*;
import pers.defoliation.magic.curse.CurseUtil;
import team.unknowndomain.liar.annotation.Liar;

@Liar(EnchantmentManager.class)
public class EnchantmentManagerLiar {

    public static int a(int par0, ItemStack par1ItemStack)
    {
        if (par1ItemStack == null) {
            return 0;
        }
        if(!CurseUtil.isEnchantmentWork(par1ItemStack))
            return 0;
        NBTTagList var2 = par1ItemStack.r();
        if (var2 == null) {
            return 0;
        }
        for (int var3 = 0; var3 < var2.c(); var3++)
        {
            short var4 = ((NBTTagCompound)var2.b(var3)).d("id");
            short var5 = ((NBTTagCompound)var2.b(var3)).d("lvl");
            if (var4 == par0) {
                return var5;
            }
        }
        return 0;
    }
}
