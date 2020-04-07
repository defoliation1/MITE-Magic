package pers.defoliation.magic.liar;

import net.minecraft.Item;
import net.minecraft.ItemRock;
import net.minecraft.ItemStack;
import team.unknowndomain.liar.annotation.Liar;

@Liar(ItemRock.class)
public class ItemRockLiar {

    public static int getExperienceValueWhenSacrificed(ItemStack item_stack)
    {
        Item item = item_stack.b();
        if ((item == Item.aY) && (item_stack.getItemSubtype() == 4)) {
            return 25;
        }

        //将吃石英获得经验改为100
        return item == Item.p ? 500 : item == Item.bJ ? 250 : item == Item.cb ? 100 : 0;
    }

}
