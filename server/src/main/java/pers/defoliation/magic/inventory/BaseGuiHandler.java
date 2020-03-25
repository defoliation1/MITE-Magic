package pers.defoliation.magic.inventory;

import common.defoliation.Location;
import common.defoliation.entity.PlayerEntity;
import common.defoliation.inventory.GuiHandler;
import common.defoliation.inventory.InventoryView;
import common.defoliation.mod.mite.entity.MITEPlayerEntity;
import common.defoliation.mod.mite.inventory.GuiRegister;
import net.minecraft.EntityHuman;
import pers.defoliation.magic.Main;
import pers.defoliation.magic.inventory.gui.GuiMagicAnvilContainer;
import pers.defoliation.magic.inventory.gui.GuiMagicEnchantment;

public class BaseGuiHandler implements GuiHandler {

    public static final int MAGIC_ANVIL = 1;
    public static final int MAGIC_ENCHANT_TABLE = 2;

    public BaseGuiHandler() {
        GuiRegister.INSTANCE.register(Main.mod, this);
    }

    @Override
    public InventoryView getServerGui(int i, PlayerEntity playerEntity, Location location) {
        switch (i) {
            case MAGIC_ANVIL: {
                return new MagicAnvilContainer(((MITEPlayerEntity)playerEntity).getHandle(), location);
            }
            case MAGIC_ENCHANT_TABLE:{
                return new MagicEnchantTableContainer(((MITEPlayerEntity)playerEntity).getHandle(),location);
            }
        }
        return null;
    }

    @Override
    public InventoryView getClientGui(int i, PlayerEntity playerEntity, Location location) {
        switch (i) {
            case MAGIC_ANVIL: {
                return new GuiMagicAnvilContainer(new MagicAnvilContainer(((MITEPlayerEntity)playerEntity).getHandle(), location));
            }
            case MAGIC_ENCHANT_TABLE:{
                return new GuiMagicEnchantment(new MagicEnchantTableContainer(((MITEPlayerEntity)playerEntity).getHandle(),location));
            }
        }
        return null;
    }

}
