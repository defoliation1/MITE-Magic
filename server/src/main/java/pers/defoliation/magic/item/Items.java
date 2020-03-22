package pers.defoliation.magic.item;

import net.minecraft.Material;

public class Items {

    public static Material magicstoneMaterial = new Material("magicstone");
    private static int id = 4000 - 256;

    public static MagicstoneShard magicstoneShard;
    public static MagicIngot magicIngot;
    public static Magicstone magicstone;
    public static MagicBook magicBook;
    public static MagicLeather magicLeather;
    public static MagicPaper magicPaper;

    static {
        magicstoneMaterial.setDurability(2).
                setMinHarvestLevel(3)
                .setRockyMineral()
                .f()
                .setMetal(true);
    }

    public static void registerItems() {

        magicstoneShard = new MagicstoneShard(nextItemId(), magicstoneMaterial);
        magicstone = new Magicstone(nextItemId(), magicstoneMaterial);
        magicIngot = new MagicIngot(nextItemId(), magicstoneMaterial);
        magicBook = new MagicBook(nextItemId());
        magicLeather = new MagicLeather(nextItemId());
        magicPaper = new MagicPaper(nextItemId());

    }

    private static int nextItemId() {
        return id++;
    }

}
