package pers.defoliation.magic.curse;

import common.defoliation.event.EventHandler;
import common.defoliation.event.player.PlayerPickupItemEvent;
import common.defoliation.mod.mite.inventory.ItemStackWrapper;
import net.minecraft.EnchantmentManager;
import net.minecraft.Item;
import net.minecraft.ItemStack;

import java.util.Optional;

public class PrideCurse extends MagicCurse {

    private final CurseManager curseManager = CurseManager.INSTANCE;

    public PrideCurse() {
        super("pride");
    }

    @Override
    public boolean canCurse(Item item) {
        return true;
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent playerPickupItemEvent) {
        ItemStack itemStack = ((ItemStackWrapper) playerPickupItemEvent.getItem().getItemStack()).getHandler();
        Optional<CurseLevel<PrideCurse>> curseLevel = curseManager.getCurseFromItemStack(itemStack,this);
        if(curseLevel.isPresent()){
            int level = curseLevel.get().level;
            int needLevel = 20 + 10 * level;
            playerPickupItemEvent.setCancelled(!canHold(level,needLevel));
        }
    }

    public static boolean canHold(int itemEnchantLevel,int playerLevel){
        return playerLevel >= (20+10*itemEnchantLevel);
    }

}
