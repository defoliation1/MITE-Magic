package pers.defoliation.magic.item;

import net.minecraft.CreativeModeTab;
import net.minecraft.Item;
import net.minecraft.Material;

public class MagicPaper extends Item {

    public MagicPaper(int id) {
        super(id,Material.paper,"");
        d(64).setCraftingDifficultyAsComponent(30f).a(CreativeModeTab.f).d("magic:magic_paper")
        .b("magic_paper");
    }
}
