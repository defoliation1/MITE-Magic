package pers.defoliation.magic.curse;

import net.minecraft.Item;
import net.minecraft.ItemStack;

public interface Curse {

    String getName();

    String getTranslateName();

    boolean canCurse(Item item);

    int getProbability(ItemStack itemStack,int enchantLevel);

}
