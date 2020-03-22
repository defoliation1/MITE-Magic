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
        CurseManager.INSTANCE.getCurseFromItemStack(itemStack,Curses.envy).ifPresent(curseLevel -> {
            int envyLevel = curseLevel.level;
            if (envyLevel <= 0)
                return;
            long now = DimensionManager.getWorld(0).I() / 20;
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

            int dTime = 60 * 20 * 5 / envyLevel;

            if (now > l + dTime && (l+dTime)%(dTime/5)==0) {
                entityHuman.g(1f);
                if (MITE.getMITE().isRemote())
                    ((EntityHumanLiar) (Object) entityHuman).getPlayer().sendMessage(LocaleI18n.a("message.envy.damage", itemStack.s()));
            }
        });
    }

}
