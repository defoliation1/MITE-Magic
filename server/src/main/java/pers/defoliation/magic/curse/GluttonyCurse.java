package pers.defoliation.magic.curse;

import common.defoliation.mod.mite.DimensionManager;
import common.defoliation.mod.mite.inventory.ItemStackWrapper;
import common.defoliation.mod.mite.nbt.MITENBTTagCompound;
import net.minecraft.*;

import java.util.List;

public class GluttonyCurse extends MagicCurse {

    public GluttonyCurse() {
        super("gluttony");
    }

    @Override
    public boolean canCurse(Item item) {
        return true;
    }

    public static void modifier(ItemStack gluttonyItem, IInventory inventory) {
        CurseManager.INSTANCE.getCurseFromItemStack(gluttonyItem, Curses.gluttony).ifPresent(curseLevel -> {
            int level = curseLevel.level;
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
                for (int i = 0; i < inventory.j_(); i++) {
                    ItemStack itemStack = inventory.a(i);
                    if (itemStack != null && itemStack.b() instanceof ItemFood && ItemStackWrapper.of(itemStack).getAmount() > 0) {
                        ItemStackWrapper wrapper = ItemStackWrapper.of(itemStack);
                        wrapper.setAmount(wrapper.getAmount() - 1);
                        if (wrapper.getAmount() <= 0) {
                            inventory.a(i, null);
                        } else
                            mitenbtTagCompound.setLong("gluttony", second + getFoodTime((ItemFood) itemStack.b(), level));
                        break;
                    }
                }
            }
        });
    }

    private static int getFoodTime(ItemFood itemFood, int level) {
        return (itemFood.satiation + itemFood.nutrition) * 50 * 20 / level;
    }
}
