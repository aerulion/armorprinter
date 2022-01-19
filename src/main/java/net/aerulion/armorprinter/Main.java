package net.aerulion.armorprinter;

import java.util.HashMap;
import java.util.Map;
import net.aerulion.armorprinter.cmd.CMD_armorprinter;
import net.aerulion.armorprinter.cmd.CMD_printperson;
import net.aerulion.armorprinter.cmd.CMD_printrandomarmor;
import net.aerulion.armorprinter.listener.OnInventoryClick;
import net.aerulion.armorprinter.listener.PlayerQuitListener;
import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin {

  public static final @NotNull Map<String, Color> colorCache = new HashMap<>();
  public static Main plugin;

  @Override
  public void onEnable() {
    plugin = this;
    getCommand("armorprinter").setExecutor(new CMD_armorprinter());
    getCommand("printrandomarmor").setExecutor(new CMD_printrandomarmor());
    getCommand("printperson").setExecutor(new CMD_printperson());
    getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);
    getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
  }
}