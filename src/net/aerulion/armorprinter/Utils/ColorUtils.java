package net.aerulion.armorprinter.Utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ColorUtils {

	public static BufferedImage getPlayerSkinTexture(String Playername) throws IOException {
		URL url = new URL("https://minotar.net/skin/" + Playername + ".png");
		BufferedImage img = ImageIO.read(url);
		return img;
	}

	public static BufferedImage getBodyTexture(BufferedImage skin) {
		BufferedImage finalImage;
		if (skin.getHeight() == 64) {
			finalImage = new BufferedImage(56, 12, BufferedImage.TYPE_INT_RGB);
			BufferedImage Part1 = skin.getSubimage(32, 52, 16, 12);
			BufferedImage Part1Overlay = skin.getSubimage(48, 52, 16, 12);
			BufferedImage Part2 = skin.getSubimage(16, 20, 40, 12);
			BufferedImage Part2Overlay = skin.getSubimage(16, 36, 40, 12);
			Graphics2D g = finalImage.createGraphics();
			g.drawImage(Part1, 0, 0, null);
			g.drawImage(Part1Overlay, 0, 0, null);
			g.drawImage(Part2, 16, 0, null);
			g.drawImage(Part2Overlay, 16, 0, null);
			g.dispose();
		} else {
			finalImage = new BufferedImage(40, 12, BufferedImage.TYPE_INT_RGB);
			BufferedImage Part1 = skin.getSubimage(16, 20, 40, 12);
			Graphics2D g = finalImage.createGraphics();
			g.drawImage(Part1, 0, 0, null);
			g.dispose();
		}

		return finalImage;
	}

	public static BufferedImage getLegsTexture(BufferedImage skin) {
		BufferedImage finalImage;
		if (skin.getHeight() == 64) {
			finalImage = new BufferedImage(32, 8, BufferedImage.TYPE_INT_RGB);
			BufferedImage Part1 = skin.getSubimage(0, 20, 16, 8);
			BufferedImage Part1Overlay = skin.getSubimage(0, 36, 16, 8);
			BufferedImage Part2 = skin.getSubimage(16, 52, 16, 8);
			BufferedImage Part2Overlay = skin.getSubimage(0, 52, 16, 8);
			Graphics2D g = finalImage.createGraphics();
			g.drawImage(Part1, 0, 0, null);
			g.drawImage(Part1Overlay, 0, 0, null);
			g.drawImage(Part2, 16, 0, null);
			g.drawImage(Part2Overlay, 16, 0, null);
			g.dispose();
		} else {
			finalImage = new BufferedImage(16, 8, BufferedImage.TYPE_INT_RGB);
			BufferedImage Part1 = skin.getSubimage(0, 20, 16, 8);
			Graphics2D g = finalImage.createGraphics();
			g.drawImage(Part1, 0, 0, null);
			g.dispose();
		}

		return finalImage;
	}

	public static BufferedImage getFeetTexture(BufferedImage skin) {
		BufferedImage finalImage;
		if (skin.getHeight() == 64) {
			finalImage = new BufferedImage(32, 4, BufferedImage.TYPE_INT_RGB);
			BufferedImage Part1 = skin.getSubimage(0, 28, 16, 4);
			BufferedImage Part1Overlay = skin.getSubimage(0, 44, 16, 4);
			BufferedImage Part2 = skin.getSubimage(16, 60, 16, 4);
			BufferedImage Part2Overlay = skin.getSubimage(0, 60, 16, 4);
			Graphics2D g = finalImage.createGraphics();
			g.drawImage(Part1, 0, 0, null);
			g.drawImage(Part1Overlay, 0, 0, null);
			g.drawImage(Part2, 16, 0, null);
			g.drawImage(Part2Overlay, 16, 0, null);
			g.dispose();
		} else {
			finalImage = new BufferedImage(16, 4, BufferedImage.TYPE_INT_RGB);
			BufferedImage Part1 = skin.getSubimage(0, 28, 16, 4);
			Graphics2D g = finalImage.createGraphics();
			g.drawImage(Part1, 0, 0, null);
			g.dispose();
		}

		return finalImage;
	}

	public static ItemStack getBodyItem(BufferedImage Skin, int quality) {
		int[] rgb = ColorThief.getColor(getBodyTexture(Skin), quality);
		ItemStack body = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta mbody = (LeatherArmorMeta) body.getItemMeta();
		mbody.setColor(Color.fromRGB(rgb[0], rgb[1], rgb[2]));
		body.setItemMeta(mbody);
		return body;
	}

	public static ItemStack getLegsItem(BufferedImage Skin, int quality) {
		int[] rgb = ColorThief.getColor(getLegsTexture(Skin), quality);
		ItemStack body = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta mbody = (LeatherArmorMeta) body.getItemMeta();
		mbody.setColor(Color.fromRGB(rgb[0], rgb[1], rgb[2]));
		body.setItemMeta(mbody);
		return body;
	}

	public static ItemStack getFeetItem(BufferedImage Skin, int quality) {
		int[] rgb = ColorThief.getColor(getFeetTexture(Skin), quality);
		ItemStack body = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta mbody = (LeatherArmorMeta) body.getItemMeta();
		mbody.setColor(Color.fromRGB(rgb[0], rgb[1], rgb[2]));
		body.setItemMeta(mbody);
		return body;
	}

	public static ItemStack[] getItems(String PlayerName, int quality) throws IOException {
		BufferedImage skin = getPlayerSkinTexture(PlayerName);
		ItemStack[] Items = { getBodyItem(skin, quality), getLegsItem(skin, quality), getFeetItem(skin, quality) };
		return Items;
	}

}
