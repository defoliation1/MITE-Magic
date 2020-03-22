package pers.defoliation.magic.block;

import net.minecraft.BlockOreBlock;
import net.minecraft.Material;

public class MagicMetalBlock extends BlockOreBlock {

    public MagicMetalBlock(int par1, Material material) {
        super(par1, material);
        d("magic:magic_ancient_metal_block");
        c("magic_metal_block");
    }

}
