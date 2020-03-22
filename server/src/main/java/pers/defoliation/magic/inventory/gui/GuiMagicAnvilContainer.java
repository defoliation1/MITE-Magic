package pers.defoliation.magic.inventory.gui;

import common.defoliation.entity.PlayerEntity;
import common.defoliation.inventory.Inventory;
import common.defoliation.inventory.InventoryView;
import net.minecraft.ItemStack;
import net.minecraft.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.opengl.GL11;
import pers.defoliation.magic.inventory.MagicAnvilContainer;

public class GuiMagicAnvilContainer extends GuiContainer implements InventoryView {

    private static final ResourceLocation gui = new ResourceLocation("textures/gui/container/anvil.png");

    private MagicAnvilContainer magicAnvilContainer;

    public GuiMagicAnvilContainer(MagicAnvilContainer par1Container) {
        super(par1Container);
        magicAnvilContainer = par1Container;
    }

    @Override
    public void a(float v, int i, int i1) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        f.J().a(gui);
        int var4 = (g - c) / 2;
        int var5 = (h - d) / 2;
        b(var4, var5, 0, 0, c, d);
//        b(var4 + 59, var5 + 20, 0, d + (canBeRenamed(magicAnvilContainer.a(0).d()) ? 0 : 16), 110, 16);

        if (((magicAnvilContainer.a(0).e()) || (magicAnvilContainer.a(1).e())) && (!magicAnvilContainer.a(2).e())) {
            b(var4 + 99, var5 + 45, c, 0, 28, 21);
        }
        if (magicAnvilContainer.getTopInventory().getItem(0) != null)
            if (magicAnvilContainer.cost > magicAnvilContainer.player.getExperienceValue())
                o.b("§4需要: " + magicAnvilContainer.cost + " xp", var4 + 99, var5 + 35, 4210752);
            else {
                o.b("§2需要: " + magicAnvilContainer.cost + " xp", var4 + 99, var5 + 35, 4210752);
            }
    }

    public boolean canBeRenamed(ItemStack item_stack) {
        return (item_stack != null) && (item_stack.canBeRenamed());
    }

    @Override
    public Inventory getTopInventory() {
        return null;
    }

    @Override
    public Inventory getBottomInventory() {
        return null;
    }

    @Override
    public PlayerEntity getPlayer() {
//        return e.player);
        return null;
    }
}
