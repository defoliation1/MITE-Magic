package pers.defoliation.magic.curse;

import common.defoliation.mod.mite.inventory.ItemStackWrapper;
import common.defoliation.mod.mite.nbt.MITENBTTagCompound;
import common.defoliation.nbt.NBTTagCompound;
import net.minecraft.ItemStack;

import java.util.*;

public class CurseManager {

    public static final CurseManager INSTANCE = new CurseManager();
    private static final String CURSE = "curse";

    private HashMap<String, Curse> curseMap = new HashMap<>();

    public void registerCurse(Curse curse) {
        if (curseMap.containsKey(curse.getName()))
            throw new RuntimeException("curse [" + curse.getName() + "] already register");
        curseMap.put(curse.getName(), curse);
    }

    public Optional<Curse> getCurse(String name) {
        return Optional.ofNullable(curseMap.get(name));
    }

    public List<CurseLevel> getCursesFromItemStack(ItemStack itemStack) {
        ItemStackWrapper itemStackWrapper = ItemStackWrapper.of(itemStack);
        NBTTagCompound nbtTagCompound = itemStackWrapper.getNBT();
        if (!nbtTagCompound.hasKey(CURSE))
            return Collections.emptyList();
        NBTTagCompound curseCompound = nbtTagCompound.getCompound(CURSE);
        ArrayList<CurseLevel> list = new ArrayList();
        for (String key : curseCompound.keys()) {
            Optional<Curse> curse = getCurse(key);
            if (curse.isPresent())
                list.add(new CurseLevel(curse.get(), curseCompound.getInt(key)));
        }
        return list;
    }

    public Optional<CurseLevel> getCurseFromItemStack(ItemStack itemStack, Curse curse) {
        ItemStackWrapper itemStackWrapper = ItemStackWrapper.of(itemStack);
        NBTTagCompound nbtTagCompound = itemStackWrapper.getNBT();
        if (!nbtTagCompound.hasKey(CURSE))
            return Optional.empty();
        NBTTagCompound curseCompound = nbtTagCompound.getCompound(CURSE);
        if (!curseCompound.hasKey(curse.getName()))
            return Optional.empty();
        return Optional.of(new CurseLevel(curse, curseCompound.getInt(curse.getName())));
    }

    public void applyCurse(ItemStack itemStack, Curse curse, int level) {
        applyCurse(itemStack, new CurseLevel(curse, level));
    }

    public void applyCurse(ItemStack itemStack, CurseLevel curseLevel) {
        ItemStackWrapper itemStackWrapper = ItemStackWrapper.of(itemStack);
        NBTTagCompound nbtTagCompound = itemStackWrapper.getNBT();
        NBTTagCompound curseCompound;
        if (nbtTagCompound.hasKey(CURSE)) {
            curseCompound = nbtTagCompound.getCompound(CURSE);
        } else {
            curseCompound = new MITENBTTagCompound(new net.minecraft.NBTTagCompound(CURSE));
            nbtTagCompound.set(CURSE, curseCompound);
        }
        curseCompound.setInt(curseLevel.curse.getName(), curseLevel.level);
    }

}
