package pers.defoliation.magic.curse;

import net.minecraft.Item;
import net.minecraft.ItemArmor;
import net.minecraft.item.EnumRarity;

public class PollutionCurse extends MagicCurse {

    public PollutionCurse() {
        super("pollution");
    }

    @Override
    public boolean canCurse(Item item) {
        return item instanceof ItemArmor;
    }
}
