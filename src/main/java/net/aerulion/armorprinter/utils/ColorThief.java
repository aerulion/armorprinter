/*
 * Java Color Thief
 * by Sven Woltmann, Fonpit AG
 *
 * https://www.androidpit.com
 * https://www.androidpit.de
 *
 * License
 * -------
 * Creative Commons Attribution 2.5 License:
 * http://creativecommons.org/licenses/by/2.5/
 *
 * Thanks
 * ------
 * Lokesh Dhakar - for the original Color Thief JavaScript version
 * available at http://lokeshdhakar.com/projects/color-thief/
 *
 * ------
 * Slightly modified by aerulion
 *
 */
package net.aerulion.armorprinter.utils;

import net.aerulion.armorprinter.utils.MMCQ.CMap;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;

public class ColorThief {

    private static final boolean DEFAULT_IGNORE_WHITE = false;

    public static int[] getColor(BufferedImage sourceImage, int quality) {
        int[][] palette = getPalette(sourceImage, 2, quality);
        if (palette == null) {
            return null;
        }
        int[] dominantColor = palette[0];
        return dominantColor;
    }

    public static int[][] getPalette(BufferedImage sourceImage, int colorCount, int quality) {
        CMap cmap = getColorMap(sourceImage, colorCount, quality);
        if (cmap == null) {
            return null;
        }
        return cmap.palette();
    }

    public static CMap getColorMap(BufferedImage sourceImage, int colorCount, int quality) {
        boolean ignoreWhite = DEFAULT_IGNORE_WHITE;
        if (colorCount < 2 || colorCount > 256) {
            throw new IllegalArgumentException("Specified colorCount must be between 2 and 256.");
        }
        if (quality < 1) {
            throw new IllegalArgumentException("Specified quality should be greater then 0.");
        }

        int[][] pixelArray;

        switch (sourceImage.getType()) {
            case BufferedImage.TYPE_3BYTE_BGR:
            case BufferedImage.TYPE_4BYTE_ABGR:
                pixelArray = getPixelsFast(sourceImage, quality, ignoreWhite);
                break;

            default:
                pixelArray = getPixelsSlow(sourceImage, quality, ignoreWhite);
        }

        // Send array to quantize function which clusters values using median
        // cut algorithm
        CMap cmap = MMCQ.quantize(pixelArray, colorCount);
        return cmap;
    }

    private static int[][] getPixelsFast(BufferedImage sourceImage, int quality, boolean ignoreWhite) {
        DataBufferByte imageData = (DataBufferByte) sourceImage.getRaster().getDataBuffer();
        byte[] pixels = imageData.getData();
        int pixelCount = sourceImage.getWidth() * sourceImage.getHeight();

        int colorDepth;
        int type = sourceImage.getType();
        switch (type) {
            case BufferedImage.TYPE_3BYTE_BGR:
                colorDepth = 3;
                break;

            case BufferedImage.TYPE_4BYTE_ABGR:
                colorDepth = 4;
                break;

            default:
                throw new IllegalArgumentException("Unhandled type: " + type);
        }

        int expectedDataLength = pixelCount * colorDepth;
        if (expectedDataLength != pixels.length) {
            throw new IllegalArgumentException("(expectedDataLength = " + expectedDataLength + ") != (pixels.length = " + pixels.length + ")");
        }

        // Store the RGB values in an array format suitable for quantize
        // function

        // numRegardedPixels must be rounded up to avoid an
        // ArrayIndexOutOfBoundsException if all
        // pixels are good.
        int numRegardedPixels = (pixelCount + quality - 1) / quality;

        int numUsedPixels = 0;
        int[][] pixelArray = new int[numRegardedPixels][];
        int offset, r, g, b, a;

        // Do the switch outside of the loop, that's much faster
        switch (type) {
            case BufferedImage.TYPE_3BYTE_BGR:
                for (int i = 0; i < pixelCount; i += quality) {
                    offset = i * 3;
                    b = pixels[offset] & 0xFF;
                    g = pixels[offset + 1] & 0xFF;
                    r = pixels[offset + 2] & 0xFF;

                    // If pixel is not white
                    if (!(ignoreWhite && r > 250 && g > 250 && b > 250)) {
                        pixelArray[numUsedPixels] = new int[]{r, g, b};
                        numUsedPixels++;
                    }
                }
                break;

            case BufferedImage.TYPE_4BYTE_ABGR:
                for (int i = 0; i < pixelCount; i += quality) {
                    offset = i * 4;
                    a = pixels[offset] & 0xFF;
                    b = pixels[offset + 1] & 0xFF;
                    g = pixels[offset + 2] & 0xFF;
                    r = pixels[offset + 3] & 0xFF;

                    // If pixel is mostly opaque and not white
                    if (a >= 125 && !(ignoreWhite && r > 250 && g > 250 && b > 250)) {
                        pixelArray[numUsedPixels] = new int[]{r, g, b};
                        numUsedPixels++;
                    }
                }
                break;

            default:
                throw new IllegalArgumentException("Unhandled type: " + type);
        }

        // Remove unused pixels from the array
        return Arrays.copyOfRange(pixelArray, 0, numUsedPixels);
    }

    private static int[][] getPixelsSlow(BufferedImage sourceImage, int quality, boolean ignoreWhite) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();

        int pixelCount = width * height;

        // numRegardedPixels must be rounded up to avoid an
        // ArrayIndexOutOfBoundsException if all
        // pixels are good.
        int numRegardedPixels = (pixelCount + quality - 1) / quality;

        int numUsedPixels = 0;

        int[][] res = new int[numRegardedPixels][];
        int r, g, b;

        for (int i = 0; i < pixelCount; i += quality) {
            int row = i / width;
            int col = i % width;
            int rgb = sourceImage.getRGB(col, row);

            r = (rgb >> 16) & 0xFF;
            g = (rgb >> 8) & 0xFF;
            b = (rgb) & 0xFF;
            if (!(ignoreWhite && r > 250 && g > 250 && b > 250)) {
                res[numUsedPixels] = new int[]{r, g, b};
                numUsedPixels++;
            }
        }

        return Arrays.copyOfRange(res, 0, numUsedPixels);
    }
}