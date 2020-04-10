package pers.defoliation.magic.curse;

import net.minecraft.Item;
import net.minecraft.ItemArmor;
import net.minecraft.ItemStack;

public class AsocialCurse extends MagicCurse {

    public AsocialCurse() {
        super("asocial");
    }

    public static boolean modifier(ItemStack itemStack, ItemStack[] itemStacks) {
        boolean work = true;
        int items = 0;
        for(int i =0;i<itemStacks.length;i++){
            if(itemStacks[i]!=null){
                items++;
                if(items>=2){
                    work = false;
                    break;
                }
            }
        }
        return work;
    }

    @Override
    public boolean canCurse(Item item) {
        return item instanceof ItemArmor;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

}
