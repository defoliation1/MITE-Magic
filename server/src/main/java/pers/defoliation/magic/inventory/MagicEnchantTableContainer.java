package pers.defoliation.magic.inventory;

import common.defoliation.Location;
import common.defoliation.entity.PlayerEntity;
import common.defoliation.inventory.Inventory;
import common.defoliation.inventory.InventoryView;
import common.defoliation.mod.liar.EntityPlayerLiar;
import common.defoliation.mod.mite.inventory.MITEInventory;
import common.defoliation.mod.mite.inventory.SimpleInventory;
import net.minecraft.*;
import pers.defoliation.magic.MagicEnchantmentManager;
import pers.defoliation.magic.Main;
import pers.defoliation.magic.block.MagicEnchantTable;
import pers.defoliation.magic.curse.CurseManager;

import java.util.List;

public class MagicEnchantTableContainer extends Container implements InventoryView {

    private Location location;
    private SimpleInventory simpleInventory = new SimpleInventory(1){
        public void e()
        {
            super.e();
            MagicEnchantTableContainer.this.a(this);
        }
    };
    public int[] cost = new int[3];
    public long f;

    public MagicEnchantTableContainer(EntityHuman player, Location location) {
        super(player);
        this.location = location;
        this.a(new Slot(simpleInventory, 0, 25, 47) {
            public boolean a(ItemStack par1ItemStack) {
                return par1ItemStack.isEnchantable();
            }
        });
        for (int var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                this.a(new Slot(player.bn, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }
        for (int var6 = 0; var6 < 9; ++var6) {
            this.a(new Slot(player.bn, var6, 8 + var6 * 18, 142));
        }
    }

    //绑定？
    public void a(ICrafting par1ICrafting) {
        super.a(par1ICrafting);
        par1ICrafting.a(this, 0, this.cost[0]);
        par1ICrafting.a(this, 1, this.cost[1]);
        par1ICrafting.a(this, 2, this.cost[2]);
    }

    //tick更新
    public void b() {
        super.b();
        for (int var1 = 0; var1 < this.e.size(); var1++) {
            ICrafting var2 = (ICrafting) this.e.get(var1);
            var2.a(this, 0, this.cost[0]);
            var2.a(this, 1, this.cost[1]);
            var2.a(this, 2, this.cost[2]);
        }
    }

    public void b(int par1, int par2) {
        if ((par1 >= 0) && (par1 <= 2)) {
            this.cost[par1] = par2;
        } else {
            super.b(par1, par2);
        }
    }

    @Override
    public void a(final IInventory par1IInventory) {
        if (par1IInventory == this.simpleInventory) {
            final ItemStack var2 = par1IInventory.a(0);
            if (var2 != null && var2.isEnchantable()) {
                this.f = Main.random.nextLong();
                if (!this.world.I) {
                    final int num_accessible_bookshelves = MagicEnchantmentManager.getAccessibleBookshelves(location, 48);
                    for (int slot_index = 0; slot_index < 3; ++slot_index) {
                        this.cost[slot_index] = MagicEnchantmentManager.calcEnchantmentLevelsForSlot(location, slot_index, num_accessible_bookshelves, var2);
                    }
                    this.b();
                }
            } else {
                for (int var3 = 0; var3 < 3; ++var3) {
                    this.cost[var3] = 0;
                }
            }
        }
    }

    //确认附魔，par2为选项
    @Override
    public boolean a(final EntityHuman par1EntityPlayer, final int par2) {
        final ItemStack var3 = this.simpleInventory.a(0);
        final int experience_cost = Enchantment.getExperienceCost(this.cost[par2]);
        if (this.cost[par2] > 0 && var3 != null && (par1EntityPlayer.bJ >= experience_cost || par1EntityPlayer.bG.d)) {
            if (!this.world.I) {
                if (ItemPotion.isBottleOfWater(var3)) {
                    par1EntityPlayer.s(-experience_cost);
                    this.simpleInventory.a(0, new ItemStack(Item.bF));
                    return true;
                }
                if (ItemGoldenApple.isUnenchantedGoldenApple(var3)) {
                    par1EntityPlayer.s(-experience_cost);
                    this.simpleInventory.a(0, new ItemStack(Item.av, 1, 1));
                    return true;
                }
                final List var4 = EnchantmentManager.b(Main.random, var3, this.cost[par2]);
                final boolean var5 = var3.d == Item.aN.cv;
                if (var4 != null) {
                    par1EntityPlayer.s(-experience_cost);
                    if (var5) {
                        var3.d = Item.bY.cv;
                    }
                    final int var6 = var5 ? Main.random.nextInt(var4.size()) : -1;
                    for (int var7 = 0; var7 < var4.size(); ++var7) {
                        final EnchantmentInstance var8 = (EnchantmentInstance) var4.get(var7);
                        if (!var5 || var7 == var6) {
                            if (var5) {
                                Item.bY.a(var3, var8);
                            } else {
                                var3.a(var8.b, var8.c);
                            }
                        }
                    }
                    CurseManager.INSTANCE.randomApplyCurse(var3, experience_cost, 3000);
                    this.a(0).f();
                }
            }
            return true;
        }
        return false;
    }

    //shift
    @Override
    public ItemStack b(final EntityHuman par1EntityPlayer, final int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.c.get(par2);
        if ((var4 != null) && (var4.e()))
        {
            ItemStack var5 = var4.d();
            var3 = var5.m();
            if (par2 == 0)
            {
                if (!a(var5, 1, 37, true)) {
                    return null;
                }
            }
            else
            {
                if ((((Slot)this.c.get(0)).e()) || (!((Slot)this.c.get(0)).a(var5))) {
                    return null;
                }
                if ((var5.p()) && (var5.b == 1))
                {
                    ((Slot)this.c.get(0)).c(var5.m());
                    var5.b = 0;
                }
                else if (var5.b >= 1)
                {
                    ItemStack item_stack = new ItemStack(var5.d, 1, var5.getItemSubtype());
                    if (var5.i()) {
                        item_stack.setItemDamage(var5.k());
                    }
                    if (var5.b().hasQuality()) {
                        item_stack.setQuality(var5.getQuality());
                    }
                    ((Slot)this.c.get(0)).c(item_stack);
                    var5.b -= 1;
                }
            }
            if (var5.b == 0) {
                var4.c((ItemStack)null);
            } else {
                var4.f();
            }
            if (var5.b == var3.b) {
                return null;
            }
            var4.a(par1EntityPlayer, var5);
        }
        return var3;
    }

    @Override
    public void b(final EntityHuman par1EntityPlayer) {
        super.b(par1EntityPlayer);
        if (!this.world.I) {
            final ItemStack var2 = this.simpleInventory.a_(0);
            if (var2 != null) {
                par1EntityPlayer.b(var2);
            }
        }
    }

    @Override
    public Inventory getTopInventory() {
        return new MITEInventory(simpleInventory);
    }

    @Override
    public Inventory getBottomInventory() {
        return ((EntityPlayerLiar) player).getPlayer().getPlayerInventory();
    }

    @Override
    public PlayerEntity getPlayer() {
        return ((EntityPlayerLiar) player).getPlayer();
    }

    @Override
    public boolean a(EntityHuman entityHuman) {
        return world.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ()) instanceof MagicEnchantTable;
    }
}
