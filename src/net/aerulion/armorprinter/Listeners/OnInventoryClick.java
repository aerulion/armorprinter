package net.aerulion.armorprinter.Listeners;

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
import net.aerulion.armorprinter.Main;
import net.aerulion.armorprinter.Utils.Util;

public class OnInventoryClick implements Listener {

	@EventHandler
	public void OnInvClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			if (e.getView().getTitle() != null && e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				if (e.getView().getTitle().equals(" §8§m+---§2§l§o  Armor§8-§2§l§oPrinter  §8§m---+")) {
					if (Util.dyeMaterialColors.keySet().contains(e.getCurrentItem().getType())) {
						e.setCancelled(true);
						DyeColor color = Util.dyeMaterialColors.get(e.getCurrentItem().getType());
						Color col = Main.PlayerColors.get(e.getWhoClicked().getName());
						if (col == null) {
							col = color.getColor();
						} else {
							col = col.mixDyes(color);
						}
						Main.PlayerColors.put(e.getWhoClicked().getName(), col);
						Util.UpdateColorInventory((Player) e.getWhoClicked());
						((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_BOAT_PADDLE_WATER, 1.0F, 1.5F);

						return;
					}

					if (e.getCurrentItem().getType().equals(Material.LEATHER_HELMET) || e.getCurrentItem().getType().equals(Material.LEATHER_CHESTPLATE) || e.getCurrentItem().getType().equals(Material.LEATHER_LEGGINGS) || e.getCurrentItem().getType().equals(Material.LEATHER_BOOTS)) {
						e.setCancelled(true);
						ItemStack is = new ItemStack(e.getCurrentItem());
						ItemMeta mis = is.getItemMeta();
						mis.setDisplayName(null);
						is.setItemMeta(mis);
						e.getWhoClicked().getInventory().addItem(is);
						((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
						return;
					}

					if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
						e.setCancelled(true);
						Main.PlayerColors.put(e.getWhoClicked().getName(), null);
						Util.UpdateColorInventory((Player) e.getWhoClicked());
						((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
						return;
					}
				}
			}

		}
	}

}
