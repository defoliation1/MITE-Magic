package pers.defoliation.magic.entity;

import common.defoliation.mod.mite.register.SpawnIn;
import net.minecraft.*;

import java.util.List;

@SpawnIn(8)
public class PigZombieLord extends EntityPigZombie implements IRangedEntity {

    private PathfinderGoalArrowAttack bp = new PathfinderGoalArrowAttack(this, 1.0D, 20, 60, 15.0F);
    private PathfinderGoalMeleeAttack bq = new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.2D, false);

    private ItemStack spareItem;

    public PigZombieLord(World par1World) {
        super(par1World);
        this.c.clear();

        this.c.a(0, new PathfinderGoalFloat(this));
        this.c.a(1, new PathfinderGoalBreakDoor(this));
        this.c.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.c.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        this.c.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.c.a(7, new PathfinderGoalRandomLookaround(this));

        this.d.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.d.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));

        this.c.a(2, new EntityAIMoveToFoodItem(this, 1.0F, true));

        changeAI();
    }

    public void az() {
        super.az();
        this.a((IAttribute) GenericAttributes.d).a(0.55D);
        this.a((IAttribute) GenericAttributes.e).a(9.0D);
    }

    public void addRandomEquipment() {
        setHeldItemStack(new ItemStack(Item.m).randomizeForMob(this, true));
        spareItem = new ItemStack(Item.I).randomizeForMob(this, true);
        setHelmet(new ItemStack(Item.an).randomizeForMob(this, true));
        setCuirass(new ItemStack(Item.ao).randomizeForMob(this, true));
        setLeggings(new ItemStack(Item.ap).randomizeForMob(this, true));
        setBoots(new ItemStack(Item.aq).randomizeForMob(this, true));
    }

    @Override
    public void a(EntityLiving entityLiving, float v) {
        EntityArrow var3 = new EntityArrow(this.q, this, entityLiving, 1.6F, 14 - this.q.r * 4, Item.arrowRustedIron, false);
        int var4 = EnchantmentManager.a(Enchantment.v.z, getHeldItemStack());
        int var5 = EnchantmentManager.a(Enchantment.w.z, getHeldItemStack());

        double damage = v * 2.0F + this.ab.nextGaussian() * 0.25D + this.q.r * 0.11F;

        var3.b(damage);
        if (var4 > 0) {
            var3.b(var3.c() + var4 * 0.5D + 0.5D);
        }
        if (var5 > 0) {
            var3.a(var5);
        }
        a("random.bow", 1.0F, 1.0F / (aD().nextFloat() * 0.4F + 0.8F));
        this.q.d(var3);
    }

    public void changeAI() {
        //移除AI
        this.c.a(this.bq);
        this.c.a(this.bp);

        ItemStack var1 = getHeldItemStack();

        if (var1 != null && var1.b() instanceof ItemBow) {
            this.c.a(4, this.bp);

            this.c.a(3, new EntityAISeekFiringPosition(this, 1.0F, true));
        } else {
            this.c.a(4, this.bq);
        }
    }

    public void setHeldItemStack(ItemStack item_stack) {
        super.setHeldItemStack(item_stack);
        if (onServer()) {
            bT();
        }
    }

    public void c() {
        super.c();
        if (getTarget() != null && getTicksExistedWithOffset() % 200 == 0) {
            List list = this.q.a(EntityPigZombie.class, this.E.b(getMaxTargettingRange(), 6, getMaxTargettingRange()));
            if (list.isEmpty())
                return;
            for (Object o : list) {
                EntityPigZombie entityPigZombie = (EntityPigZombie) o;
                entityPigZombie.c(getTarget());
            }
        }
        if ((getHeldItemStack() == null && spareItem != null) || getTicksExistedWithOffset() % 20 == 0) {
            if (getHeldItemStack() == null) {
                swapWeapon();
            } else {
                Entity entity = getTarget();
                if (entity != null && canSeeTarget(true)) {
                    double distance = d(entity);
                    if (getHeldItem() instanceof ItemBow) {
                        if (distance < 5d)
                            swapWeapon();
                    } else if (distance > 6d)
                        swapWeapon();
                }
            }
        }
    }

    public void swapWeapon() {
        ItemStack itemStack = spareItem;
        spareItem = getHeldItemStack();
        setHeldItemStack(itemStack);
    }

    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        if (par1NBTTagCompound.b("spare_item_stack")) {
            this.spareItem = ItemStack.a(par1NBTTagCompound.l("spare_item_stack"));
        } else {
            this.spareItem = null;
        }
    }

    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        if (this.spareItem != null)
        {
            NBTTagCompound compound = new NBTTagCompound();
            this.spareItem.b(compound);

            par1NBTTagCompound.a("spare_item_stack", compound);
        }
    }

}
