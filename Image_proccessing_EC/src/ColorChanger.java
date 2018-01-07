
import java.io.*;
import javax.imageio.*;

import java.awt.Color;
import java.awt.image.*;

public class ColorChanger {
	
	public static int rotateHue(int rgb, float angle ) {
		int b=rgb&0xFF;
		int g=(rgb>>8)&0xFF;
		int r=(rgb>>16)&0xFF;
		float[] hsb=Color.RGBtoHSB(r, g, b, null);
		hsb[0]+=angle;
		return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
	}
	
	
	public static void main(String args[]) throws IOException {
		BufferedImage raw, processed;
		raw = ImageIO.read(new File("flower.png"));
		int width = raw.getWidth();
		int height = raw.getHeight();
		processed = new BufferedImage(width, height, raw.getType());
		float hue = 90 / 360.0f;
		// hard coded hue value
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = rotateHue(raw.getRGB(x, y), hue);
				processed.setRGB(x, y, rgb);
			}
		}
		ImageIO.write(processed, "PNG", new File("processed.png"));
	}
}