package pers.defoliation.magic.block;

import net.minecraft.BlockEnchantmentTable;
import net.minecraft.Item;
import net.minecraft.ItemBlock;
import pers.defoliation.magic.Main;
import pers.defoliation.magic.block.tile.MagicEnchantTableTileEntity;
import pers.defoliation.magic.inventory.BaseGuiHandler;
import pers.defoliation.magic.item.Items;
import pers.defoliation.magic.liar.BlockEnchantmentTableLiar;

public class Blocks {

    public static MagicstoneOre magicstoneOre;
    public static MagicMetalBlock magicMetalBlock;
    public static BlockEnchantmentTable magicEnchantTable;
    public static MagicBookshelf magicBookshelf;
    public static MagicAnvil magicAnvil;

    public static void registerBlocks() {
        magicstoneOre = new MagicstoneOre(160, Items.magicstoneMaterial, 4);
        magicMetalBlock = new MagicMetalBlock(161, Items.magicstoneMaterial);
        magicEnchantTable = new BlockEnchantmentTable(162, Items.magicstoneMaterial);
        magicEnchantTable.c(2.4f).b(20f).d("magic:magic_enchanting_table").c("magic_enchanting_table");

        BlockEnchantmentTableLiar liar = (BlockEnchantmentTableLiar)(Object)magicEnchantTable;
        liar.setTileEntity(new MagicEnchantTableTileEntity());
        liar.setConsumer((playerEntity, location) -> playerEntity.openInventory(Main.mod, BaseGuiHandler.MAGIC_ENCHANT_TABLE,location));

        magicBookshelf = new MagicBookshelf(163);
        magicAnvil = new MagicAnvil(164, Items.magicstoneMaterial);
        Item.g[magicstoneOre.cF] = new ItemBlock(magicstoneOre);
        Item.g[magicMetalBlock.cF] = new ItemBlock(magicMetalBlock);
        Item.g[magicEnchantTable.cF] = new ItemBlock(magicEnchantTable);
        Item.g[magicBookshelf.cF] = new ItemBlock(magicBookshelf);
        Item.g[magicAnvil.cF] = new ItemBlock(magicAnvil);


    }
}
