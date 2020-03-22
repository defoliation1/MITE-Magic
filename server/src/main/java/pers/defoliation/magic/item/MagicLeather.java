package pers.defoliation.magic.item;

import net.minecraft.CreativeModeTab;
import net.minecraft.Item;

public class MagicLeather extends Item {

    public MagicLeather(int par1) {
        super(par1,"");
        d("magic:magic_leather")
        .b("magic_leather")
        .setCraftingDifficultyAsComponent(100F)
        .a(CreativeModeTab.l);
    }

}
