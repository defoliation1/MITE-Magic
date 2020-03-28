package pers.defoliation.magic.liar;

import common.defoliation.Location;
import common.defoliation.entity.PlayerEntity;
import common.defoliation.mod.liar.EntityPlayerLiar;
import net.minecraft.*;
import team.unknowndomain.liar.annotation.Liar;

import java.util.function.BiConsumer;

@Liar(BlockEnchantmentTable.class)
public class BlockEnchantmentTableLiar {

    private TileEntity tileEntity;
    private BiConsumer<PlayerEntity, Location> consumer;

    public void setTileEntity(TileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    public void setConsumer(BiConsumer<PlayerEntity, Location> consumer) {
        this.consumer = consumer;
    }

    public TileEntity b(World par1World) {
        if (tileEntity != null)
            return tileEntity;
        return new TileEntityEnchantTable();
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityHuman player, EnumFace face, float offset_x, float offset_y, float offset_z) {
        if (!world.isAirOrPassableBlock(x, y + 1, z, false)) {
            return false;
        } else {
            if (player.onServer()) {
                if (tileEntity != null) {
                    TileEntity tile_entity = world.r(x, y, z);
                    if (tile_entity != null && consumer != null) {
                        PlayerEntity playerEntity = ((EntityPlayerLiar) player).getPlayer();
                        consumer.accept(playerEntity, new Location(playerEntity.getWorld(), x, y, z));
                    }
                } else {
                    TileEntityEnchantTable tile_entity = (TileEntityEnchantTable) world.r(x, y, z);
                    if (tile_entity != null) {
                        player.a(x, y, z, tile_entity.b() ? tile_entity.a() : null);
                    }
                }
            }

            return true;
        }
    }


}
