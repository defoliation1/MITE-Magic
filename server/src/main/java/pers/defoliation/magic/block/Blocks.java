package pers.defoliation.magic.block;

import net.minecraft.Item;
import net.minecraft.ItemBlock;
import pers.defoliation.magic.item.Items;

public class Blocks {

    public static MagicstoneOre magicstoneOre;
    public static MagicMetalBlock magicMetalBlock;
    public  static MagicEnchantingTable magicEnchantingTable;
    public static MagicBookshelf magicBookshelf;
    public static MagicAnvil magicAnvil;

    public static void registerBlocks(){
        magicstoneOre = new MagicstoneOre(160, Items.magicstoneMaterial,4);
        magicMetalBlock = new MagicMetalBlock(161,Items.magicstoneMaterial);
        magicEnchantingTable = new MagicEnchantingTable(162,Items.magicstoneMaterial);
        magicBookshelf = new MagicBookshelf(163);
        magicAnvil = new MagicAnvil(164,Items.magicstoneMaterial);
        Item.g[magicstoneOre.cF] = new ItemBlock(magicstoneOre);
        Item.g[magicMetalBlock.cF] = new ItemBlock(magicMetalBlock);
        Item.g[magicEnchantingTable.cF] = new ItemBlock(magicEnchantingTable);
        Item.g[magicBookshelf.cF] = new ItemBlock(magicBookshelf);
        Item.g[magicAnvil.cF] = new ItemBlock(magicAnvil);
    }
}
