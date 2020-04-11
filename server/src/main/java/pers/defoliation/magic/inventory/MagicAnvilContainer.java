package pers.defoliation.magic.inventory;

import common.defoliation.Location;
import common.defoliation.entity.PlayerEntity;
import common.defoliation.inventory.Inventory;
import common.defoliation.inventory.InventoryView;
import common.defoliation.mod.liar.EntityPlayerLiar;
import common.defoliation.mod.mite.inventory.MITEInventory;
import common.defoliation.mod.mite.inventory.SimpleInventory;
import net.minecraft.*;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import pers.defoliation.magic.block.Blocks;
import pers.defoliation.magic.block.MagicAnvil;
import pers.defoliation.magic.block.tile.MagicAnvilTileEntity;
import pers.defoliation.magic.curse.CurseManager;
import pers.defoliation.magic.item.Items;

import java.util.*;
import java.util.function.Consumer;

public class MagicAnvilContainer extends Container implements InventoryView {

    private static Random random = new Random(System.currentTimeMillis());
    private Location location;
    private SimpleInventory simpleInventory = new SimpleInventory(3);

    public int cost;

    private ItemStack computeItem;

    private Slot itemSlot;
    private Slot magicSlot;

    public MagicAnvilContainer(EntityHuman player, Location location) {
        super(player);
        this.location = location;

        itemSlot = new Slot(simpleInventory, 0, 27, 47) {
            public boolean a(ItemStack par1ItemStack) {
                NBTTagList stored_enchantments = par1ItemStack.r();
                if (stored_enchantments != null
                        && !stored_enchantments.a.isEmpty()
                        && par1ItemStack.b() != Item.bY)
                    return true;
                return false;
            }
        };
        magicSlot = new Slot(simpleInventory, 1, 76, 47) {
            public boolean a(ItemStack par1ItemStack) {
                if (par1ItemStack.d == Items.magicstone.cv)
                    return true;
                return false;
            }
        };

        a(itemSlot);

        a(magicSlot);

        a(new Slot(simpleInventory, 2, 134, 47) {

            public boolean a(ItemStack par1ItemStack) {
                return false;
            }

            //TODO 是否能拿出来
            public boolean a(EntityHuman par1EntityPlayer) {
                return cost > 0 && cost <= par1EntityPlayer.bJ;
            }

            public void a(EntityHuman par1EntityPlayer, ItemStack par2ItemStack) {
                //TODO 当点这个槽时
                if (!world.I) {
                    ((MagicAnvilTileEntity) world.r(location.getBlockX(), location.getBlockY(), location.getBlockZ()))
                            .addDamage(world, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 1);
                    par1EntityPlayer.addExperience(-cost, false, false);
                }
                simpleInventory.a(0, null);
                ItemStack itemStack = simpleInventory.a(1);
                if (itemStack != null && itemStack.b > 1) {
                    itemStack.b -= 1;
                    simpleInventory.a(1, itemStack);
                } else
                    simpleInventory.a(1, null);
                if (!world.I) {
                    world.e(1021, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0);
                }
            }
        });

        for (int var7 = 0; var7 < 3; var7++) {
            for (int var8 = 0; var8 < 9; var8++) {
                a(new Slot(player.bn, var8 + var7 * 9 + 9, 8 + var8 * 18, 84 + var7 * 18));
            }
        }
        for (int var7 = 0; var7 < 9; var7++) {
            a(new Slot(player.bn, var7, 8 + var7 * 18, 142));
        }
    }

