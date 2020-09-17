package net.aerulion.armorprinter.cmd;

import net.aerulion.armorprinter.Main;
import net.aerulion.armorprinter.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CMD_armorprinter implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§7[§e§lArmorPrinter§7] §cDieser Befehl kann nur als Spieler ausgeführt werden.");
            return true;
        }

        Player p = (Player) commandSender;

        if (!p.hasPermission("armorprinter.armorprinter")) {
            commandSender.sendMessage("§7[§e§lArmorPrinter§7] §cDu hast keine Rechte diesen Befehl zu nutzen.");
            return true;
        }

        if (!Main.colorCache.containsKey(p.getName())) {
            Main.colorCache.put(p.getName(), null);
        }
        p.openInventory(Utils.dyeInv(p));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}