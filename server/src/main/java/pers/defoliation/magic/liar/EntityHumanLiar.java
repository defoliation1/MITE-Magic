package pers.defoliation.magic.liar;

import net.minecraft.*;
import pers.defoliation.magic.curse.CurseLevel;
import pers.defoliation.magic.curse.CurseManager;
import pers.defoliation.magic.curse.Curses;
import pers.defoliation.magic.curse.WeakCurse;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

import java.util.Optional;

@Liar(EntityHuman.class)
public abstract class EntityHumanLiar extends EntityLiving {

    public PlayerInventory bn;

    @Deceive
    public EntityHumanLiar(World par1World) {
        super(par1World);
    }

    public void j(double par1, double par3, double par5) {
        if (this.o == null) {
            if (a(Material.h)) {
                int var7 = Math.round(MathHelper.a(par1 * par1 + par3 * par3 + par5 * par5) * 100.0F);
                if (var7 > 0) {
                    a(StatisticList.q, var7);
                    if (onServer()) {
                        addHungerServerSide(0.015F * var7 * 0.01F);
                    }
                }
            } else if (H()) {
                int var7 = Math.round(MathHelper.a(par1 * par1 + par5 * par5) * 100.0F);
                if (var7 > 0) {
                    a(StatisticList.m, var7);
                    if (onServer()) {
                        addHungerServerSide(0.015F * var7 * 0.01F);
                    }
                }
            } else if (e()) {
                if (par3 > 0.0D) {
                    a(StatisticList.o, (int) Math.round(par3 * 100.0D));
                    if (onServer()) {
                        addHungerServerSide((float) par3 / 10.0F);
                    }
                }
            } else if (this.F) {
                int var7 = Math.round(MathHelper.a(par1 * par1 + par5 * par5) * 100.0F);
                if (inBed()) {
                    var7 = 0;
                }
                if (var7 > 0) {
                    a(StatisticList.l, var7);
                    if (ai()) {
                        if (onServer()) {
                            addHungerServerSide(0.05F * var7 * 0.01F);

                            int weakLevel = 0;

                            for (int i1 = 0; i1 < bn.b.length; i1++) {
                                ItemStack itemStack = bn.b[i1];
                                if (itemStack == null)
                                    continue;
                                Optional<CurseLevel<WeakCurse>> optional= CurseManager.INSTANCE.getCurseFromItemStack(itemStack,Curses.weak);
                                if(optional.isPresent())
                                    weakLevel+=optional.get().level;
                            }
                            if (weakLevel > 0)
                                WeakCurse.modifier((EntityHuman) (Object) this, weakLevel);
                        }
                    } else if (onServer()) {
                        addHungerServerSide(0.01F * var7 * 0.01F);
                    }
                }
            } else {
                int var7 = Math.round(MathHelper.a(par1 * par1 + par5 * par5) * 100.0F);
                if (var7 > 25) {
                    a(StatisticList.p, var7);
                }
            }
        } else if ((this.o instanceof EntityBoat)) {
            if (onClient()) {
                if (this.bf != 0.0F) {
                    addHungerClientSide(Math.abs(this.bf) * 0.01F);
                }
            }
        }
    }

    @Deceive
    public void addHungerServerSide(float hunger) {
    }

    @Deceive
    public void addHungerClientSide(float hunger) {
    }

    @Deceive
    public void a(Statistic par1StatBase, int par2) {
    }

}
