package pers.defoliation.magic.curse;

import common.defoliation.mod.mite.DimensionManager;
import common.defoliation.mod.mite.inventory.ItemStackWrapper;
import common.defoliation.mod.mite.nbt.MITENBTTagCompound;
import net.minecraft.EnchantmentManager;
import net.minecraft.Item;
import net.minecraft.ItemFood;
import net.minecraft.ItemStack;

import java.util.List;

public class GluttonyCurse extends MagicCurse {

    public GluttonyCurse() {
        super("gluttony");
    }

    @Override
    public boolean canCurse(Item item) {
        return true;
    }

    public static void modifier(ItemStack gluttonyItem, List<ItemStack> itemStacks) {
        int level = EnchantmentManager.getEnchantmentLevel(Curses.gluttony, gluttonyItem);
        if (level <= 0)
            return;
        MITENBTTagCompound mitenbtTagCompound = (MITENBTTagCompound) ItemStackWrapper.of(gluttonyItem).getNBT();
        long second = DimensionManager.getWorld(0).I() / 20;
        if (!mitenbtTagCompound.hasKey("gluttony")) {
            mitenbtTagCompound.setLong("gluttony", second + getFoodTime(Item.fishLargeCooked, level));
            return;
        }
        long itemTime = mitenbtTagCompound.getLong("gluttony");
        if (second > itemTime) {
            for (ItemStack itemStack : itemStacks) {
                if (itemStack != null && itemStack.b() instanceof ItemFood && ItemStackWrapper.of(itemStack).getAmount() > 0) {
                    ItemStackWrapper wrapper = ItemStackWrapper.of(itemStack);
                    wrapper.setAmount(wrapper.getAmount() - 1);
                    mitenbtTagCompound.setLong("gluttony", second + getFoodTime((ItemFood) itemStack.b(), level));
                    break;
                }
            }
        }
    }

    private static int getFoodTime(ItemFood itemFood, int level) {
        return (itemFood.satiation + itemFood.nutrition) * 50 * 20 / level;
    }
}
