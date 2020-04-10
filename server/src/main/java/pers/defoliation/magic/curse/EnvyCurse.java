package pers.defoliation.magic.curse;

import common.defoliation.MITE;
import common.defoliation.mod.liar.EntityHumanLiar;
import common.defoliation.mod.mite.DimensionManager;
import common.defoliation.mod.mite.inventory.ItemStackWrapper;
import common.defoliation.mod.mite.nbt.MITENBTTagCompound;
import net.minecraft.*;

public class EnvyCurse extends MagicCurse {

    public EnvyCurse() {
        super("envy");
    }

    @Override
    public boolean canCurse(Item item) {
        return true;
    }

    public static void modifier(EntityHuman entityHuman, ItemStack itemStack) {
        CurseManager.INSTANCE.getCurseFromItemStack(itemStack, Curses.envy).ifPresent(curseLevel -> {
            int envyLevel = curseLevel.level;
            if (envyLevel <= 0)
                return;
            long now = DimensionManager.getWorld(0).I();
            MITENBTTagCompound miteNBT = (MITENBTTagCompound) ItemStackWrapper.of(itemStack).getNBT();
            if (!miteNBT.hasKey("envy")) {
                miteNBT.setLong("envy", now);
                return;
            }

            PlayerInventory playerInventory = entityHuman.bn;

            if (playerInventory.getCurrentItemStack() == itemStack) {
                miteNBT.setLong("envy", now);
                return;
            }
            for (int i1 = 0; i1 < playerInventory.b.length; i1++) {
                if (playerInventory.b[i1] == itemStack) {
                    miteNBT.setLong("envy", now);
                    return;
                }
            }

            long l = miteNBT.getLong("envy");

            int dTime = 60 * 15 * 20 / envyLevel;

            if (now > l + dTime) {
                entityHuman.attackEntityFrom(new Damage(DamageSource.j, 1));
                if (MITE.getMITE().isRemote())
                    ((EntityHumanLiar) (Object) entityHuman).getPlayer().sendMessage(String.format(LocaleI18n.a("message.envy.damage"), itemStack.s()));
                miteNBT.setLong("envy", now);
            }
        });
    }

}
