package pers.defoliation.magic.curse;

import common.defoliation.event.EventHandler;
import common.defoliation.event.player.PlayerPickupItemEvent;
import common.defoliation.mod.mite.inventory.ItemStackWrapper;
import net.minecraft.EnchantmentManager;
import net.minecraft.Item;
import net.minecraft.ItemStack;

public class PrideCurse extends MagicCurse {

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
        if (EnchantmentManager.hasEnchantment(itemStack, this)) {
            int level = EnchantmentManager.getEnchantmentLevel(this, itemStack);
            int needLevel = 20 + 10 * level;
            playerPickupItemEvent.setCancelled(!canHold(level,needLevel));
        }
    }

    public static boolean canHold(int itemEnchantLevel,int playerLevel){
        return playerLevel >= (20+10*itemEnchantLevel);
    }

}
