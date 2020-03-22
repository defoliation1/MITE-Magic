package pers.defoliation.magic.liar;

import common.defoliation.mod.liar.EntityHumanLiar;
import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import pers.defoliation.magic.curse.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Liar(EntityPlayer.class)
public abstract class EntityPlayerLiar extends EntityHuman {

    public String bN;
    public PlayerConnection a;
    public MinecraftServer b;
    public PlayerInteractManager c;
    public double d;
    public double e;
    public List f;
    public List g;
    public float bO;
    public float bP;
    public int last_satiation;
    public int last_nutrition;
    public int protein;
    public int essential_fats;
    public int phytonutrients;
    public int last_experience;
    public int bT;
    public int bU;
    public int bV;
    public boolean bW;
    public long bX;
    public int bY;
    public boolean h;
    public int i;
    public boolean j;
    public boolean[] Sr;
    public boolean raS;
    public int respawn_experience;
    public double pos_x_at_last_world_map_update;
    public double pos_z_at_last_world_map_update;
    public int ticks_logged_in;
    public int[] runegate_destination_coords;
    public boolean sync_client_player_on_next_tick;
    public int portal_grace_ticks;
    public boolean is_not_on_hit_list;
    public boolean initial_on_update;
    public boolean master_hash_received;
    public boolean master_hash_validated;
    public int sacred_stones_placed;
    public int allotted_time;
    public boolean is_disconnecting_while_in_bed;
    public boolean try_push_out_of_blocks;
    public int last_skill_learned_on_day;
    public short respawn_countdown;
    public List referenced_books_read;
    public float vision_dimming_on_client;
    public long prevent_item_pickup_due_to_held_item_breaking_until;
    public double last_received_motion_x;
    public double last_received_motion_z;
    public boolean set_position_in_bed_next_tick;

