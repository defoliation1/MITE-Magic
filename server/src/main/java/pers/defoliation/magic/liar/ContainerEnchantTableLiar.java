package pers.defoliation.magic.liar;

import common.defoliation.Location;
import common.defoliation.mod.liar.WorldLiar;
import net.minecraft.*;
import pers.defoliation.magic.MagicEnchantmentManager;
import pers.defoliation.magic.curse.CurseManager;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

import java.util.List;
import java.util.Random;

@Liar(ContainerEnchantTable.class)
public abstract class ContainerEnchantTableLiar extends Container {

    public int i;
    public int j;
    public int k;
    public int[] g;
    public Random l;
    public IInventory a;

    @Deceive
    public ContainerEnchantTableLiar(EntityHuman player) {
        super(player);
    }

    public int getNumAccessibleBookshelves() {
        return MagicEnchantmentManager.getAccessibleBookshelves(new Location(((WorldLiar) (Object) world).getWorld(), i, j, k), 24);
    }

    public int calcEnchantmentLevelsForSlot(Random random, int slot_index, int num_accessible_bookshelves, ItemStack item_stack) {
        return MagicEnchantmentManager.calcEnchantmentLevelsForSlot(new Location(((WorldLiar) (Object) world).getWorld(), i, j, k), slot_index, num_accessible_bookshelves, item_stack);
    }

    public boolean a(final EntityHuman par1EntityPlayer, final int par2) {
        final ItemStack var3 = this.a.a(0);
        final int experience_cost = Enchantment.getExperienceCost(this.g[par2]);
        if (this.g[par2] > 0 && var3 != null && (par1EntityPlayer.bJ >= experience_cost || par1EntityPlayer.bG.d)) {
            if (!this.world.I) {
                if (ItemPotion.isBottleOfWater(var3)) {
                    par1EntityPlayer.s(-experience_cost);
                    this.a.a(0, new ItemStack(Item.bF));
                    return true;
                }
                final List var4 = EnchantmentManager.b(this.l, var3, this.g[par2]);
                final boolean var5 = var3.d == Item.aN.cv;
                if (var4 != null) {
                    par1EntityPlayer.s(-experience_cost);
                    if (var5) {
                        var3.d = Item.bY.cv;
                    }
                    final int var6 = var5 ? this.l.nextInt(var4.size()) : -1;
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
                    Block enchantment_table_block = this.world.getBlock(this.i, this.j, this.k);
                    int antiCurseLevel = enchantment_table_block == Block.enchantmentTableEmerald ? 1500 : 2000;
                    CurseManager.INSTANCE.randomApplyCurse(var3, experience_cost, antiCurseLevel);
                    this.a(0).f();
                }
            }
            return true;
        }
        return false;
    }

}
