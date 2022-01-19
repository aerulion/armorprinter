package net.aerulion.armorprinter.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColorUtils {

  private ColorUtils() {
  }

  public static BufferedImage getPlayerSkinTexture(final String playerName) throws IOException {
    final @NotNull URL url = new URL("https://minotar.net/skin/" + playerName + ".png");
    return ImageIO.read(url);
  }

  public static @NotNull BufferedImage getBodyTexture(final @NotNull BufferedImage skin) {
    final @NotNull BufferedImage finalImage;
    if (skin.getHeight() == 64) {
      finalImage = new BufferedImage(56, 12, BufferedImage.TYPE_INT_RGB);
      final BufferedImage part1 = skin.getSubimage(32, 52, 16, 12);
      final BufferedImage part1Overlay = skin.getSubimage(48, 52, 16, 12);
      final BufferedImage part2 = skin.getSubimage(16, 20, 40, 12);
      final BufferedImage part2Overlay = skin.getSubimage(16, 36, 40, 12);
      final Graphics2D graphics = finalImage.createGraphics();
      graphics.drawImage(part1, 0, 0, null);
      graphics.drawImage(part1Overlay, 0, 0, null);
      graphics.drawImage(part2, 16, 0, null);
      graphics.drawImage(part2Overlay, 16, 0, null);
      graphics.dispose();
    } else {
      finalImage = new BufferedImage(40, 12, BufferedImage.TYPE_INT_RGB);
      final BufferedImage part1 = skin.getSubimage(16, 20, 40, 12);
      final Graphics2D g = finalImage.createGraphics();
      g.drawImage(part1, 0, 0, null);
      g.dispose();
    }

    return finalImage;
  }

  public static @NotNull BufferedImage getLegsTexture(final @NotNull BufferedImage skin) {
    final @NotNull BufferedImage finalImage;
    if (skin.getHeight() == 64) {
      finalImage = new BufferedImage(32, 8, BufferedImage.TYPE_INT_RGB);
      final BufferedImage part1 = skin.getSubimage(0, 20, 16, 8);
      final BufferedImage part1Overlay = skin.getSubimage(0, 36, 16, 8);
      final BufferedImage part2 = skin.getSubimage(16, 52, 16, 8);
      final BufferedImage part2Overlay = skin.getSubimage(0, 52, 16, 8);
      final Graphics2D g = finalImage.createGraphics();
      g.drawImage(part1, 0, 0, null);
      g.drawImage(part1Overlay, 0, 0, null);
      g.drawImage(part2, 16, 0, null);
      g.drawImage(part2Overlay, 16, 0, null);
      g.dispose();
    } else {
      finalImage = new BufferedImage(16, 8, BufferedImage.TYPE_INT_RGB);
      final BufferedImage part1 = skin.getSubimage(0, 20, 16, 8);
      final Graphics2D g = finalImage.createGraphics();
      g.drawImage(part1, 0, 0, null);
      g.dispose();
    }

    return finalImage;
  }

  public static @NotNull BufferedImage getFeetTexture(final @NotNull BufferedImage skin) {
    final @NotNull BufferedImage finalImage;
    if (skin.getHeight() == 64) {
      finalImage = new BufferedImage(32, 4, BufferedImage.TYPE_INT_RGB);
      final BufferedImage part1 = skin.getSubimage(0, 28, 16, 4);
      final BufferedImage part1Overlay = skin.getSubimage(0, 44, 16, 4);
      final BufferedImage part2 = skin.getSubimage(16, 60, 16, 4);
      final BufferedImage part2Overlay = skin.getSubimage(0, 60, 16, 4);
      final Graphics2D g = finalImage.createGraphics();
      g.drawImage(part1, 0, 0, null);
      g.drawImage(part1Overlay, 0, 0, null);
      g.drawImage(part2, 16, 0, null);
      g.drawImage(part2Overlay, 16, 0, null);
      g.dispose();
    } else {
      finalImage = new BufferedImage(16, 4, BufferedImage.TYPE_INT_RGB);
      final BufferedImage part1 = skin.getSubimage(0, 28, 16, 4);
      final Graphics2D g = finalImage.createGraphics();
      g.drawImage(part1, 0, 0, null);
      g.dispose();
    }

    return finalImage;
  }

  public static @NotNull ItemStack getChestplate(final @NotNull BufferedImage skin,
      final int quality) {
    final int @Nullable [] rgb = ColorThief.getColor(getBodyTexture(skin), quality);
    final @NotNull ItemStack itemStack = new ItemStack(Material.LEATHER_CHESTPLATE);
    if (rgb != null && itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta) {
      leatherArmorMeta.setColor(Color.fromRGB(rgb[0], rgb[1], rgb[2]));
      itemStack.setItemMeta(leatherArmorMeta);
    }
    return itemStack;
  }

  public static @NotNull ItemStack getLeggings(final @NotNull BufferedImage skin,
      final int quality) {
    final int @Nullable [] rgb = ColorThief.getColor(getLegsTexture(skin), quality);
    final @NotNull ItemStack itemStack = new ItemStack(Material.LEATHER_LEGGINGS);
    if (rgb != null && itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta) {
      leatherArmorMeta.setColor(Color.fromRGB(rgb[0], rgb[1], rgb[2]));
      itemStack.setItemMeta(leatherArmorMeta);
    }
    return itemStack;
  }

  public static @NotNull ItemStack getBoots(final @NotNull BufferedImage skin, final int quality) {
    final int @Nullable [] rgb = ColorThief.getColor(getFeetTexture(skin), quality);
    final @NotNull ItemStack itemStack = new ItemStack(Material.LEATHER_BOOTS);
    if (rgb != null && itemStack.getItemMeta() instanceof LeatherArmorMeta leatherArmorMeta) {
      leatherArmorMeta.setColor(Color.fromRGB(rgb[0], rgb[1], rgb[2]));
      itemStack.setItemMeta(leatherArmorMeta);
    }
    return itemStack;
  }

  public static ItemStack @NotNull [] getItems(final String playerName, final int quality)
      throws IOException {
    final BufferedImage skin = getPlayerSkinTexture(playerName);
    return new ItemStack[]{getChestplate(skin, quality), getLeggings(skin, quality),
        getBoots(skin, quality)};
  }
}