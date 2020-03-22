package pers.defoliation.magic.block;

import net.minecraft.Block;
import net.minecraft.BlockBookshelf;
import net.minecraft.BlockBreakInfo;
import net.minecraft.Item;
import pers.defoliation.magic.item.Items;

public class MagicBookshelf extends BlockBookshelf {

    public MagicBookshelf(int par1) {
        super(par1);
        d("magic:magic_bookshelf_side")
        .c("magic_bookshelf")
        ;
    }

    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        return info.wasExploded() ? this.dropBlockAsEntityItem(info, Item.F.cv, 0, 1, 1.5F) + this.dropBlockAsEntityItem(info, Items.magicPaper.cv, 0, 1, 1.5F) : this.dropBlockAsEntityItem(info, Items.magicBook.cv, 0, 3, 1.0F);
    }

    public boolean canBeReplacedBy(int metadata, Block other_block, int other_block_metadata)
    {
        return false;
    }

}
