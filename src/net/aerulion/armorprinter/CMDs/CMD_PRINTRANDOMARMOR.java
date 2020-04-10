package net.aerulion.armorprinter.CMDs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.aerulion.armorprinter.Utils.Util;

public class CMD_PRINTRANDOMARMOR implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§7[§e§lArmorPrinter§7] §cDieser Befehl kann nur als Spieler ausgeführt werden.");
			return true;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("armorprinter.printrandomarmor")) {
			sender.sendMessage("§7[§e§lArmorPrinter§7] §cDu hast keine Rechte diesen Befehl zu nutzen.");
			return true;
		}
		int x;

		if (args.length != 1) {
			sender.sendMessage("§7[§e§lArmorPrinter§7] §cSyntaxfehler. Nutze /printrandomarmor <AnzahlDerFärbungen>");
			return true;
		}
		try {
			x = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			sender.sendMessage("§7[§e§lArmorPrinter§7] §cKeine gültige Zahl.");
			return true;
		}

		if (x < 1 || x > 10) {
			sender.sendMessage("§7[§e§lArmorPrinter§7] §cDie Zahl mussen zwischen 1 und 10 liegen.");
			return true;
		}

		for (ItemStack is : Util.getRandomArmor(x)) {
			p.getInventory().addItem(is);
		}
		sender.sendMessage("§7[§a§lArmor§7§l-§a§lPrinter§7] §aEine zufällig gefärbte Rüstung wurde in dein Inventar gelegt.");

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("<AnzahlAnFärbungen>");
		}
		return Arrays.asList();
	}

}
