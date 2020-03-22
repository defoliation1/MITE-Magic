package pers.defoliation.magic.curse;

import net.minecraft.Item;
import pers.defoliation.magic.Main;

public class RustCurse extends MagicCurse {

    public RustCurse() {
        super("rust");
    }

    @Override
    public boolean canCurse(Item item) {
        return true;
    }

    public static int modifier(int damage, int level){
        if (level > Main.random.nextInt(5))
            damage += 1 + Main.random.nextInt(2 + level) * level;
        return damage;
    }

}
