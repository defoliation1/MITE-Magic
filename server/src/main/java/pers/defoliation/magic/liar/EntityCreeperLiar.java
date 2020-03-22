package pers.defoliation.magic.liar;

import net.minecraft.*;
import pers.defoliation.magic.curse.BlasterCurse;
import pers.defoliation.magic.curse.Curses;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

@Liar(EntityCreeper.class)
public abstract class EntityCreeperLiar extends EntityMonster {

    public int bp;
    public int bq;
    public int br;
    public float bs;
    public boolean has_exploded;
    public int recently_took_damage_from_conspicuous_cactus;

    @Deceive
    public EntityCreeperLiar(World par1World) {
        super(par1World);
    }

    public void l_() {
        if (this.recently_took_damage_from_conspicuous_cactus > 0) {
            this.recently_took_damage_from_conspicuous_cactus -= 1;
        }
        if (T()) {
            if (j instanceof EntityHuman) {
                EntityHuman entityHuman = (EntityHuman) j;
                int maxLevel = 0;
                for (ItemStack itemStack : entityHuman.bn.b) {
                    if (EnchantmentManager.hasEnchantment(itemStack, Curses.blaster)) {
                        int level = EnchantmentManager.getEnchantmentLevel(Curses.blaster, itemStack);
                        if (level > maxLevel)
                            maxLevel = level;
                    }
                }
                if (EnchantmentManager.hasEnchantment(entityHuman.getHeldItemStack(), Curses.blaster)) {
                    int level = EnchantmentManager.getEnchantmentLevel(Curses.blaster, entityHuman.getHeldItemStack());
                    if (level > maxLevel)
                        maxLevel = level;
                }
                if(maxLevel>0){
                    bq = br;
                    this.bs = BlasterCurse.getExplosiveSize(maxLevel);
                }
            }
            this.bp = this.bq;
            int var1 = bV();
            if ((var1 > 0) && (this.bq == 0)) {
                a("random.fuse", 1.0F, 0.5F);
            }
            this.bq += var1;
            if (this.bq < 0) {
                this.bq = 0;
            }
            if (this.bq >= this.br) {
                this.bq = this.br;
                if (!this.q.I) {
                    boolean var2 = this.q.O().b("mobGriefing");

                    float explosion_size_vs_blocks = this.bs * 0.715F;
                    float explosion_size_vs_living_entities = this.bs * 1.1F;
                    if (bT()) {
                        this.q.createExplosion(this, this.u, this.v + this.P / 4.0F, this.w, explosion_size_vs_blocks * 2.0F, explosion_size_vs_living_entities * 2.0F, var2);
                    } else {
                        this.q.createExplosion(this, this.u, this.v + this.P / 4.0F, this.w, explosion_size_vs_blocks, explosion_size_vs_living_entities, var2);
                    }
                    this.has_exploded = true;

                    entityFX(EnumEntityFX.frags);

                    x();
                }
            }
        }
        super.l_();
    }

    @Deceive
    public boolean bT() {
        return false;
    }

    @Deceive
    public int bV() {
        return 0;
    }
}
