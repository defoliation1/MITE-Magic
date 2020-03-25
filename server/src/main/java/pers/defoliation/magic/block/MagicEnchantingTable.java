package pers.defoliation.magic.block;

import common.defoliation.Location;
import common.defoliation.entity.PlayerEntity;
import common.defoliation.mod.liar.EntityPlayerLiar;
import net.minecraft.*;
import pers.defoliation.magic.Main;
import pers.defoliation.magic.block.tile.MagicAnvilTileEntity;
import pers.defoliation.magic.block.tile.MagicEnchantTableTileEntity;
import pers.defoliation.magic.inventory.BaseGuiHandler;

public class MagicEnchantingTable extends BlockEnchantmentTable {

    public MagicEnchantingTable(int par1, Material gem_type) {
        super(par1, gem_type);
        c("magic_enchanting_table");
    }

    public TileEntity b(World world) {
        return new MagicEnchantTableTileEntity();
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityHuman player, EnumFace face, float offset_x, float offset_y, float offset_z)
    {
        if (!world.isAirOrPassableBlock(x, y + 1, z, false)) {
            return false;
        }
        if (player.onServer())
        {
            MagicEnchantTableTileEntity tile_entity = (MagicEnchantTableTileEntity)world.r(x, y, z);
            if (tile_entity != null) {
                PlayerEntity playerEntity = ((EntityPlayerLiar) player).getPlayer();
                playerEntity.openInventory(Main.mod, BaseGuiHandler.MAGIC_ENCHANT_TABLE,new Location(playerEntity.getWorld(),x,y,z));
            }
        }
        return true;
    }

}
