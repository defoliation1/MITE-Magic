package pers.defoliation.magic.block;

import net.minecraft.*;

public class MagicMetalBlock extends Block {

    public MagicMetalBlock(int par1, Material material) {
        super(par1, material, new BlockConstants());
        d("magic:magic_ancient_metal_block");
        c("magic_metal_block");
        this.a(CreativeModeTab.b);
        this.setMaxStackSize(4);
        c(6.5f);
    }

    public float getCraftingDifficultyAsComponent(int metadata) {
        return this.cU.isMetal() ? ItemIngot.getCraftingDifficultyAsComponent(this.cU) * 9.0F : ItemRock.getCraftingDifficultyAsComponent(this.cU) * (float)(this.cU == Material.quartz ? 4 : 9);
    }

}
