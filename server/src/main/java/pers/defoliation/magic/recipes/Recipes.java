package pers.defoliation.magic.recipes;

import net.minecraft.Block;
import net.minecraft.CraftingManager;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import pers.defoliation.magic.block.Blocks;

import static pers.defoliation.magic.item.Items.*;

public class Recipes {

    public static void registerRecipes() {
        CraftingManager craftingManager = CraftingManager.a();
        craftingManager.addRecipe(new ItemStack(magicstone, 1), true,
                "###", "###", "###", '#', magicstoneShard);
        setAroundItem(magicIngot,1,Item.ancientMetalNugget,magicstoneShard);
        setAroundItem(magicPaper,8,Item.aM,magicstoneShard);
        setAroundItem(magicLeather,8,Item.aH,magicstoneShard);
        craftingManager.addShapelessRecipe(new ItemStack(magicBook,1),magicPaper,magicPaper,magicPaper,magicLeather);
        craftingManager.addRecipe(new ItemStack(Blocks.magicEnchantTable),true,
                " # ","XAX","AAA",'#',magicBook,"X",magicstone,"A", Block.au);
        craftingManager.addRecipe(new ItemStack(Blocks.magicBookshelf),true,
                "MMM","SSS","MMM",'M',Block.C,'S',magicBook);
        craftingManager.addRecipe(new ItemStack(Blocks.magicAnvil),true,
                "KKK"," D ","DDD",'K',Blocks.magicMetalBlock,'D',magicIngot);
    }

    private static void setAroundItem(Item result,int num,Item i1,Item i2){
        CraftingManager craftingManager = CraftingManager.a();
        craftingManager.addRecipe(new ItemStack(result, num), true,
                "###", "#X#", "###", '#', i1, 'X', i2);
    }

}
