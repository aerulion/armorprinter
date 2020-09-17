package net.aerulion.armorprinter.listener;

import net.aerulion.armorprinter.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (Main.colorCache.containsKey(e.getPlayer().getName())) {
            Main.colorCache.remove(e.getPlayer().getName());
        }
    }
}