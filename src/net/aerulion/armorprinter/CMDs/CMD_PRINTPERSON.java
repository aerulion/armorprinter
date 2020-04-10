package net.aerulion.armorprinter.CMDs;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import net.aerulion.armorprinter.Main;
import net.aerulion.armorprinter.Utils.Util;

public class CMD_PRINTPERSON implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("�7[�e�lArmorPrinter�7] �cDieser Befehl kann nur als Spieler ausgef�hrt werden.");
			return true;
		}

		Player player = (Player) sender;

		if (!player.hasPermission("armorprinter.printperson")) {
			sender.sendMessage("�7[�e�lArmorPrinter�7] �cDu hast keine Rechte diesen Befehl zu nutzen.");
			return true;
		}

		String playerName;
		int quality;

		if (args.length == 1) {
			playerName = args[0];
			quality = 1;

		} else if (args.length == 2) {
			try {
				quality = Integer.parseInt(args[1]);
				playerName = args[0];
				if (quality < 1 || quality > 10) {
					sender.sendMessage("�7[�e�lArmorPrinter�7] �cDie Zahl muss zwischen 1 und 10 liegen.");
					return true;
				}
			} catch (NumberFormatException e) {
				sender.sendMessage("�7[�e�lArmorPrinter�7] �cKeine g�ltige Zahl angegeben.");
				return true;
			}

		} else {
			player.sendMessage("�7[�e�lArmorPrinter�7] �cSyntaxfehler. Nutze /printperson <SpielerName> [Qualit�t] um einen ArmorStand zu spawnen.");
			return true;
		}

		ArmorStand AS = Util.summonArmorstand(player);
		Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, Task -> {
			try {
				Util.EquipPrintedArmorStand(AS, playerName, quality);
				player.sendMessage("�7[�e�lArmorPrinter�7] �e�o" + args[0] + " �awurde als ArmorStand gespawnt.");
			} catch (IOException e) {
				player.sendMessage("�7[�e�lArmorPrinter�7] �cFehler: Der Skin konnte nicht geladen werden.");
			}
		});

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			return null;
		} else if (args.length == 2) {
			return Arrays.asList("[Qualit�t]");
		}
		return Arrays.asList();
	}

}
