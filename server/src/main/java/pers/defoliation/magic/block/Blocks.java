package pers.defoliation.magic.block;

import net.minecraft.*;
import pers.defoliation.magic.item.Items;

public class Blocks {

    public static MagicstoneOre magicstoneOre;
    public static MagicMetalBlock magicMetalBlock;
    public  static MagicEnchantTable magicEnchantingTable;
    public static MagicBookshelf magicBookshelf;
    public static MagicAnvil magicAnvil;

    public static void registerBlocks(){
        magicstoneOre = new MagicstoneOre(160, Items.magicstoneMaterial,2);
        magicMetalBlock = new MagicMetalBlock(161, Material.f);
        magicEnchantingTable = new MagicEnchantTable(162);
        magicBookshelf = new MagicBookshelf(163);
        magicAnvil = new MagicAnvil(164,Items.magicstoneMaterial);

        for (int direction = 0; direction < 6; direction++) {
            for (int metadata = 0; metadata < 16; metadata++) {
                if (magicEnchantingTable.isValidMetadata(metadata)) {
                    if ((magicEnchantingTable.use_neighbor_brightness[(metadata + direction * 16)] = magicEnchantingTable.useNeighborBrightness(magicEnchantingTable.use_neighbor_brightness, metadata, EnumDirection.get(direction)))) {
                        magicEnchantingTable.x[162] = true;
                    }
                }
            }
        }

        Item.g[magicstoneOre.cF] = new ItemBlock(magicstoneOre);
        Item.g[magicMetalBlock.cF] = new ItemBlock(magicMetalBlock);
        Item.g[magicEnchantingTable.cF] = new ItemBlock(magicEnchantingTable);
        Item.g[magicBookshelf.cF] = new ItemBlock(magicBookshelf);
        Item.g[magicAnvil.cF] = new ItemAnvil(magicAnvil){
            @Override
            public BlockAnvil getBlock() {
                return magicAnvil;
            }
        };
    }
}
