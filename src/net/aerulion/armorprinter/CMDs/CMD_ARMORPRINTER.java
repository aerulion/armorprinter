package net.aerulion.armorprinter.CMDs;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.aerulion.armorprinter.Main;
import net.aerulion.armorprinter.Utils.Util;

public class CMD_ARMORPRINTER implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§7[§e§lArmorPrinter§7] §cDieser Befehl kann nur als Spieler ausgeführt werden.");
			return true;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("armorprinter.armorprinter")) {
			sender.sendMessage("§7[§e§lArmorPrinter§7] §cDu hast keine Rechte diesen Befehl zu nutzen.");
			return true;
		}

		if (!Main.PlayerColors.containsKey(p.getName())) {
			Main.PlayerColors.put(p.getName(), null);
		}
		p.openInventory(Util.dyeInv(p));

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}

}
