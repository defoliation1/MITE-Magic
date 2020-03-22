package pers.defoliation.magic.curse;

import common.defoliation.Location;
import common.defoliation.entity.PlayerEntity;
import common.defoliation.mod.liar.EntityHumanLiar;
import net.minecraft.EntityHuman;
import net.minecraft.Item;
import net.minecraft.ItemStack;

public class LygophobiaCurse extends MagicCurse {
    public LygophobiaCurse() {
        super("lygophobia");
    }

    @Override
    public boolean canCurse(Item item) {
        return true;
    }

    public static boolean modifier(EntityHuman entityHuman, ItemStack itemStack) {
        PlayerEntity mitePlayerEntity = ((EntityHumanLiar) (Object) entityHuman).getPlayer();
        Location location = mitePlayerEntity.getLocation();
        if (entityHuman.q.n(location.getBlockX(), location.getBlockY(), location.getBlockZ()) < 7){
            Curses.setEnchWork(itemStack,false);
            return false;
        }
        return true;
    }
}
