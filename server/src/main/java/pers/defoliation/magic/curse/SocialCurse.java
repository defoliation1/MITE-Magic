package pers.defoliation.magic.curse;

import net.minecraft.Item;
import net.minecraft.ItemArmor;
import net.minecraft.ItemStack;

public class SocialCurse extends MagicCurse {

    public SocialCurse() {
        super("social");
    }

    public static boolean modifier(ItemStack itemStack, ItemStack[] itemStacks) {
        boolean work = true;
        for (int i = 0; i < 4; i++) {
            if (itemStacks[i] == null) {
                work = false;
                break;
            }
        }
        if (!work)
            CurseUtil.setEnchantmentWork(itemStack, false);
        return work;
    }

    @Override
    public boolean canCurse(Item item) {
        return item instanceof ItemArmor;
    }
}
