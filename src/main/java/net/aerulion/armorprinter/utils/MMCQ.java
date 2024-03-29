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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MMCQ {

  private static final int SIGBITS = 5;
  private static final int RSHIFT = 8 - SIGBITS;
  private static final int MULT = 1 << RSHIFT;
  private static final int HISTOSIZE = 1 << (3 * SIGBITS);
  private static final int VBOX_LENGTH = 1 << SIGBITS;
  private static final double FRACT_BY_POPULATION = 0.75;
  private static final int MAX_ITERATIONS = 1000;
  private static final Comparator<VBox> COMPARATOR_COUNT = Comparator.comparingInt(
      a -> a.count(false));
  private static final Comparator<VBox> COMPARATOR_PRODUCT = (a, b) -> {
    int aCount = a.count(false);
    int bCount = b.count(false);
    int aVolume = a.volume(false);
    int bVolume = b.volume(false);

    // If count is 0 for both (or the same), sort by volume
    if (aCount == bCount) {
      return aVolume - bVolume;
    }

    // Otherwise, sort by products
    return Long.compare((long) aCount * aVolume, (long) bCount * bVolume);
  };

  private MMCQ() {
  }

  /**
   * Get reduced-space color index for a pixel.
   *
   * @param r the red value
   * @param g the green value
   * @param b the blue value
   * @return the color index
   */
  static int getColorIndex(final int r, final int g, final int b) {
    return (r << (2 * SIGBITS)) + (g << SIGBITS) + b;
  }

  /**
   * Histo (1-d array, giving the number of pixels in each quantized region of color space), or null
   * on error.
   */
  private static int @NotNull [] getHisto(final int @NotNull [] @NotNull [] pixels) {
    final int @NotNull [] histo = new int[HISTOSIZE];
    int index;
    int rval;
    int gval;
    int bval;

    for (final int[] pixel : pixels) {
      rval = pixel[0] >> RSHIFT;
      gval = pixel[1] >> RSHIFT;
      bval = pixel[2] >> RSHIFT;
      index = getColorIndex(rval, gval, bval);
      histo[index]++;
    }
    return histo;
  }

  private static @NotNull VBox vboxFromPixels(final int @NotNull [] @NotNull [] pixels,
      final int[] histo) {
    int rmin = 1000000;
    int rmax = 0;
    int gmin = 1000000;
    int gmax = 0;
    int bmin = 1000000;
    int bmax = 0;

    int rval;
    int gval;
    int bval;

    // find min/max
    for (final int[] pixel : pixels) {
      rval = pixel[0] >> RSHIFT;
      gval = pixel[1] >> RSHIFT;
      bval = pixel[2] >> RSHIFT;

      if (rval < rmin) {
        rmin = rval;
      } else if (rval > rmax) {
        rmax = rval;
      }

      if (gval < gmin) {
        gmin = gval;
      } else if (gval > gmax) {
        gmax = gval;
      }

      if (bval < bmin) {
        bmin = bval;
      } else if (bval > bmax) {
        bmax = bval;
      }
    }

    return new VBox(rmin, rmax, gmin, gmax, bmin, bmax, histo);
  }

  private static VBox @Nullable [] medianCutApply(final int[] histo, final @NotNull VBox vbox) {
    if (vbox.count(false) == 0) {
      return null;
    }

    // only one pixel, no split
    if (vbox.count(false) == 1) {
      return new VBox[]{vbox.clone(), null};
    }

    final int rw = vbox.r2 - vbox.r1 + 1;
    final int gw = vbox.g2 - vbox.g1 + 1;
    final int bw = vbox.b2 - vbox.b1 + 1;
    final int maxw = Math.max(Math.max(rw, gw), bw);

    // Find the partial sum arrays along the selected axis.
    int total = 0;
    final int @NotNull [] partialsum = new int[VBOX_LENGTH];
    Arrays.fill(partialsum, -1); // -1 = not set / 0 = 0
    final int @NotNull [] lookaheadsum = new int[VBOX_LENGTH];
    Arrays.fill(lookaheadsum, -1); // -1 = not set / 0 = 0
    int i, j, k, sum, index;

    if (maxw == rw) {
      for (i = vbox.r1; i <= vbox.r2; i++) {
        sum = 0;
        for (j = vbox.g1; j <= vbox.g2; j++) {
          for (k = vbox.b1; k <= vbox.b2; k++) {
            index = getColorIndex(i, j, k);
            sum += histo[index];
          }
        }
        total += sum;
        partialsum[i] = total;
      }
    } else if (maxw == gw) {
      for (i = vbox.g1; i <= vbox.g2; i++) {
        sum = 0;
        for (j = vbox.r1; j <= vbox.r2; j++) {
          for (k = vbox.b1; k <= vbox.b2; k++) {
            index = getColorIndex(j, i, k);
            sum += histo[index];
          }
        }
        total += sum;
        partialsum[i] = total;
      }
    } else
      /* maxw == bw */ {
      for (i = vbox.b1; i <= vbox.b2; i++) {
        sum = 0;
        for (j = vbox.r1; j <= vbox.r2; j++) {
          for (k = vbox.g1; k <= vbox.g2; k++) {
            index = getColorIndex(j, k, i);
            sum += histo[index];
          }
        }
        total += sum;
        partialsum[i] = total;
      }
    }

    for (i = 0; i < VBOX_LENGTH; i++) {
      if (partialsum[i] != -1) {
        lookaheadsum[i] = total - partialsum[i];
      }
    }

    // determine the cut planes
    return maxw == rw ? doCut('r', vbox, partialsum, lookaheadsum, total)
        : maxw == gw ? doCut('g', vbox, partialsum, lookaheadsum, total)
            : doCut('b', vbox, partialsum, lookaheadsum, total);
  }

  private static VBox @NotNull [] doCut(final char color, final @NotNull VBox vbox,
      final int[] partialsum, final int[] lookaheadsum, final int total) {
    final int vboxDim1;
    final int vboxDim2;

    if (color == 'r') {
      vboxDim1 = vbox.r1;
      vboxDim2 = vbox.r2;
    } else if (color == 'g') {
      vboxDim1 = vbox.g1;
      vboxDim2 = vbox.g2;
    } else
      /* color == 'b' */ {
      vboxDim1 = vbox.b1;
      vboxDim2 = vbox.b2;
    }

    final int left;
    final int right;
    final @Nullable VBox vbox1;
    final @Nullable VBox vbox2;
    int d2;
    int count2;

    for (int i = vboxDim1; i <= vboxDim2; i++) {
      if (partialsum[i] > total / 2) {
        vbox1 = vbox.clone();
        vbox2 = vbox.clone();

        left = i - vboxDim1;
        right = vboxDim2 - i;

        if (left <= right) {
          d2 = Math.min(vboxDim2 - 1, (i + right / 2));
        } else {
          // 2.0 and cast to int is necessary to have the same
          // behaviour as in JavaScript
          d2 = Math.max(vboxDim1, ((int) (i - 1 - left / 2.0)));
        }

        // avoid 0-count boxes
        while (d2 < 0 || partialsum[d2] <= 0) {
          d2++;
        }
        count2 = lookaheadsum[d2];
        while (count2 == 0 && d2 > 0 && partialsum[d2 - 1] > 0) {
          count2 = lookaheadsum[--d2];
        }

        // set dimensions
        if (color == 'r') {
          vbox1.r2 = d2;
          vbox2.r1 = d2 + 1;
        } else if (color == 'g') {
          vbox1.g2 = d2;
          vbox2.g1 = d2 + 1;
        } else
          /* color == 'b' */ {
          vbox1.b2 = d2;
          vbox2.b1 = d2 + 1;
        }

        return new VBox[]{vbox1, vbox2};
      }
    }

    throw new RuntimeException("VBox can't be cut");
  }

  public static @Nullable CMap quantize(final int @NotNull [] @NotNull [] pixels,
      final int maxColors) {
    // short-circuit
    if (pixels.length == 0 || maxColors < 2 || maxColors > 256) {
      return null;
    }

    final int @NotNull [] histo = getHisto(pixels);

    // get the beginning vbox from the colors
    final @NotNull VBox vbox = vboxFromPixels(pixels, histo);
    final @NotNull ArrayList<VBox> pq = new ArrayList<>();
    pq.add(vbox);

    // Round up to have the same behaviour as in JavaScript
    final int target = (int) Math.ceil(FRACT_BY_POPULATION * maxColors);

    // first set of colors, sorted by population
    iter(pq, COMPARATOR_COUNT, target, histo);

    // Re-sort by the product of pixel occupancy times the size in color
    // space.
    pq.sort(COMPARATOR_PRODUCT);

    // next set - generate the median cuts using the (npix * vol) sorting.
    if (maxColors > pq.size()) {
      iter(pq, COMPARATOR_PRODUCT, maxColors, histo);
    }

    // Reverse to put the highest elements first into the color map
    Collections.reverse(pq);

    // calculate the actual colors
    final @NotNull CMap cmap = new CMap();
    for (final VBox vb : pq) {
      cmap.push(vb);
    }

    return cmap;
  }

  /**
   * Inner function to do the iteration.
   */
  private static void iter(final @NotNull List<VBox> lh, final Comparator<VBox> comparator,
      final int target, final int[] histo) {
    int niters = 0;
    VBox vbox;

    while (niters < MAX_ITERATIONS) {
      vbox = lh.get(lh.size() - 1);
      if (vbox.count(false) == 0) {
        lh.sort(comparator);
        niters++;
        continue;
      }
      lh.remove(lh.size() - 1);

      // do the cut
      final VBox @Nullable [] vboxes = medianCutApply(histo, vbox);

      if (vboxes == null) {
        throw new IllegalStateException("vboxes not defined; shouldn't happen!");
      }

      final VBox vbox1 = vboxes[0];
      final VBox vbox2 = vboxes[1];

      if (vbox1 == null) {
        throw new IllegalStateException("vbox1 not defined; shouldn't happen!");
      }

      lh.add(vbox1);
      if (vbox2 != null) {
        lh.add(vbox2);
      }
      lh.sort(comparator);

      if (lh.size() >= target) {
        return;
      }
      niters++;
    }
  }

  /**
   * 3D color space box.
   */
  public static class VBox {

    private final int[] histo;
    public int r1;
    public int r2;
    public int g1;
    public int g2;
    public int b1;
    public int b2;
    private int[] _avg;
    private Integer _volume;
    private Integer _count;

    public VBox(final int r1, final int r2, final int g1, final int g2, final int b1, final int b2,
        final int[] histo) {
      this.r1 = r1;
      this.r2 = r2;
      this.g1 = g1;
      this.g2 = g2;
      this.b1 = b1;
      this.b2 = b2;

      this.histo = histo;
    }

    public int volume(final boolean force) {
      if (_volume == null || force) {
        _volume = ((r2 - r1 + 1) * (g2 - g1 + 1) * (b2 - b1 + 1));
      }

      return _volume;
    }

    public int count(final boolean force) {
      if (_count == null || force) {
        int npix = 0;
        int i;
        int j;
        int k;
        int index;

        for (i = r1; i <= r2; i++) {
          for (j = g1; j <= g2; j++) {
            for (k = b1; k <= b2; k++) {
              index = getColorIndex(i, j, k);
              npix += histo[index];
            }
          }
        }

        _count = npix;
      }

      return _count;
    }

    @Override
    public @NotNull VBox clone() {
      return new VBox(r1, r2, g1, g2, b1, b2, histo);
    }

    @Override
    public @NotNull String toString() {
      return "r1: " + r1 + " / r2: " + r2 + " / g1: " + g1 + " / g2: " + g2 + " / b1: " + b1
          + " / b2: " + b2;
    }

    public int[] avg(final boolean force) {
      if (_avg == null || force) {
        int ntot = 0;

        int rsum = 0;
        int gsum = 0;
        int bsum = 0;

        int hval;
        int i;
        int j;
        int k;
        int histoindex;

        for (i = r1; i <= r2; i++) {
          for (j = g1; j <= g2; j++) {
            for (k = b1; k <= b2; k++) {
              histoindex = getColorIndex(i, j, k);
              hval = histo[histoindex];
              ntot += hval;
              rsum += (hval * (i + 0.5) * MULT);
              gsum += (hval * (j + 0.5) * MULT);
              bsum += (hval * (k + 0.5) * MULT);
            }
          }
        }

        if (ntot > 0) {
          _avg = new int[]{(rsum / ntot), (gsum / ntot), (bsum / ntot)};
        } else {
          _avg = new int[]{(MULT * (r1 + r2 + 1) / 2), (MULT * (g1 + g2 + 1) / 2),
              (MULT * (b1 + b2 + 1) / 2)};
        }
      }

      return _avg;
    }

  }

  /**
   * Color map.
   */
  public static class CMap {

    public final List<VBox> vboxes = new ArrayList<>();

    public void push(final VBox box) {
      vboxes.add(box);
    }

    public int @NotNull [] @NotNull [] palette() {
      final int numVBoxes = vboxes.size();
      final int @NotNull [] @NotNull [] palette = new int[numVBoxes][];
      for (int i = 0; i < numVBoxes; i++) {
        palette[i] = vboxes.get(i).avg(false);
      }
      return palette;
    }

    public int size() {
      return vboxes.size();
    }
  }

}