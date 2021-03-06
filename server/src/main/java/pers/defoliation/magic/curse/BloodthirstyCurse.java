package pers.defoliation.magic.curse;

import common.defoliation.MITE;
import common.defoliation.mod.liar.EntityHumanLiar;
import common.defoliation.mod.mite.DimensionManager;
import common.defoliation.mod.mite.inventory.ItemStackWrapper;
import common.defoliation.mod.mite.nbt.MITENBTTagCompound;
import net.minecraft.*;

public class BloodthirstyCurse extends MagicCurse {

    public BloodthirstyCurse() {
        super("bloodthirsty");
    }

    @Override
    public boolean canCurse(Item item) {
        return ((item instanceof ItemSword)) || ((item instanceof ItemScythe)) || item instanceof ItemBattleAxe
                || item instanceof ItemKnife || item instanceof ItemDagger || item instanceof ItemHatchet
                || item instanceof ItemCarrotStick || item instanceof ItemPickaxe;
    }

    public static void modifier(EntityHuman entityHuman, ItemStack itemStack) {
        CurseManager.INSTANCE.getCurseFromItemStack(itemStack, Curses.bloodthirsty).ifPresent(curseLevel -> {
            int bloodthirstyLevel = curseLevel.level;
            if (bloodthirstyLevel <= 0)
                return;
            long second = DimensionManager.getWorld(0).I();
            MITENBTTagCompound miteNBT = (MITENBTTagCompound) ItemStackWrapper.of(itemStack).getNBT();
            if (!miteNBT.hasKey("bloodthirsty")) {
                miteNBT.setLong("bloodthirsty", second);
                return;
            }
            long l = miteNBT.getLong("bloodthirsty");
            if (second - l > 17280 - 1320 * bloodthirstyLevel) {
                entityHuman.attackEntityFrom(new Damage(DamageSource.j, 2));
                miteNBT.setLong("bloodthirsty", second);
                if (MITE.getMITE().isRemote())
                    ((EntityHumanLiar) (Object) entityHuman).getPlayer().sendMessage(String.format(LocaleI18n.a("message.bloodthirsty.damage"), itemStack.s()));
            }
        });
    }
}
