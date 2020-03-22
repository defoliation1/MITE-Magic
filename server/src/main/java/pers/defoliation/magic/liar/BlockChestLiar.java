package pers.defoliation.magic.liar;

import net.minecraft.*;
import pers.defoliation.magic.curse.Curses;
import pers.defoliation.magic.curse.GluttonyCurse;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Liar(BlockChest.class)
public abstract class BlockChestLiar extends BlockDirectionalWithTileEntity {

    @Deceive
    public BlockChestLiar(int id, Material material, BlockConstants constants) {
        super(id, material, constants);
    }

    public boolean updateTick(World world, int x, int y, int z, Random rand) {
        if ((super.updateTick(world, x, y, z, rand)) && (world.getBlock(x, y, z) != this)) {
            return true;
        }
        TileEntityChest chest_entity = (TileEntityChest) world.r(x, y, z);
        if (chest_entity != null) {
            chest_entity.checkForWormComposting();
        }

        List<ItemStack> itemFoods = new ArrayList<>();
        List<ItemStack> gluttonyItems = new ArrayList<>();

        for (int i1 = 0; i1 < chest_entity.i.length; i1++) {
            ItemStack itemStack = chest_entity.i[i1];
            if (itemStack != null) {
                Item item = itemStack.b();
                if (itemStack.isEnchantable() && EnchantmentManager.hasEnchantment(itemStack, Curses.gluttony)) {
                    gluttonyItems.add(itemStack);
                } else if (item instanceof ItemFood)
                    itemFoods.add(itemStack);
            }
        }

        for (ItemStack gluttonyItem : gluttonyItems) {
            GluttonyCurse.modifier(gluttonyItem, itemFoods);
        }

        for (int i1 = 0; i1 < chest_entity.i.length; i1++) {
            ItemStack itemStack = chest_entity.i[i1];
            if (itemStack != null && itemStack.b <= 0) {
                chest_entity.a(i1,null);
            }
        }
        return false;
    }

}
