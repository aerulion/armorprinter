package net.aerulion.armorprinter.listener;

import net.aerulion.armorprinter.Main;
import net.aerulion.armorprinter.utils.Utils;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OnInventoryClick implements Listener {

  @EventHandler
  public void OnInvClick(final @NotNull InventoryClickEvent event) {
    if (event.getWhoClicked() instanceof Player player && event.getCurrentItem() != null
        && event.getCurrentItem().getType() != Material.AIR && event.getView().getTitle()
        .equals(" §8§m+---§2§l§o  Armor§8-§2§l§oPrinter  §8§m---+")) {
      if (Utils.dyeMaterialColors.containsKey(event.getCurrentItem().getType())) {
        event.setCancelled(true);
        final DyeColor dyeColor = Utils.dyeMaterialColors.get(event.getCurrentItem().getType());
        Color color = Main.colorCache.get(player.getName());
        if (color == null) {
          color = dyeColor.getColor();
        } else {
          color = color.mixDyes(dyeColor);
        }
        Main.colorCache.put(player.getName(), color);
        Utils.UpdateColorInventory(player);
        player.playSound(player.getLocation(), Sound.ENTITY_BOAT_PADDLE_WATER, 1.0F, 1.5F);

        return;
      }

      if (event.getCurrentItem().getType() == Material.LEATHER_HELMET
          || event.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE
          || event.getCurrentItem().getType() == Material.LEATHER_LEGGINGS
          || event.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
        event.setCancelled(true);
        final @Nullable ItemStack is = new ItemStack(event.getCurrentItem());
        final ItemMeta mis = is.getItemMeta();
        mis.setDisplayName(null);
        is.setItemMeta(mis);
        player.getInventory().addItem(is);
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
        return;
      }

      if (event.getCurrentItem().getType() == Material.BARRIER) {
        event.setCancelled(true);
        Main.colorCache.put(player.getName(), null);
        Utils.UpdateColorInventory(player);
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
      }
    }
  }
}