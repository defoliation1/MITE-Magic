package pers.defoliation.magic.block;

import common.defoliation.Location;
import common.defoliation.entity.PlayerEntity;
import common.defoliation.mod.liar.EntityPlayerLiar;
import net.minecraft.*;
import pers.defoliation.magic.Main;
import pers.defoliation.magic.block.tile.MagicAnvilTileEntity;
import pers.defoliation.magic.block.tile.MagicEnchantTableTileEntity;
import pers.defoliation.magic.inventory.BaseGuiHandler;

import java.util.Random;

public class MagicEnchantTable extends BlockContainer {

    public IIcon a;
    public IIcon b;

    public MagicEnchantTable(int par1) {
        super(par1, Material.stone, new BlockConstants());
        d("magic:magic_enchanting_table").
        c("magic_enchanting_table");
        this.setBlockBoundsForAllThreads(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
        this.k(255);
        this.a(CreativeModeTab.c);
    }

    public TileEntity b(World world) {
        return new MagicEnchantTableTileEntity();
    }

    public void b(World par1World, int par2, int par3, int par4, Random par5Random) {
        super.b(par1World, par2, par3, par4, par5Random);

        for(int var6 = par2 - 2; var6 <= par2 + 2; ++var6) {
            for(int var7 = par4 - 2; var7 <= par4 + 2; ++var7) {
                if (var6 > par2 - 2 && var6 < par2 + 2 && var7 == par4 - 1) {
                    var7 = par4 + 2;
                }

                if (par5Random.nextInt(16) == 0) {
                    for(int var8 = par3; var8 <= par3 + 1; ++var8) {
                        if (par1World.a(var6, var8, var7) == Block.as.cF) {
                            if (!par1World.c((var6 - par2) / 2 + par2, var8, (var7 - par4) / 2 + par4)) {
                                break;
                            }

                            par1World.spawnParticle(EnumParticle.enchantmenttable, (double)par2 + 0.5D, (double)par3 + 2.0D, (double)par4 + 0.5D, (double)((float)(var6 - par2) + par5Random.nextFloat()) - 0.5D, (double)((float)(var8 - par3) - par5Random.nextFloat() - 1.0F), (double)((float)(var7 - par4) + par5Random.nextFloat()) - 0.5D);
                        }
                    }
                }
            }
        }

    }

    public IIcon a(int par1, int par2) {
        return par1 == 0 ? this.b : (par1 == 1 ? this.a : this.cW);
    }

    public void a(IconRegister par1IconRegister) {
        this.cW = par1IconRegister.a(this.E() + "_" + "side");
        this.a = par1IconRegister.a(this.E() + "_" + "top");
        this.b = par1IconRegister.a("enchanting_table_bottom");
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityHuman player, EnumFace face, float offset_x, float offset_y, float offset_z)
    {
        if (!world.isAirOrPassableBlock(x, y + 1, z, false)) {
            return false;
        }
        if (player.onServer())
        {
            MagicEnchantTableTileEntity tile_entity = (MagicEnchantTableTileEntity)world.r(x, y, z);
            if (tile_entity != null) {
                PlayerEntity playerEntity = ((EntityPlayerLiar) player).getPlayer();
                playerEntity.openInventory(Main.mod, BaseGuiHandler.MAGIC_ENCHANT_TABLE,new Location(playerEntity.getWorld(),x,y,z));
            }
        }
        return true;
    }

    public boolean isFaceFlatAndSolid(int metadata, EnumFace face) {
        return face.isBottom();
    }

    public boolean hidesAdjacentSide(IBlockAccess block_access, int x, int y, int z, Block neighbor, int side) {
        return side == 1;
    }

    public boolean isStandardFormCube(boolean[] is_standard_form_cube, int metadata) {
        return false;
    }

    public boolean blocksPrecipitation(boolean[] blocks_precipitation, int metadata) {
        return true;
    }

}
