package net.aerulion.armorprinter.cmd;

import java.util.Collections;
import java.util.List;
import net.aerulion.armorprinter.Main;
import net.aerulion.armorprinter.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMD_armorprinter implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label,
      String[] args) {

    if (!(commandSender instanceof @NotNull Player player)) {
      commandSender.sendMessage(
          "§7[§e§lArmorPrinter§7] §cDieser Befehl kann nur als Spieler ausgeführt werden.");
      return true;
    }

    if (!player.hasPermission("armorprinter.armorprinter")) {
      commandSender.sendMessage(
          "§7[§e§lArmorPrinter§7] §cDu hast keine Rechte diesen Befehl zu nutzen.");
      return true;
    }

    if (!Main.colorCache.containsKey(player.getName())) {
      Main.colorCache.put(player.getName(), null);
    }
    player.openInventory(Utils.dyeInv(player));

    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String label,
      String[] args) {
    return Collections.emptyList();
  }
}