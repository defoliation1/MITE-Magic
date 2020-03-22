package pers.defoliation.magic.curse;

import net.minecraft.ItemStack;
import net.minecraft.LocaleI18n;

public abstract class MagicCurse implements Curse{

    private final String name;

    public MagicCurse(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTranslateName() {
        return LocaleI18n.a("curse."+getName());
    }

    @Override
    public int getProbability(ItemStack itemStack, int enchantLevel) {
        return 10;
    }

    public final CurseLevel<MagicCurse> withLevel(int level){
        return new CurseLevel(this,level);
    }

}
