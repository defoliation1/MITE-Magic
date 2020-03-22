package pers.defoliation.magic.liar;

import net.minecraft.*;
import pers.defoliation.magic.Main;
import pers.defoliation.magic.curse.CurseLevel;
import pers.defoliation.magic.curse.CurseManager;
import pers.defoliation.magic.curse.Curses;
import pers.defoliation.magic.curse.RustCurse;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

import java.util.Optional;

@Liar(ItemStack.class)
public class ItemStackLiar {

    public int b;
    public int c;
    public int d;
    public NBTTagCompound e;
    public int subtype;
    public int damage;
    public EntityItemFrame g;
    public EnumQuality quality;
    public boolean is_artifact;

    public ItemDamageResult tryDamageItem(final World world, int damage, final boolean prevent_destruction) {
        if (!this.g() || damage < 1) {
            return null;
        }
        Optional<CurseLevel<RustCurse>> curseFromItemStack = CurseManager.INSTANCE.getCurseFromItemStack((ItemStack) (Object) this, Curses.rust);
        if (curseFromItemStack.isPresent()) {
            damage = RustCurse.modifier(damage, curseFromItemStack.get().level);
        }

        final float fraction_of_unbreaking = this.getEnchantmentLevelFraction(Enchantment.t);
        if (fraction_of_unbreaking > 0.0f) {
            int points_negated = 0;
            final float chance_of_negation_per_point = fraction_of_unbreaking * 0.75f;
            for (int i = 0; i < damage; ++i) {
                if (Main.random.nextFloat() < chance_of_negation_per_point) {
                    ++points_negated;
                }
            }
            damage -= points_negated;
        }
        if (prevent_destruction && this.damage + damage >= this.l()) {
            damage = this.l() - this.damage - 1;
        }
        if (damage <= 0) {
            return null;
        }
        final ItemDamageResult result = new ItemDamageResult().setItemLostDurability();
        this.setItemDamage(this.damage + damage);
        return (this.damage >= this.l()) ? result.setItemWasDestroyed(world, (ItemStack) (Object) this) : result;
    }

    public boolean isEnchantable() {
        if (b() == Item.aN) {
            return true;
        }
        if ((ItemPotion.isBottleOfWater((ItemStack) (Object) this))) {
            return true;
        }
        if (e() != 1) {
            return false;
        }
        if (!g()) {
            return false;
        }
        return (b().c() > 0) && (!y());
    }

    @Deceive
    public Item b() {
        return null;
    }

    @Deceive
    public int e() {
        return 0;
    }

    @Deceive
    public boolean g() {
        return false;
    }

    @Deceive
    public boolean y() {
        return false;
    }

    @Deceive
    public float getEnchantmentLevelFraction(Enchantment enchantment) {
        return 0;
    }

    @Deceive
    public int l() {
        return 1;
    }

    @Deceive
    public ItemStack setItemDamage(int damage) {
        return null;
    }

}
