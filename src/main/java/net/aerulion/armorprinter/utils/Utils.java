package net.aerulion.armorprinter.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import net.aerulion.armorprinter.Main;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Utils {

  public static final Map<Material, DyeColor> dyeMaterialColors = createDyeMap();

  public static @NotNull Map<Material, DyeColor> createDyeMap() {
    @NotNull Map<Material, DyeColor> dyeMap = new EnumMap<>(Material.class);
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

  public static @NotNull Inventory dyeInv(@NotNull Player p) {

    @NotNull ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
    @NotNull LeatherArmorMeta mArmor = (LeatherArmorMeta) helmet.getItemMeta();
    mArmor.setColor(Main.colorCache.get(p.getName()));
    mArmor.setDisplayName("§e§lClick to add this item to your inventory");
    helmet.setItemMeta(mArmor);
    @NotNull ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    chestplate.setItemMeta(mArmor);
    @NotNull ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS);
    leggins.setItemMeta(mArmor);
    @NotNull ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
    boots.setItemMeta(mArmor);

    @NotNull Inventory inventory = Bukkit.createInventory(null, 54,
        " §8§m+---§2§l§o  Armor§8-§2§l§oPrinter  §8§m---+");

    @NotNull ItemStack dye1 = new ItemStack(Material.BLACK_DYE);
    inventory.setItem(13, dye1);
    @NotNull ItemStack dye2 = new ItemStack(Material.GRAY_DYE);
    inventory.setItem(14, dye2);
    @NotNull ItemStack dye3 = new ItemStack(Material.LIGHT_GRAY_DYE);
    inventory.setItem(15, dye3);
    @NotNull ItemStack dye4 = new ItemStack(Material.WHITE_DYE);
    inventory.setItem(16, dye4);
    @NotNull ItemStack dye5 = new ItemStack(Material.RED_DYE);
    inventory.setItem(22, dye5);
    @NotNull ItemStack dye6 = new ItemStack(Material.ORANGE_DYE);
    inventory.setItem(23, dye6);
    @NotNull ItemStack dye7 = new ItemStack(Material.YELLOW_DYE);
    inventory.setItem(24, dye7);
    @NotNull ItemStack dye8 = new ItemStack(Material.LIME_DYE);
    inventory.setItem(25, dye8);
    @NotNull ItemStack dye9 = new ItemStack(Material.GREEN_DYE);
    inventory.setItem(31, dye9);
    @NotNull ItemStack dye10 = new ItemStack(Material.CYAN_DYE);
    inventory.setItem(32, dye10);
    @NotNull ItemStack dye11 = new ItemStack(Material.BLUE_DYE);
    inventory.setItem(33, dye11);
    @NotNull ItemStack dye12 = new ItemStack(Material.LIGHT_BLUE_DYE);
    inventory.setItem(34, dye12);
    @NotNull ItemStack dye13 = new ItemStack(Material.PINK_DYE);
    inventory.setItem(40, dye13);
    @NotNull ItemStack dye14 = new ItemStack(Material.MAGENTA_DYE);
    inventory.setItem(41, dye14);
    @NotNull ItemStack dye15 = new ItemStack(Material.PURPLE_DYE);
    inventory.setItem(42, dye15);
    @NotNull ItemStack dye16 = new ItemStack(Material.BROWN_DYE);
    inventory.setItem(43, dye16);

    inventory.setItem(10, helmet);
    inventory.setItem(19, chestplate);
    inventory.setItem(28, leggins);
    inventory.setItem(37, boots);

    @NotNull ItemStack reset = new ItemStack(Material.BARRIER);
    ItemMeta mReset = reset.getItemMeta();
    mReset.setDisplayName("§e§lReset");
    reset.setItemMeta(mReset);
    inventory.setItem(49, reset);

    return inventory;
  }

  public static void UpdateColorInventory(@NotNull Player player) {
    @NotNull Inventory inventory = player.getOpenInventory().getTopInventory();
    @Nullable ItemStack helmet = inventory.getItem(10);
    @NotNull LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) helmet.getItemMeta();
    leatherArmorMeta.setColor(Main.colorCache.get(player.getName()));
    helmet.setItemMeta(leatherArmorMeta);
    @Nullable ItemStack chestplate = inventory.getItem(19);
    chestplate.setItemMeta(leatherArmorMeta);
    @Nullable ItemStack leggings = inventory.getItem(28);
    leggings.setItemMeta(leatherArmorMeta);
    @Nullable ItemStack boots = inventory.getItem(37);
    boots.setItemMeta(leatherArmorMeta);
    player.updateInventory();
  }

  public static @NotNull ItemStack dyeRandom(@NotNull ItemStack is, int x) {
    DyeColor @NotNull [] dc = {DyeColor.BLACK, DyeColor.BLUE, DyeColor.BROWN, DyeColor.CYAN,
        DyeColor.GRAY, DyeColor.GREEN, DyeColor.LIGHT_BLUE, DyeColor.LIME, DyeColor.MAGENTA,
        DyeColor.ORANGE, DyeColor.PINK, DyeColor.PURPLE, DyeColor.RED, DyeColor.LIGHT_GRAY,
        DyeColor.WHITE, DyeColor.YELLOW};
    @NotNull LeatherArmorMeta mis = (LeatherArmorMeta) is.getItemMeta();
    ThreadLocalRandom random = ThreadLocalRandom.current();
    @Nullable Color col = null;
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

  public static @NotNull List<ItemStack> getRandomArmor(int x) {
    @NotNull List<ItemStack> armor = new ArrayList<>();
    @NotNull ItemStack helmet = dyeRandom(new ItemStack(Material.LEATHER_HELMET), x);
    @NotNull ItemStack chestplate = dyeRandom(new ItemStack(Material.LEATHER_CHESTPLATE), x);
    @NotNull ItemStack leggings = dyeRandom(new ItemStack(Material.LEATHER_LEGGINGS), x);
    @NotNull ItemStack boots = dyeRandom(new ItemStack(Material.LEATHER_BOOTS), x);
    armor.add(helmet);
    armor.add(chestplate);
    armor.add(leggings);
    armor.add(boots);
    return armor;
  }

  public static @NotNull ArmorStand summonArmorStand(@NotNull Player player) {
    @NotNull ArmorStand armorStand = (ArmorStand) player.getLocation().getWorld()
        .spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
    armorStand.setArms(true);
    armorStand.setBasePlate(false);
    armorStand.setGravity(false);
    armorStand.setDisabledSlots(EquipmentSlot.values());
    return armorStand;
  }

  public static void equipPrintedArmorStand(@NotNull ArmorStand armorStand,
      @NotNull String playerName, int quality) throws IOException {
    @NotNull ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
    @NotNull SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
    skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
    skull.setItemMeta(skullMeta);
    ItemStack @NotNull [] items = ColorUtils.getItems(playerName, quality);
    armorStand.getEquipment().setHelmet(skull);
    armorStand.getEquipment().setChestplate(items[0]);
    armorStand.getEquipment().setLeggings(items[1]);
    armorStand.getEquipment().setBoots(items[2]);
    armorStand.setCustomName(playerName);
  }
}