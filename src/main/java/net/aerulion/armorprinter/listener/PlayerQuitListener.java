package net.aerulion.armorprinter.listener;

import net.aerulion.armorprinter.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitListener implements Listener {

  @EventHandler
  public void onPlayerQuit(@NotNull PlayerQuitEvent e) {
    Main.colorCache.remove(e.getPlayer().getName());
  }
}