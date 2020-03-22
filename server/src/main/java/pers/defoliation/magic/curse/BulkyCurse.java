package pers.defoliation.magic.curse;

import net.minecraft.*;

public class BulkyCurse extends MagicCurse {

    public BulkyCurse() {
        super("bulky");
    }

    @Override
    public boolean canCurse(Item item) {
        return item instanceof ItemArmor;
    }

    public static int getMaxLevel(EntityLiving entityLiving){
        int max = 0;
        if(entityLiving instanceof EntityHuman){
            EntityHuman human = (EntityHuman) entityLiving;
            for (int i1 = 0; i1 < human.bn.b.length; i1++) {
                ItemStack stack = human.bn.b[i1];
                if(stack!=null){
                    int level = EnchantmentManager.getEnchantmentLevel(Curses.bulky,stack);
                    if(level>max)
                        max = level;
                }
            }
        }else {
            for (int i1 = 0; i1 < entityLiving.g.length; i1++) {
                ItemStack stack = entityLiving.g[i1];
                if(stack!=null){
                    int level = EnchantmentManager.getEnchantmentLevel(Curses.bulky,stack);
                    if(level>max)
                        max = level;
                }
            }
        }
        return max;
    }

}