    @Deceive
    public EntityPlayerLiar(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    public void l_() {
        if (this.set_position_in_bed_next_tick) {
            if (this.inBed()) {
                this.setSizeProne();
                this.setPositionAndRotationInBed();
            }
            this.set_position_in_bed_next_tick = false;
        }
        if (this.try_push_out_of_blocks && this.ac > 1) {
            if (this.pushOutOfBlocks() == -1) {
            }
            this.try_push_out_of_blocks = false;
        }
        if (this.bu != null && this.isZevimrgvInTournament()) {
            this.bG.c = true;
        }
        if (Minecraft.inDevMode() || this.isZevimrgvInTournament()) {
            final int load = (int) (this.b.getLoadOnServer() * 100.0f);
            this.sendPacket(new Packet85SimpleSignal(EnumSignal.server_load).setShort((load < 1000) ? load : 1000));
        }
        if (this.initial_on_update) {
            if (DedicatedServer.tournament_type == EnumTournamentType.score) {
                DedicatedServer.generatePrizeKeyFile((EntityPlayer) (Object) this);
                DedicatedServer.updateTournamentScoreOnClient(this, false);
            } else if (DedicatedServer.isTournament()) {
                DedicatedServer.generatePrizeKeyFile((EntityPlayer) (Object) this);
            }
            this.initial_on_update = false;
        }
        if (this.ac % 24 == 0) {
            final SoonestReconnectionTime srt = DedicatedServer.getSoonestReconnectionTime(this.bu);
            if (srt != null && srt.ticks_disconnected > 0L) {
                final SoonestReconnectionTime soonestReconnectionTime = srt;
                --soonestReconnectionTime.ticks_disconnected;
            }
        }
        if (!this.master_hash_received && this.ticks_logged_in >= 20 && this.ticks_logged_in % 20 == 0) {
            if (this.ticks_logged_in < 1000) {
                this.sendPacket(new Packet85SimpleSignal(EnumSignal.mh).setInteger((int) this.q.H()));
            } else {
                this.b.an().b(this.bu + " never sent a master hash!");
                this.master_hash_received = true;
            }
        }
        this.sendPacket(new Packet85SimpleSignal(EnumSignal.malnourished).setInteger(((this.protein == 0) ? 1 : 0) | ((this.phytonutrients == 0) ? 4 : 0) | EnumInsulinResistanceLevel.getOrdinalForTransmission(this.insulin_resistance_level) << 3 | this.getInsulinResistance() << 8));
        if (this.sync_client_player_on_next_tick) {
            this.syncClientPlayer();
            this.sync_client_player_on_next_tick = false;
        }
        this.updateMinecartFuelAmounts();
        ++this.ticks_logged_in;
        if (this.portal_grace_ticks > 0) {
            --this.portal_grace_ticks;
        }
        if (this.isUnderworldBottomBedrockVisible()) {
            this.a(AchievementList.portalToNether);
        }
        if (super.f != null && super.f.b() instanceof ItemBow) {
            this.addHungerServerSide(0.01f * EnchantmentManager.getEnduranceModifier(this));
        }
        final WorldServer world = (WorldServer) this.q;
        this.bq.onUpdate((EntityPlayer) (Object) this);
        final float chance_of_snow_items_melting = Item.getChanceOfSnowAndIceItemsMelting(this.af() ? 20.0f : this.getBiome().F);
        if (chance_of_snow_items_melting > 0.0f) {
            for (int i = 0; i < this.bn.a.length; ++i) {
                final ItemStack item_stack = this.bn.getInventorySlotContents(i);
                if (item_stack != null) {
                    if ((item_stack.hasMaterial(Material.x, true) || item_stack.hasMaterial(Material.y, true) || item_stack.hasMaterial(Material.w, true)) && item_stack.subjectToChanceOfDisappearing(chance_of_snow_items_melting, this.ab).b < 1) {
                        this.bn.a(i, null);
                    }
                }
            }
        }
        this.tickPlayerInventory();
        if (this.ac % 100 == 0) {
            if (Math.random() < 0.009999999776482582 && this.isOnHitList() && world.isThundering(true) && world.F(this.getBlockPosX(), this.getBlockPosY(), this.getBlockPosZ())) {
                world.c(new EntityLightning(world, this.u, this.v, this.w));
                this.tryDamageArmor(DamageSource.j, 20.0f, null);
                this.attackEntityFrom(new Damage(DamageSource.divine_lightning, 20.0f));
            }
            if (this.ac % 1000 == 0) {
                List blocks = null;
                final int x = this.getBlockPosX();
                final int y = this.getBlockPosY();
                final int z = this.getBlockPosZ();
                final int range = 5;
                final int ranged_squared = range * range;
                for (int dx = -range; dx <= range; ++dx) {
                    for (int dz = -range; dz <= range; ++dz) {
                        for (int dy = -range; dy <= range; ++dy) {
                            final int block_x = x + dx;
                            final int block_y = y + dy;
                            final int block_z = z + dz;
                            if (world.isAirOrPassableBlock(block_x - 1, block_y, block_z, false) || world.isAirOrPassableBlock(block_x + 1, block_y, block_z, false) || world.isAirOrPassableBlock(block_x, block_y, block_z - 1, false) || world.isAirOrPassableBlock(block_x, block_y, block_z + 1, false)) {
                                final float block_center_x = block_x + 0.5f;
                                final float block_center_y = block_y + 0.5f;
                                final float block_center_z = block_z + 0.5f;
                                if (this.canEntityBeSeenFrom(block_center_x - 1.0f, block_center_y, block_center_z, ranged_squared) || this.canEntityBeSeenFrom(block_center_x + 1.0f, block_center_y, block_center_z, ranged_squared) || this.canEntityBeSeenFrom(block_center_x, block_center_y - 1.0f, block_center_z, ranged_squared) || this.canEntityBeSeenFrom(block_center_x, block_center_y + 1.0f, block_center_z, ranged_squared) || this.canEntityBeSeenFrom(block_center_x, block_center_y, block_center_z - 1.0f, ranged_squared) || this.canEntityBeSeenFrom(block_center_x, block_center_y, block_center_z + 1.0f, ranged_squared)) {
                                    final Block block = this.q.getBlock(block_x, block_y, block_z);
                                    if (block instanceof BlockMonsterEggs) {
                                        if (this.q.isOverworld()) {
                                            if (this.q.a(EnumSkyBlock.a, block_x - 1, block_y, block_z) > 7) {
                                                continue;
                                            }
                                            if (this.q.a(EnumSkyBlock.a, block_x + 1, block_y, block_z) > 7) {
                                                continue;
                                            }
                                            if (this.q.a(EnumSkyBlock.a, block_x, block_y + 1, block_z) > 7) {
                                                continue;
                                            }
                                            if (this.q.a(EnumSkyBlock.a, block_x, block_y, block_z - 1) > 7) {
                                                continue;
                                            }
                                            if (this.q.a(EnumSkyBlock.a, block_x, block_y, block_z + 1) > 7) {
                                                continue;
                                            }
                                        }
                                        if (blocks == null) {
                                            blocks = new ArrayList();
                                        }
                                        blocks.add(new ChunkPosition(block_x, block_y, block_z));
                                    }
                                }
                            }
                        }
                    }
                }
                if (blocks != null) {
                    for (Object o : blocks) {
                        ChunkPosition block_pos = (ChunkPosition) o;
                        if (!this.q.getAsWorldServer().doesQueuedBlockOperationExist(block_pos.a, block_pos.b, block_pos.c, EnumBlockOperation.spawn_silverfish)) {
                            final int fake_entity_id = Entity.obtainNextEntityID();
                            world.watchAnimal(fake_entity_id, block_pos.a, block_pos.b, block_pos.c, 4);
                            world.e(2001, block_pos.a, block_pos.b, block_pos.c, Block.bq.cF + (world.h(block_pos.a, block_pos.b, block_pos.c) << 12));
                            this.q.getAsWorldServer().addScheduledBlockOperation(EnumBlockOperation.spawn_silverfish, block_pos.a, block_pos.b, block_pos.c, this.q.I() + 20L, false, this);
                        }
                    }
                }
            }
        }
        Label_1880:
        {
            if (this.ac >= 60) {
                if (this.ac != 60) {
                    if (World.getDistanceSqFromDeltas(this.u - this.pos_x_at_last_world_map_update, 0.0, this.w - this.pos_z_at_last_world_map_update) <= 16.0) {
                        break Label_1880;
                    }
                }
            }
        }
        if (this.bT > 0) {
            --this.bT;
        }
        this.bp.b();
        if (!this.q.I && !this.bp.a((EntityHuman) this)) {
            this.i();
            this.bp = this.bo;
        }
        while (!this.g.isEmpty()) {
            final int var1 = Math.min(this.g.size(), 127);
            final int[] var2 = new int[var1];
            final Iterator var3 = this.g.iterator();
            int var4 = 0;
            while (var3.hasNext() && var4 < var1) {
                var2[var4++] = (int) var3.next();
                var3.remove();
            }
            this.a.b(new Packet29DestroyEntity(var2));
        }
        if (!this.f.isEmpty()) {
            if (this.f.size() > 500) {
                Debug.setErrorMessage("EntityPlayerMP: loadedChunks.size() > 500");
            }
            final ArrayList var5 = new ArrayList();
            final Iterator var6 = this.f.iterator();
            final ArrayList var7 = new ArrayList();
            final long ms = System.currentTimeMillis();
            while (var6.hasNext() && var5.size() < 5) {
                final ChunkCoordIntPair var8 = (ChunkCoordIntPair) var6.next();
                if (this.q.doesChunkAndAllNeighborsExist(var8.a, var8.b, MITEConstant.considerNeighboringChunksInLightingArtifactPrevention() ? 2 : 1, false)) {
                    var6.remove();
                    final Chunk chunk = this.q.e(var8.a, var8.b);
                    if (!chunk.isWithinBlockDomain()) {
                        continue;
                    }
                    if (MITEConstant.preventLightingArtifacts()) {
                        Packet56MapChunkBulk.checkLighting(chunk);
                    }
                    var5.add(chunk);
                    var7.addAll(((WorldServer) this.q).c(var8.a * 16, 0, var8.b * 16, var8.a * 16 + 16, 256, var8.b * 16 + 16));
                    if (System.currentTimeMillis() - ms > 10L) {
                        break;
                    }
                    if (MinecraftServer.F().getLoadOnServer() > 0.8f) {
                        break;
                    }
                    continue;
                }
            }
            if (!var5.isEmpty()) {
                this.a.b(new Packet56MapChunkBulk(var5));
                for (Object var10 : var7) {
                    this.b((TileEntity) var10);
                }
                for (Object var11 : var5) {
                    this.p().q().a((EntityPlayer) (Object) this, (Chunk) var11);
                }
            }
        }
        if (this.bX > 0L && this.b.ar() > 0 && MinecraftServer.aq() - this.bX > this.b.ar() * 1000 * 60) {
            this.a.c("You have been idle for too long!");
        }
        if (DedicatedServer.isTournamentThatUsesAllottedTimes()) {
            if (this.allotted_time > 0) {
                --this.allotted_time;
            }
            if (this.allotted_time == 0 && this.isZevimrgvInTournament()) {
                this.allotted_time = 20;
            }
            if (this.allotted_time == 0) {
                DedicatedServer.players_kicked_for_depleted_time_shares.put(this.bu, new Long(this.q.I()));
                this.a.c("Time share depleted");
            } else if (this.allotted_time % 20 == 0) {
                this.sendPacket(new Packet85SimpleSignal(EnumSignal.allotted_time).setInteger(this.allotted_time));
            }
        }
        final double distance_sq_to_world_spawn = this.getDistanceSqToWorldSpawnPoint(false);
        if (this.q.isOverworld() && distance_sq_to_world_spawn >= 1.0E8 && distance_sq_to_world_spawn < 4.0E8) {
            this.a(AchievementList.explorer);
        }
        if (this.ac % 10 == 0 && this.o instanceof EntityBoat) {
            final int x2 = this.getBlockPosX();
            final int y2 = this.getFootBlockPosY();
            final int z2 = this.getBlockPosZ();
            boolean eligible = true;
            for (int dy2 = -4; eligible && dy2 <= -1; ++dy2) {
                for (int dx2 = -8; eligible && dx2 <= 8; ++dx2) {
                    for (int dz2 = -8; eligible && dz2 <= 8; ++dz2) {
                        if (this.q.g(x2 + dx2, y2 + dy2, z2 + dz2) != Material.h) {
                            eligible = false;
                            break;
                        }
                    }
                }
            }
            if (eligible) {
                this.a(AchievementList.seaworthy);
            }
        }
    }

    @Deceive
    public void syncClientPlayer() {
    }

    @Deceive
    public void updateMinecartFuelAmounts() {
    }

    @Deceive
    public boolean isUnderworldBottomBedrockVisible() {
        return false;
    }

    public void tickPlayerInventory() {
        //TODO 物品吃食物可以写这里
        boolean players_eyes_inside_water = a(Material.h);
        boolean steam_and_hiss = false;
        //不保证这个list里的ItemStack的数量都为0以上或不为空
        List<ItemStack> itemFoods = new ArrayList<>();
        List<ItemStack> gluttonyItems = new ArrayList<>();
        for (int i = 0; i < this.bn.a.length + this.bn.b.length; i++) {
            ItemStack item_stack = this.bn.getInventorySlotContents(i);
            if (item_stack != null) {
                Item item = item_stack.b();
                if (players_eyes_inside_water) {
                    if ((item instanceof ItemVessel)) {
                        if ((item instanceof ItemBucket)) {
                            ItemBucket bucket = (ItemBucket) item;
                            if (bucket.contains(Material.i)) {
                                this.bn.convertAllItemsInSlot(i, bucket.getPeerForContents(Material.stone));
                                steam_and_hiss = true;
                            }
                        }
                    }
                }


                if (item_stack.isEnchantable()) {
                    if (EnchantmentManager.hasEnchantment(item_stack, Curses.pride)) {
                        int level = EnchantmentManager.getEnchantmentLevel(Curses.pride, item_stack);
                        if (!PrideCurse.canHold(level, ((EntityHumanLiar) (Object) getAsPlayer()).getPlayer().getLevel())) {
                            this.bn.a(i, null);
                            dropItemStack(item_stack, 1f);
                            continue;
                        }
                    }
                    Curses.setEnchWork(item_stack, true);
                    boolean work = true;
                    if (EnchantmentManager.hasEnchantment(item_stack, Curses.lygophobia)) {
                        work = LygophobiaCurse.modifier(this, item_stack);
                    }
                    if (work && EnchantmentManager.hasEnchantment(item_stack, Curses.social)) {
                        work = SocialCurse.modifier(item_stack, this.bn.b);
                    }
                    if (work && EnchantmentManager.hasEnchantment(item_stack, Curses.asocial)) {
                        work = AsocialCurse.modifier(item_stack, this.bn.b);
                    }
                    if (work && EnchantmentManager.hasEnchantment(item_stack, Curses.sloth)) {
                        SlothCurse.modifier(item_stack);
                    }

                    if (EnchantmentManager.hasEnchantment(item_stack, Curses.bloodthirsty))
                        BloodthirstyCurse.modifier(this, item_stack);
                    if (EnchantmentManager.hasEnchantment(item_stack, Curses.gluttony))
                        gluttonyItems.add(item_stack);
                    if (EnchantmentManager.hasEnchantment(item_stack, Curses.envy))
                        EnvyCurse.modifier(this, item_stack);
                } else if (item instanceof ItemFood)
                    itemFoods.add(item_stack);
            }
        }

        for (ItemStack gluttonyItem : gluttonyItems) {
            GluttonyCurse.modifier(gluttonyItem, itemFoods);
        }

        for (int i = 0; i < this.bn.a.length; i++) {
            ItemStack item_stack = this.bn.getInventorySlotContents(i);
            if (item_stack != null && item_stack.b <= 0) {
                this.bn.a(i, null);
            }
        }

        if (steam_and_hiss) {
            entityFX(EnumEntityFX.steam_with_hiss);
        }
    }


    @Deceive
    public boolean isOnHitList() {
        return false;
    }

    @Deceive
    public void b(TileEntity par1TileEntity) {
    }

    @Deceive
    public WorldServer p() {
        return null;
    }

}
