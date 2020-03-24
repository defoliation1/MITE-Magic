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

    /**
     * 控制添加到物品上的概率
     * @param itemStack 附魔完毕，正在添加诅咒的物品
     * @param enchantLevel 附魔时选择的经验
     * @return 这个诅咒在附魔时添加到物品上的概率
     */
    @Override
    public int getProbability(ItemStack itemStack, int enchantLevel) {
        return 10;
    }

    public final CurseLevel<MagicCurse> withLevel(int level){
        return new CurseLevel(this,level);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }
}
