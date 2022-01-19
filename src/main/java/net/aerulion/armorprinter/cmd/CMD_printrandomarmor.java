package net.aerulion.armorprinter.cmd;

import java.util.List;
import net.aerulion.armorprinter.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CMD_printrandomarmor implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
      final @NotNull String label, final String @NotNull [] args) {

    if (!(sender instanceof @NotNull Player player)) {
      sender.sendMessage(
          "§7[§e§lArmorPrinter§7] §cDieser Befehl kann nur als Spieler ausgeführt werden.");
      return true;
    }

    if (!player.hasPermission("armorprinter.printrandomarmor")) {
      sender.sendMessage("§7[§e§lArmorPrinter§7] §cDu hast keine Rechte diesen Befehl zu nutzen.");
      return true;
    }
    final int x;

    if (args.length != 1) {
      sender.sendMessage(
          "§7[§e§lArmorPrinter§7] §cSyntaxfehler. Nutze /printrandomarmor <AnzahlDerFärbungen>");
      return true;
    }
    try {
      x = Integer.parseInt(args[0]);
    } catch (final NumberFormatException e) {
      sender.sendMessage("§7[§e§lArmorPrinter§7] §cKeine gültige Zahl.");
      return true;
    }

    if (x < 1 || x > 10) {
      sender.sendMessage("§7[§e§lArmorPrinter§7] §cDie Zahl mussen zwischen 1 und 10 liegen.");
      return true;
    }

    for (final ItemStack is : Utils.getRandomArmor(x)) {
      player.getInventory().addItem(is);
    }
    sender.sendMessage(
        "§7[§a§lArmor§7§l-§a§lPrinter§7] §aEine zufällig gefärbte Rüstung wurde in dein Inventar gelegt.");

    return true;
  }

  @Override
  public List<String> onTabComplete(final @NotNull CommandSender sender,
      final @NotNull Command command, final @NotNull String alias, final String @NotNull [] args) {
    if (args.length == 1) {
      return List.of("<AnzahlAnFärbungen>");
    }
    return List.of();
  }
}