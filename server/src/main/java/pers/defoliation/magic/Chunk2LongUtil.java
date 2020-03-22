package pers.defoliation.magic;

import common.defoliation.Chunk;
import common.defoliation.Location;
import common.defoliation.World;

public class Chunk2LongUtil {

    public static long location2Long(Location location) {
        return getLongHash(location.getBlockX(), location.getBlockY());
    }

    public static Chunk getChunkByLong(World world,long l) {
        int x = getChunkXByLongHash(l);
        int y = getChunkYByLongHash(l);
        return world.getChunk(x,y);
    }

    private static int getChunkXByLongHash(long longHash) {
        int chunkX = (int) (longHash >> 28);
        if ((chunkX & 134217728) > 0) {
            chunkX &= -134217729;
            chunkX *= -1;
        }
        return chunkX;
    }

    private static int getChunkYByLongHash(long longHash) {
        int chunkY = (int) (longHash & 268435455);
        if ((chunkY & 134217728) > 0) {
            chunkY &= -134217729;
            chunkY *= -1;
        }
        return chunkY;
    }

    public static long getLongHash(int x, int y) {
        int x1 = a(x);
        int y1 = a(y);

        long l = x1;
        l = l << 28;
        l += y1;
        return l;
    }

    private static int a(int i) {
        if (i >= 0)
            return i >>> 4;
        else {
            i *= -1;
            return (i >>> 4) | 134217728;
        }
    }

}
