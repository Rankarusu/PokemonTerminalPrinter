package moe.ranka;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePrinter {
    private static final char block = '█';

    public void printImage(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();

        var sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // getRGB returns an integer in the following hex format 0xAARRGGBB (Alpha,Red,Green,Blue)
                int rgb = img.getRGB(j, i);
                // extract the alpha channel since there is no get method for it
                int alpha = (rgb >> 24) & 0xFF;

                if (alpha == 0) {
                    System.out.print(" ");
                } else {
                    var color = new Color(rgb);
                    System.out.print(this.getEscapeSequenceTrueColor(color) + block);
                }
            }
            System.out.print("\n");
        }
    }

    private String getEscapeSequenceTrueColor(Color color) {
        //38 specifies foreground color and 2 that we use 24 bit true color
        return String.format("\u001B[38;2;%s;%s;%sm",
                             color.getRed(),
                             color.getGreen(),
                             color.getBlue());
    }

    private String getEscapeSequence8bit(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        // the rgb space in xterm is divided into smaller palettes.
        // Each channel is divided into 6 levels
        // By doing * 6 / 256 we get the level closest to our rgb value.
        // we skip the first 16 as these are the standard ansi colors.
        int index = 16 + (36 * (r * 6 / 256)) + (6 * (g * 6 / 256)) + (b * 6 / 256);
        //38 specifies foreground color and 5 that we use 8Bit colors (xterm lookup table)
        return String.format("\u001B[38;5;%sm", index);
    }


}
