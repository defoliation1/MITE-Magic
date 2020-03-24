package pers.defoliation.magic;

import common.defoliation.Location;
import common.defoliation.MITE;
import common.defoliation.event.EventHandler;
import common.defoliation.event.block.BlockBreakEvent;
import common.defoliation.event.block.BlockPlaceEvent;
import common.defoliation.mod.mite.MITEWorld;
import it.unimi.dsi.fastutil.longs.Long2BooleanOpenHashMap;
import net.minecraft.*;
import pers.defoliation.magic.block.Blocks;

import java.util.Random;
import java.util.WeakHashMap;

public class MagicEnchantmentManager {

    private static WeakHashMap<Location, BookshelvesCache> cache = new WeakHashMap<>();

    private static Long2BooleanOpenHashMap chunkUpdate = new Long2BooleanOpenHashMap();

    private static final Random random = new Random(System.currentTimeMillis());

    private MagicEnchantmentManager() {

    }

    static {
        chunkUpdate.defaultReturnValue(false);
        MITE.getMITE().registerEventHandler(Main.mod,new MagicEnchantmentManager());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        Location location = blockPlaceEvent.getBlockPlaced().getLocation();
        chunkUpdate.put(Chunk2LongUtil.getLongHash(location.getBlockX(), location.getBlockZ()), false);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockPlaceEvent) {
        Location location = blockPlaceEvent.getBlock().getLocation();
        chunkUpdate.put(Chunk2LongUtil.getLongHash(location.getBlockX(), location.getBlockZ()), false);
    }

    public static int getAccessibleBookshelves(Location location, int maxBookshelves) {
        if (canUseCache(location) && cache.containsKey(location)) {
            BookshelvesCache bookshelvesCache = cache.get(location);
            if (System.currentTimeMillis() - bookshelvesCache.createTime > 1000 * 60 * 5) {
                cache.remove(location);
            } else {
                return bookshelvesCache.num;
            }
        }
        World world = ((MITEWorld) location.getWorld()).getHandle();
        int num_bookshelves = 0;
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        for (int dy = 0; dy <= 1 && (dy != 1 || world.isAirOrPassableBlock(x, y + 1, z, false)); ++dy) {
            final boolean[][] is_block_accessible = new boolean[5][5];
            if (world.isAirOrPassableBlock(x, y + dy, z - 1, false)) {
                is_block_accessible[2][0] = true;
                if (world.isAirOrPassableBlock(x - 1, y + dy, z - 1, false)) {
                    is_block_accessible[0][1] = true;
                    is_block_accessible[1][0] = true;
                }
                if (world.isAirOrPassableBlock(x + 1, y + dy, z - 1, false)) {
                    is_block_accessible[3][0] = true;
                    is_block_accessible[4][1] = true;
                }
            }
            if (world.isAirOrPassableBlock(x + 1, y + dy, z, false)) {
                is_block_accessible[4][2] = true;
                if (world.isAirOrPassableBlock(x + 1, y + dy, z - 1, false)) {
                    is_block_accessible[3][0] = true;
                    is_block_accessible[4][1] = true;
                }
                if (world.isAirOrPassableBlock(x + 1, y + dy, z + 1, false)) {
                    is_block_accessible[4][3] = true;
                    is_block_accessible[3][4] = true;
                }
            }
            if (world.isAirOrPassableBlock(x, y + dy, z + 1, false)) {
                is_block_accessible[2][4] = true;
                if (world.isAirOrPassableBlock(x + 1, y + dy, z + 1, false)) {
                    is_block_accessible[4][3] = true;
                    is_block_accessible[3][4] = true;
                }
                if (world.isAirOrPassableBlock(x - 1, y + dy, z + 1, false)) {
                    is_block_accessible[1][4] = true;
                    is_block_accessible[0][3] = true;
                }
            }
            if (world.isAirOrPassableBlock(x - 1, y + dy, z, false)) {
                is_block_accessible[0][2] = true;
                if (world.isAirOrPassableBlock(x - 1, y + dy, z + 1, false)) {
                    is_block_accessible[1][4] = true;
                    is_block_accessible[0][3] = true;
                }
                if (world.isAirOrPassableBlock(x - 1, y + dy, z - 1, false)) {
                    is_block_accessible[0][1] = true;
                    is_block_accessible[1][0] = true;
                }
            }
            loop:
            for (int dx = -2; dx <= 2; ++dx) {
                for (int dz = -2; dz <= 2; ++dz) {
                    if (is_block_accessible[2 + dx][2 + dz]) {
                        if (world.getBlock(x + dx, y + dy, z + dz) == Block.as) {
                            ++num_bookshelves;
                        } else if (world.getBlock(x + dx, y + dy, z + dz) == Blocks.magicBookshelf) {
                            num_bookshelves += 2;
                        }
                        if (num_bookshelves >= maxBookshelves) {
                            num_bookshelves = maxBookshelves;
                            break loop;
                        }
                    }
                }
            }
        }
        cache.put(location, new BookshelvesCache(num_bookshelves));
        updateChunkFlag(location);
        return num_bookshelves;
    }

    private static void updateChunkFlag(Location location) {
        int x = location.getBlockX();
        int z = location.getBlockZ();
        chunkUpdate.put(Chunk2LongUtil.getLongHash(x + 2, z + 2), true);
        chunkUpdate.put(Chunk2LongUtil.getLongHash(x - 2, z + 2), true);
        chunkUpdate.put(Chunk2LongUtil.getLongHash(x + 2, z - 2), true);
        chunkUpdate.put(Chunk2LongUtil.getLongHash(x - 2, z - 2), true);
    }

    private static boolean canUseCache(Location location) {
        int x = location.getBlockX();
        int z = location.getBlockZ();
        if (chunkUpdate.get(Chunk2LongUtil.getLongHash(x + 2, z + 2))
                && chunkUpdate.get(Chunk2LongUtil.getLongHash(x - 2, z + 2))
                && chunkUpdate.get(Chunk2LongUtil.getLongHash(x + 2, z - 2))
                && chunkUpdate.get(Chunk2LongUtil.getLongHash(x - 2, z - 2)))
            return true;
        return false;
    }

    private static class BookshelvesCache {

        public final int num;
        public final long createTime = System.currentTimeMillis();

        public BookshelvesCache(int num) {
            this.num = num;
        }
    }


    public static int calcEnchantmentLevelsForSlot(Location location,int slot_index, int accessibleBookshelves, ItemStack itemStack){

        final Item item = itemStack.b();
        if (ItemPotion.isBottleOfWater(itemStack)) {
            return 2;
        }
        if (item.c() <= 0) {
            return 0;
        }
        final Block enchantment_table_block = ((MITEWorld)location.getWorld()).getHandle().getBlock(location.getBlockX(),location.getBlockY(),location.getBlockZ());
        final int enchantment_table_power = (1 + accessibleBookshelves) * ((enchantment_table_block == Block.enchantmentTableEmerald) ? 2 : 4);
        final int enchantment_levels = EnchantmentManager.getEnchantmentLevelsAlteredByItemEnchantability(enchantment_table_power, item);
        float fraction = (1.0f + slot_index) / 3.0f;
        if (slot_index < 2) {
            fraction += (random.nextFloat() - 0.5f) * 0.2f;
        }
        return Math.max(Math.round(enchantment_levels * fraction), 1);
    }

}
