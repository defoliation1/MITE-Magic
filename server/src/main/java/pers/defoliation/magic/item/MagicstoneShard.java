package pers.defoliation.magic.item;

import net.minecraft.ItemNugget;
import net.minecraft.Material;

public class MagicstoneShard extends ItemNugget {

    public MagicstoneShard(int id, Material material) {
        super(id, material);
        d("magic:magicstone_shard")
        .b("magicstone_shard");
    }

}
