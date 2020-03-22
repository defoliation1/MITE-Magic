package pers.defoliation.magic.item;

import net.minecraft.ItemIngot;
import net.minecraft.Material;

public class MagicIngot extends ItemIngot {

    public MagicIngot(int id, Material material) {
        super(id, material);
        d("magic:magic_ancient_metal_ingot")
        .b("magic_ancient_metal_ingot");
    }

}
