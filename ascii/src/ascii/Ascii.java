package ascii;

import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

public class Ascii {
	public static void main(String[] args) throws IOException {
		BufferedImage image = ImageIO.read(new File("image.png"));
		int[][] picture = convertTo2DWithoutUsingGetRGB(image);

		int[][] grayscale = new int[picture.length][picture[0].length];
		for (int i = 0; i < picture.length; i++) {
			for (int j = 0; j < picture[0].length; j++) {
				grayscale[i][j] = (int)((picture[i][j] & 0xFF) + ((picture[i][j] >> 8) & 0xFF)
						+ ((picture[i][j] >> 16) & 0xFF) / 6);
				System.out.print(Character.toString(printascii(grayscale[i][j])));
				// System.out.print(grayscale[i][j]);
				// System.out.print(picture[i][j]);
			}
			System.out.println("");
		}
	}

	private static char printascii(int pixel) {
		char[] s = {'$','@','B','%','8','&','W','M','#','*','o','a','h','k','b','d','p','q','w','m','Z','O','0','Q','L','C','J','U','Y','X','z','c','v',
				'u','n','x','r','j'	,'f','t','/','|','(',')','1','{','}','[',']','?','-','_','+','~','<','>','i','!','l','I',';',':',',','"','^','`','s',
				'.' };
		if((int) (((double) pixel)/5) < 68) {
			return s[(int) (((double) pixel)/5)];
		}
		return ' ';
	
	//if(pixel<28)return"@";if(pixel<56)return"@";if(pixel<84)return"#";if(pixel<112)return"8";if(pixel<140)return"&";if(pixel<168)return"o";if(pixel<196)return":";if(pixel<224)return"*";if(pixel<252)return".";

	//return" ";

	}

	private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}
}
