package pers.defoliation.magic.inventory.gui;

import common.defoliation.entity.PlayerEntity;
import common.defoliation.inventory.Inventory;
import common.defoliation.inventory.InventoryView;
import net.minecraft.*;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.EnchantmentNameParts;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;
import pers.defoliation.magic.Main;
import pers.defoliation.magic.inventory.MagicEnchantTableContainer;

import java.util.ArrayList;
import java.util.List;

public class GuiMagicEnchantment extends GuiContainer implements InventoryView {

    private static final ResourceLocation gui = new ResourceLocation("magic:textures/gui/container/enchanting_table.png");
    private MagicEnchantTableContainer container;
    public int t;
    public float u;
    public float v;
    public float w;
    public float x;
    public float y;
    public float z;
    private ItemStack itemStack;

    public GuiMagicEnchantment(MagicEnchantTableContainer container) {
        super(container);
        this.container = container;
    }

    @Override
    public void b(final int par1, final int par2) {
        this.o.b(I18n.a("container.enchant") , 12, 6, 2631720);
        this.o.b(I18n.a("container.inventory"), 7, this.d - 96 + 3, 2631720);
    }

    @Override
    public void a(float par1, int par2, int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.f.J().a(gui);
        final int var4 = (this.g - this.c) / 2;
        final int var5 = (this.h - this.d) / 2;
        this.b(var4, var5, 0, 0, this.c, this.d);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        final ScaledResolution var6 = new ScaledResolution(this.f.u, this.f.d, this.f.e);
        GL11.glViewport((var6.a() - 320) / 2 * var6.e(), (var6.b() - 240) / 2 * var6.e(), 320 * var6.e(), 240 * var6.e());
        GL11.glTranslatef(-0.34f, 0.23f, 0.0f);
        Project.gluPerspective(90.0f, 1.3333334f, 9.0f, 80.0f);
        final float var7 = 1.0f;
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        RenderHelper.b();
        GL11.glTranslatef(0.0f, 3.3f, -16.0f);
        GL11.glScalef(var7, var7, var7);
        final float var8 = 5.0f;
        GL11.glScalef(var8, var8, var8);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        this.f.J().a(GuiEnchantment.C);
        GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
        final float var9 = this.z + (this.y - this.z) * par1;
        GL11.glTranslatef((1.0f - var9) * 0.2f, (1.0f - var9) * 0.1f, (1.0f - var9) * 0.25f);
        GL11.glRotatef(-(1.0f - var9) * 90.0f - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        float var10 = this.v + (this.u - this.v) * par1 + 0.25f;
        float var11 = this.v + (this.u - this.v) * par1 + 0.75f;
        var10 = (var10 - MathHelper.b((double)var10)) * 1.6f - 0.3f;
        var11 = (var11 - MathHelper.b((double)var11)) * 1.6f - 0.3f;
        if (var10 < 0.0f) {
            var10 = 0.0f;
        }
        if (var11 < 0.0f) {
            var11 = 0.0f;
        }
        if (var10 > 1.0f) {
            var10 = 1.0f;
        }
        if (var11 > 1.0f) {
            var11 = 1.0f;
        }
        GL11.glEnable(32826);
        GuiEnchantment.D.a(null, 0.0f, var10, var11, var9, 0.0f, 0.0625f);
        GL11.glDisable(32826);
        RenderHelper.a();
        GL11.glMatrixMode(5889);
        GL11.glViewport(0, 0, this.f.d, this.f.e);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        RenderHelper.a();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        EnchantmentNameParts.a.a(this.container.f);
        List tooltips = null;
        for (int var12 = 0; var12 < 3; ++var12) {
            final String var13 = EnchantmentNameParts.a.a();
            this.n = 0.0f;
            this.f.J().a(GuiEnchantment.B);
            final int var14 = this.container.cost[var12];
            final int experience_cost = Enchantment.getExperienceCost(var14);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (var14 == 0) {
                this.b(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
            }
            else {
                final String var15 = "" + experience_cost;
                FontRenderer var16 = this.f.m;
                int var17 = 6839882;
                if (this.f.h.bJ < experience_cost && !this.f.h.bG.d) {
                    this.b(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
                    var16.a(var13, var4 + 62, var5 + 16 + 19 * var12, 104, (var17 & 0xFEFEFE) >> 1);
                    var16 = this.f.l;
                    var17 = 4226832;
                    var16.a(var15, var4 + 62 + 104 - var16.a(var15), var5 + 16 + 19 * var12 + 7, var17);
                }
                else {
                    final int var18 = par2 - (var4 + 60);
                    final int var19 = par3 - (var5 + 14 + 19 * var12);
                    if (var18 >= 0 && var19 >= 0 && var18 < 108 && var19 < 19) {
                        this.b(var4 + 60, var5 + 14 + 19 * var12, 0, 204, 108, 19);
                        var17 = 16777088;
                        if (this.f.f.areSkillsEnabled() && !this.f.h.hasSkill(Skill.ENCHANTING)) {
                            tooltips = new ArrayList();
                            tooltips.add(EnumChatFormat.h + "Requires " + Skill.ENCHANTING.getLocalizedName(true));
                        }
                    }
                    else {
                        this.b(var4 + 60, var5 + 14 + 19 * var12, 0, 166, 108, 19);
                    }
                    var16.a(var13, var4 + 62, var5 + 16 + 19 * var12, 104, var17);
                    var16 = this.f.l;
                    var17 = 8453920;
                    var16.a(var15, var4 + 62 + 104 - var16.a(var15), var5 + 16 + 19 * var12 + 7, var17);
                }
            }
        }
        if (tooltips != null) {
            this.a(tooltips, par2, par3);
        }
    }

    @Override
    public void a(final int par1, final int par2, final int par3) {
        super.a(par1, par2, par3);
        final int var4 = (this.g - this.c) / 2;
        final int var5 = (this.h - this.d) / 2;
        for (int var6 = 0; var6 < 3; ++var6) {
            final int var7 = par1 - (var4 + 60);
            final int var8 = par2 - (var5 + 14 + 19 * var6);
            if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && this.container.a(this.f.h, var6)) {
                this.f.c.a(this.container.d, var6);
            }
        }
    }

    public void g()
    {
        ItemStack var1 = this.e.a(0).d();
        if (!ItemStack.b(var1, this.itemStack))
        {
            this.itemStack = var1;
            do
            {
                this.w += Main.random.nextInt(4) - Main.random.nextInt(4);
            } while ((this.u <= this.w + 1.0F) && (this.u >= this.w - 1.0F));
        }
        this.t += 1;
        this.v = this.u;
        this.z = this.y;
        boolean var2 = false;
        for (int var3 = 0; var3 < 3; var3++) {
            if (this.container.cost[var3] != 0) {
                var2 = true;
            }
        }
        if (var2) {
            this.y += 0.2F;
        } else {
            this.y -= 0.2F;
        }
        if (this.y < 0.0F) {
            this.y = 0.0F;
        }
        if (this.y > 1.0F) {
            this.y = 1.0F;
        }
        float var5 = (this.w - this.u) * 0.4F;
        float var4 = 0.2F;
        if (var5 < -var4) {
            var5 = -var4;
        }
        if (var5 > var4) {
            var5 = var4;
        }
        this.x += (var5 - this.x) * 0.9F;
        this.u += this.x;
    }

    @Override
    public void c() {
        super.c();
        this.g();
    }

    @Override
    public Inventory getTopInventory() {
        return container.getTopInventory();
    }

    @Override
    public Inventory getBottomInventory() {
        return container.getBottomInventory();
    }

    @Override
    public PlayerEntity getPlayer() {
        return container.getPlayer();
    }
}
