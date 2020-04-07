package pers.defoliation.magic.entity.client;

import net.minecraft.Entity;
import net.minecraft.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderZombie;

public class PigZombieLordRender extends RenderZombie {

    public void setTextures()
    {
        setTexture(0,"magic:textures/entity/lord_pig_zombie");
    }

    public ResourceLocation a(Entity par1Entity)
    {
        return this.textures[0];
    }

}
