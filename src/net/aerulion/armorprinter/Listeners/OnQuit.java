package net.aerulion.armorprinter.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.aerulion.armorprinter.Main;

public class OnQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (Main.PlayerColors.containsKey(e.getPlayer().getName())) {
			Main.PlayerColors.remove(e.getPlayer().getName());
		}
	}

}
