package net.aerulion.armorprinter;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;

import net.aerulion.armorprinter.CMDs.CMD_ARMORPRINTER;
import net.aerulion.armorprinter.CMDs.CMD_PRINTPERSON;
import net.aerulion.armorprinter.CMDs.CMD_PRINTRANDOMARMOR;
import net.aerulion.armorprinter.Listeners.OnInventoryClick;
import net.aerulion.armorprinter.Listeners.OnQuit;

public class Main extends JavaPlugin {

	public static Map<String, Color> PlayerColors = new HashMap<>();
	public static Main plugin;

	@Override
	public void onEnable() {
		plugin = this;
		getCommand("armorprinter").setExecutor(new CMD_ARMORPRINTER());
		getCommand("printrandomarmor").setExecutor(new CMD_PRINTRANDOMARMOR());
		getCommand("printperson").setExecutor(new CMD_PRINTPERSON());
		getCommand("printperson").setTabCompleter(new CMD_PRINTPERSON());
		getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);
		getServer().getPluginManager().registerEvents(new OnQuit(), this);
	}

}
