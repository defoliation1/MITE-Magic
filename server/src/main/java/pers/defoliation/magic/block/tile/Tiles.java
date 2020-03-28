package pers.defoliation.magic.block.tile;

import net.minecraft.TileEntity;

public class Tiles {

    public static void registerTile() {
        TileEntity.a(MagicAnvilTileEntity.class, "MagicAnvil");
        TileEntity.a(MagicEnchantTableTileEntity.class, "MagicEnchantTable");
    }

}
