package pers.defoliation.magic.block;

import net.minecraft.BlockEnchantmentTable;
import net.minecraft.Material;

public class MagicEnchantingTable extends BlockEnchantmentTable {

    public MagicEnchantingTable(int par1, Material gem_type) {
        super(par1, gem_type);
        c("magic_enchanting_table");
    }

}