    @Override
    public boolean a(EntityHuman entityHuman) {
        //时时检测这个container是否能打开
        if (((world.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ()) instanceof MagicAnvil))
                && ((world.r(location.getBlockX(), location.getBlockY(), location.getBlockZ()) instanceof MagicAnvilTileEntity))
        ) {
            return entityHuman.e(location.getBlockX() + 0.5D, location.getBlockY() + 0.5D, location.getBlockZ() + 0.5D) <= 64.0D;
        }
        return true;
    }

    //shift
    public ItemStack b(EntityHuman par1EntityPlayer, int par2) {
        Slot slot = (Slot) this.c.get(par2);
        //slot.e() -> slot.hasStack();
        if (slot == null || !slot.e())
            return null;
        ItemStack newStack = slot.d();
        ItemStack oldStack = newStack.m();

        boolean flag = false;

        if (par2 >= 0 && par2 <= 2) {
            flag = a(newStack, 3, 39, true);
        } else {
            flag = (!itemSlot.e() && a(newStack, 0, 1, false)) || a(newStack,1,2,false);
        }

        if (!flag)
            return null;

        if (newStack.b == 0) {
            slot.c(null);
        } else {
            //onSlotChange
            slot.f();
        }

        //onPickupFromSlot
        slot.a(par1EntityPlayer, newStack);

        return oldStack;
    }

    //当被关闭时
    public void b(EntityHuman par1EntityPlayer) {
        super.b(par1EntityPlayer);
        if (!world.I) {
            for (int var2 = 0; var2 < 2; var2++) {
                ItemStack var3 = simpleInventory.a_(var2);
                if (var3 != null) {
                    par1EntityPlayer.b(var3);
                }
            }
            simpleInventory.a(2, null);
        }
    }


    @Override
    public Inventory getTopInventory() {
        return new MITEInventory(simpleInventory);
    }

    @Override
    public Inventory getBottomInventory() {
        return ((EntityPlayerLiar) player).getPlayer().getPlayerInventory();
    }

    @Override
    public PlayerEntity getPlayer() {
        return ((EntityPlayerLiar) player).getPlayer();
    }

    private void updateResult() {

        ItemStack firstItems = simpleInventory.a(0);
        if (firstItems == null) {
            simpleInventory.a(2, null);
            cost = 0;
            return;
        }

        if (computeItem != firstItems) {
            Map map = EnchantmentManager.getEnchantmentsMap(firstItems);

            if (map.isEmpty()) {
                return;
            }

            double cost = 1000;

            double numModifier = (1d / -((double) map.size())) + 2d;

            for (Object o : map.keySet()) {
                int id = (int) o;
                Enchantment enchantment = Enchantment.b[id];
                int level = (int) map.get(id);
                int modifierLevel = level * 10;
                cost += enchantment.difficulty * modifierLevel * modifierLevel * numModifier;
            }

            this.cost = (int) cost;
            computeItem = firstItems;
        }

        if (this.cost > player.bJ) {
            //拒绝
            simpleInventory.a(2, null);
            return;
        }

        ItemStack secondItem = simpleInventory.a(1);

        if (secondItem == null) {
            simpleInventory.a(2, null);
            return;
        }

        //用于服务端与客户端不一致
//        if (world.I) {
//            int randomId = (Integer) map.keySet().toArray()[random.nextInt(map.size())];
//            map.put(randomId, ((int) map.get(randomId)) + random.nextInt(2));
//            EnchantmentManager.a(map, cloneItems);
//            simpleInventory.a(2, cloneItems);
//        } else {
//            if (result == null) {
//                int randomId = (Integer) map.keySet().toArray()[random.nextInt(map.size())];
//                map.put(randomId, ((int) map.get(randomId)) + 1);
//                EnchantmentManager.a(map, cloneItems);
//                result = cloneItems;
//            }
//        }
        ItemStack cloneItems = firstItems.m();
        if (updateEnchantment(cloneItems, 1))
            simpleInventory.a(2, cloneItems);
    }

    private boolean updateEnchantment(ItemStack itemStack, int addLevel) {
        List<Consumer<Integer>> updaterList = new ArrayList<>();
        Map<Integer, Integer> map = EnchantmentManager.getEnchantmentsMap(itemStack);
        map.entrySet()
                .stream()
                .filter(entry -> Enchantment.get(entry.getKey()).hasLevels() && entry.getValue() < 10)
                .forEach(entry -> updaterList.add(level -> {
                    map.put(entry.getKey(), entry.getValue() + level);
                    EnchantmentManager.a(map, itemStack);
                }));
        CurseManager.INSTANCE.getCursesFromItemStack(itemStack).stream()
                .filter(curseLevel -> curseLevel.level < curseLevel.curse.getMaxLevel())
                .forEach(curseLevel ->
                        updaterList.add(level ->
                                CurseManager.INSTANCE.applyCurse(itemStack, curseLevel.curse, curseLevel.level + level))
                );
        if (updaterList.isEmpty())
            return false;
        updaterList.get(random.nextInt(updaterList.size())).accept(addLevel);
        return true;
    }

    public void onUpdate() {
        if ((player instanceof EntityOtherPlayerMP)) {
            return;
        }

        updateResult();

        super.onUpdate();
    }


}
