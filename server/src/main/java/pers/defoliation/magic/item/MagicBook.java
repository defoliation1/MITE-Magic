package pers.defoliation.magic.item;

import net.minecraft.CreativeModeTab;
import net.minecraft.ItemBook;

public class MagicBook extends ItemBook {

    public MagicBook(int id) {
        super(id, "");
        d("magic:magic_book");
        b("magic_book");
        a(CreativeModeTab.f);
    }

}
