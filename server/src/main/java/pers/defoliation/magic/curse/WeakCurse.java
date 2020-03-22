package pers.defoliation.magic.curse;

import net.minecraft.EntityHuman;
import net.minecraft.Item;
import net.minecraft.ItemArmor;

public class WeakCurse extends MagicCurse {

    public WeakCurse() {
        super("weak");
    }

    @Override
    public boolean canCurse(Item item) {
        return item instanceof ItemArmor;
    }

    public static void modifier(EntityHuman entityHuman, int weakLevel) {
        //设置氧气值           获得氧气值
        entityHuman.g(entityHuman.al() - weakLevel);
        if (entityHuman.al() < 0)
            //设置为不冲刺
            entityHuman.c(false);
    }


}
