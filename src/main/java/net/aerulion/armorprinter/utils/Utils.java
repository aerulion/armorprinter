package net.aerulion.armorprinter.utils;

import net.aerulion.armorprinter.Main;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class Utils {

    public final static HashMap<Material, DyeColor> dyeMaterialColors = createDyeMap();

    public static HashMap<Material, DyeColor> createDyeMap() {
        HashMap<Material, DyeColor> dyeMap = new HashMap<>();
        dyeMap.put(Material.BLACK_DYE, DyeColor.BLACK);
        dyeMap.put(Material.GRAY_DYE, DyeColor.GRAY);
        dyeMap.put(Material.LIGHT_GRAY_DYE, DyeColor.LIGHT_GRAY);
        dyeMap.put(Material.WHITE_DYE, DyeColor.WHITE);
        dyeMap.put(Material.RED_DYE, DyeColor.RED);
        dyeMap.put(Material.ORANGE_DYE, DyeColor.ORANGE);
        dyeMap.put(Material.YELLOW_DYE, DyeColor.YELLOW);
        dyeMap.put(Material.LIME_DYE, DyeColor.LIME);
        dyeMap.put(Material.GREEN_DYE, DyeColor.GREEN);
        dyeMap.put(Material.CYAN_DYE, DyeColor.CYAN);
        dyeMap.put(Material.BLUE_DYE, DyeColor.BLUE);
        dyeMap.put(Material.LIGHT_BLUE_DYE, DyeColor.LIGHT_BLUE);
        dyeMap.put(Material.PINK_DYE, DyeColor.PINK);
        dyeMap.put(Material.MAGENTA_DYE, DyeColor.MAGENTA);
        dyeMap.put(Material.PURPLE_DYE, DyeColor.PURPLE);
        dyeMap.put(Material.BROWN_DYE, DyeColor.BROWN);
        return dyeMap;
    }

    public static Inventory dyeInv(Player p) {

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta mArmor = (LeatherArmorMeta) helmet.getItemMeta();
        mArmor.setColor(Main.colorCache.get(p.getName()));
        mArmor.setDisplayName("§e§lClick to add this item to your inventory");
        helmet.setItemMeta(mArmor);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.setItemMeta(mArmor);
        ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS);
        leggins.setItemMeta(mArmor);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.setItemMeta(mArmor);

        Inventory PrinterInventory = Bukkit.createInventory(null, 54, " §8§m+---§2§l§o  Armor§8-§2§l§oPrinter  §8§m---+");

        ItemStack dye1 = new ItemStack(Material.BLACK_DYE);
        PrinterInventory.setItem(13, dye1);
        ItemStack dye2 = new ItemStack(Material.GRAY_DYE);
        PrinterInventory.setItem(14, dye2);
        ItemStack dye3 = new ItemStack(Material.LIGHT_GRAY_DYE);
        PrinterInventory.setItem(15, dye3);
        ItemStack dye4 = new ItemStack(Material.WHITE_DYE);
        PrinterInventory.setItem(16, dye4);
        ItemStack dye5 = new ItemStack(Material.RED_DYE);
        PrinterInventory.setItem(22, dye5);
        ItemStack dye6 = new ItemStack(Material.ORANGE_DYE);
        PrinterInventory.setItem(23, dye6);
        ItemStack dye7 = new ItemStack(Material.YELLOW_DYE);
        PrinterInventory.setItem(24, dye7);
        ItemStack dye8 = new ItemStack(Material.LIME_DYE);
        PrinterInventory.setItem(25, dye8);
        ItemStack dye9 = new ItemStack(Material.GREEN_DYE);
        PrinterInventory.setItem(31, dye9);
        ItemStack dye10 = new ItemStack(Material.CYAN_DYE);
        PrinterInventory.setItem(32, dye10);
        ItemStack dye11 = new ItemStack(Material.BLUE_DYE);
        PrinterInventory.setItem(33, dye11);
        ItemStack dye12 = new ItemStack(Material.LIGHT_BLUE_DYE);
        PrinterInventory.setItem(34, dye12);
        ItemStack dye13 = new ItemStack(Material.PINK_DYE);
        PrinterInventory.setItem(40, dye13);
        ItemStack dye14 = new ItemStack(Material.MAGENTA_DYE);
        PrinterInventory.setItem(41, dye14);
        ItemStack dye15 = new ItemStack(Material.PURPLE_DYE);
        PrinterInventory.setItem(42, dye15);
        ItemStack dye16 = new ItemStack(Material.BROWN_DYE);
        PrinterInventory.setItem(43, dye16);

        PrinterInventory.setItem(10, helmet);
        PrinterInventory.setItem(19, chestplate);
        PrinterInventory.setItem(28, leggins);
        PrinterInventory.setItem(37, boots);

        ItemStack reset = new ItemStack(Material.BARRIER);
        ItemMeta mReset = reset.getItemMeta();
        mReset.setDisplayName("§e§lReset");
        reset.setItemMeta(mReset);
        PrinterInventory.setItem(49, reset);

        return PrinterInventory;
    }

    public static void UpdateColorInventory(Player player) {
        Inventory inventory = player.getOpenInventory().getTopInventory();
        ItemStack helmet = inventory.getItem(10);
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) helmet.getItemMeta();
        leatherArmorMeta.setColor(Main.colorCache.get(player.getName()));
        helmet.setItemMeta(leatherArmorMeta);
        ItemStack chestplate = inventory.getItem(19);
        chestplate.setItemMeta(leatherArmorMeta);
        ItemStack leggings = inventory.getItem(28);
        leggings.setItemMeta(leatherArmorMeta);
        ItemStack boots = inventory.getItem(37);
        boots.setItemMeta(leatherArmorMeta);
        player.updateInventory();
    }

    public static ItemStack dyeRandom(ItemStack is, int x) {
        DyeColor[] dc = {DyeColor.BLACK, DyeColor.BLUE, DyeColor.BROWN, DyeColor.CYAN, DyeColor.GRAY, DyeColor.GREEN, DyeColor.LIGHT_BLUE, DyeColor.LIME, DyeColor.MAGENTA, DyeColor.ORANGE, DyeColor.PINK, DyeColor.PURPLE, DyeColor.RED, DyeColor.LIGHT_GRAY, DyeColor.WHITE, DyeColor.YELLOW};
        LeatherArmorMeta mis = (LeatherArmorMeta) is.getItemMeta();
        Random random = new Random();
        Color col = null;
        int r1 = random.nextInt(x) + 1;
        for (int i = 0; i < r1; i++) {
            int r2 = random.nextInt(15);
            DyeColor color = dc[r2];
            if (col == null) {
                col = color.getColor();
            } else {
                col = col.mixDyes(color);
            }
        }
        mis.setColor(col);
        is.setItemMeta(mis);
        return is;
    }

    public static List<ItemStack> getRandomArmor(int x) {
        List<ItemStack> armor = new ArrayList<>();
        ItemStack helmet = dyeRandom(new ItemStack(Material.LEATHER_HELMET), x);
        ItemStack chestplate = dyeRandom(new ItemStack(Material.LEATHER_CHESTPLATE), x);
        ItemStack leggings = dyeRandom(new ItemStack(Material.LEATHER_LEGGINGS), x);
        ItemStack boots = dyeRandom(new ItemStack(Material.LEATHER_BOOTS), x);
        armor.add(helmet);
        armor.add(chestplate);
        armor.add(leggings);
        armor.add(boots);
        return armor;
    }

    public static ArmorStand summonArmorstand(Player player) {
        ArmorStand AS = (ArmorStand) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        AS.setArms(true);
        AS.setBasePlate(false);
        AS.setGravity(false);

        final Object nmsEntity = getNmsEntity(AS);
        if (nmsEntity == null) {
            return AS;
        }
        Field f;
        try {
            f = nmsEntity.getClass().getDeclaredField("disabledSlots");

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return AS;
        }
        f.setAccessible(true);
        try {
            f.set(nmsEntity, 4144959);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return AS;
    }

    public static void EquipPrintedArmorStand(ArmorStand AS, String PlayerName, int quality) throws IOException {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(PlayerName));
        skull.setItemMeta(skullMeta);
        ItemStack[] Items = ColorUtils.getItems(PlayerName, quality);
        AS.getEquipment().setHelmet(skull);
        AS.getEquipment().setChestplate(Items[0]);
        AS.getEquipment().setLeggings(Items[1]);
        AS.getEquipment().setBoots(Items[2]);
        AS.setCustomName(PlayerName);
    }

    private static Object getNmsEntity(final Entity entity) {
        try {
            return entity.getClass().getMethod("getHandle", new Class[0]).invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}