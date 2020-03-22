package pers.defoliation.magic.liar;

import net.minecraft.*;
import pers.defoliation.magic.curse.BulkyCurse;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

@Liar(EntityLiving.class)
public abstract class EntityLivingLiar extends Entity {

    @Deceive
    public EntityLivingLiar(World par1World) {
        super(par1World);
    }

    public void be() {
        this.y = 0.42100000381469727D;
        if (a(MobEffectList.j)) {
            this.y += (b(MobEffectList.j).c() + 1) * 0.1F;
        }
        int bulkyLevel = BulkyCurse.getMaxLevel((EntityLiving) (Object) this);
        if (bulkyLevel > 0) {
            this.y += bulkyLevel * -0.01;
        }
        if (ai()) {
            float var1 = this.A * 0.017453292F;
            this.x -= MathHelper.a(var1) * 0.2F;
            this.z += MathHelper.b(var1) * 0.2F;
        }
        this.an = true;
    }


    public final float getSpeedBoostVsSlowDown() {
        MobEffect slowdown_effect = b(MobEffectList.d);
        MobEffect haste_effect = b(MobEffectList.c);

        float slow_amount = slowdown_effect == null ? 0.0F : (slowdown_effect.c() + 1) * -0.2F;
        float haste_amount = haste_effect == null ? 0.0F : (haste_effect.c() + 1) * 0.2F;
        int bulkyLevel = BulkyCurse.getMaxLevel((EntityLiving) (Object) this);
        float bulky_amount = bulkyLevel == 0 ? 0 : bulkyLevel * -0.08f;
        if (this.K) {
            slow_amount -= 0.75F;
        }
        double overall_speed_modifier = slow_amount + haste_amount + bulky_amount;
        if (overall_speed_modifier < 0.0D) {
            overall_speed_modifier *= (1.0F - getResistanceToParalysis());
        }
        return (float) overall_speed_modifier;
    }

    @Deceive
    public float getResistanceToParalysis() {
        return 0;
    }

    @Deceive
    public boolean a(MobEffectList par1Potion) {
        return false;
    }

    @Deceive
    public MobEffect b(MobEffectList par1Potion) {
        return null;
    }

}
