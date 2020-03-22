package pers.defoliation.magic.item;

import net.minecraft.ItemIngot;
import net.minecraft.Material;

public class Magicstone extends ItemIngot {

    public Magicstone(int id, Material material) {
        super(id, material);
        d("magic:magicstone")
        .b("magicstone");
    }

}
