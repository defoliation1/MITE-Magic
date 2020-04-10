package pers.defoliation.magic.block;

import common.defoliation.Location;
import common.defoliation.MITE;
import common.defoliation.entity.PlayerEntity;
import common.defoliation.mod.liar.EntityPlayerLiar;
import net.minecraft.*;
import pers.defoliation.magic.Main;
import pers.defoliation.magic.block.tile.MagicAnvilTileEntity;
import pers.defoliation.magic.inventory.BaseGuiHandler;

import java.util.Objects;

public class MagicAnvil extends BlockAnvil {

    public MagicAnvil(int par1, Material metal_type) {
        super(par1, metal_type);
        c("magic_anvil");
        c(3.0f);
    }

    public void a(IconRegister par1IconRegister) {
        this.cW = par1IconRegister.a("magic:magic_anvil_base");
        this.e = new IIcon[d.length];

        for(int var2 = 0; var2 < this.e.length; ++var2) {
            this.e[var2] = par1IconRegister.a("magic:magic_anvil_" + d[var2]);
        }
    }

    public int getDurability() {
        return 128;
    }

    public TileEntity b(World world) {
        return new MagicAnvilTileEntity();
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityHuman player, EnumFace face, float offset_x, float offset_y, float offset_z)
    {
        if (!world.isAirOrPassableBlock(x, y + 1, z, false)) {
            return false;
        }
        if (player.onServer())
        {
            MagicAnvilTileEntity tile_entity = (MagicAnvilTileEntity)world.r(x, y, z);
            if (tile_entity != null) {
                PlayerEntity playerEntity = ((EntityPlayerLiar) player).getPlayer();
                playerEntity.openInventory(Main.mod, BaseGuiHandler.MAGIC_ANVIL,new Location(playerEntity.getWorld(),x,y,z));
            }
        }
        return true;
    }

}
