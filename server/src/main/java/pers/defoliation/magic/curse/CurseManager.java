package pers.defoliation.magic.curse;

import common.defoliation.mod.mite.inventory.ItemStackWrapper;
import common.defoliation.mod.mite.nbt.MITENBTTagCompound;
import common.defoliation.nbt.NBTTagCompound;
import net.minecraft.EnchantmentManager;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import pers.defoliation.magic.Main;

import java.util.*;
import java.util.stream.Collectors;

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

    public Collection<Curse> getCurses() {
        return curseMap.values();
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

    public <T extends Curse> Optional<CurseLevel<T>> getCurseFromItemStack(ItemStack itemStack, T curse) {
        ItemStackWrapper itemStackWrapper = ItemStackWrapper.of(itemStack);
        NBTTagCompound nbtTagCompound = itemStackWrapper.getNBT();
        if (!nbtTagCompound.hasKey(CURSE))
            return Optional.empty();
        NBTTagCompound curseCompound = nbtTagCompound.getCompound(CURSE);
        if (!curseCompound.hasKey(curse.getName()))
            return Optional.empty();
        return Optional.of(new CurseLevel(curse, curseCompound.getInt(curse.getName())));
    }

    public boolean hasCurse(ItemStack itemStack, Curse curse) {
        ItemStackWrapper itemStackWrapper = ItemStackWrapper.of(itemStack);
        NBTTagCompound nbtTagCompound = itemStackWrapper.getNBT();
        if (!nbtTagCompound.hasKey(CURSE))
            return false;
        NBTTagCompound curseCompound = nbtTagCompound.getCompound(CURSE);
        return curseCompound.hasKey(curse.getName());
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

    public void randomApplyCurse(ItemStack itemStack, int enchantCost, int enchantTableAntiCurseLevel) {
        int curseNum = getCurseNum(enchantCost, enchantTableAntiCurseLevel);
        if (curseNum <= 0)
            return;
        List<Curse> curses = getApplicableCurse(itemStack.b());
        if (curses.isEmpty())
            return;

        int enchantLevel = Math.max(EnchantmentManager.getEnchantmentsMap(itemStack).values().stream().mapToInt(o -> (int) o).sum(), 1);

        double d = enchantLevel / (enchantTableAntiCurseLevel / 1000d);

        int curseLevels = (int) Math.max(d, 1d);

        for (int i = 0; i < curseNum; i++) {
            if (curses.isEmpty() || curseLevels <= 0)
                return;
            TreeMap<Double, Curse> weightMap = new TreeMap<>();

            for (Curse curs : curses) {
                double lastWeight = weightMap.isEmpty() ? 0 : weightMap.lastKey().doubleValue();
                weightMap.put(curs.getProbability(itemStack, enchantCost) + lastWeight, curs);
            }

            double random = weightMap.lastKey() * Main.random.nextDouble();

            SortedMap<Double, Curse> map = weightMap.tailMap(random, false);
            Curse curse = weightMap.get(map.firstKey());
            curses.remove(curse);

            int level = Main.random.nextInt(Math.min(curse.getMaxLevel(),curseLevels)) + 1;
            curseLevels-=level;
            applyCurse(itemStack, curse, level);
        }
    }

    private int getCurseNum(int enchantLevel, int antiLevel) {
        int num = 0;
        while (enchantLevel != 0) {
            if (enchantLevel >= antiLevel) {
                enchantLevel -= antiLevel;
                if (antiLevel >= Main.random.nextInt(antiLevel + ((antiLevel + num * (antiLevel)) / 2)))
                    num++;
            } else {
                if (enchantLevel >= Main.random.nextInt(antiLevel + antiLevel / 2))
                    num++;
                enchantLevel = 0;
            }
        }
        return num;
    }

    private List<Curse> getApplicableCurse(Item item) {
        List<Curse> curses = new ArrayList<>();
        for (Curse curs : getCurses()) {
            if (curs.canCurse(item))
                curses.add(curs);
        }
        return curses;
    }


}
