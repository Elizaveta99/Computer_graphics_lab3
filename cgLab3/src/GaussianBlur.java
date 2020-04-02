import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

public class GaussianBlur {
    public static BufferedImage img;
    public static BufferedImage imgOut;
    
    static int[] filter = {1, 2, 1, 2, 4, 2, 1, 2, 1};
    static int W = 16;
    static int filterWidth = 3;
    
    public static void readImage(String filename)
    {
	GaussianBlur.img = null;
	try {
            System.out.println(filename);
            img = ImageIO.read(new File(filename));
	} catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeImage(String filename)
	{
		File output = new File(filename);
		try {
			ImageIO.write(imgOut, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    
    public static void blur() 
    {
        BufferedImage image = img;
    if (filter.length % filterWidth != 0) {
        throw new IllegalArgumentException("filter contains a incomplete row");
    }

    final int width = image.getWidth();
    final int height = image.getHeight();

    int[] input = image.getRGB(0, 0, width, height, null, 0, width);
    int[] output = new int[input.length];
    
    int[][] input_2d = new int[height + 2][width + 2];
    int[][] output_2d = new int[height + 2][width + 2];

    for (int row = 0; row < height + 2; row++) 
        for (int col = 0; col < width + 2; col++)
            input_2d[row][col] = 0;
    
    for (int row = 1; row <= height; row++) {
        for (int col = 1; col <= width; col++) {
            input_2d[row][col] = image.getRGB(col - 1, row - 1);
        }
    }
    
    int cnt_o = 0;
    for (int row = 1; row <= height; row++)
    {
        for (int col = 1; col <= width; col++)
        {
            int sum = 0, cnt = 0;
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++)
                {
                     sum += (filter[cnt] * input_2d[row + i][col + j]);
                     cnt++;
                }
            
            output_2d[row - 1][col - 1] = sum / W;
            output[cnt_o] = output_2d[row - 1][col - 1];
            cnt_o++;
        }
    }


    BufferedImage result = new BufferedImage(width, height, image.getType());
    //BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY); // ??
    //BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY); // ??
    result.setRGB(0, 0, width, height, output, 0, width);
    imgOut = result;
    
    for(int x = 0; x < imgOut.getWidth(); x++)
		{
			for(int y = 0; y < imgOut.getHeight(); y++)
			{
				int rgb = imgOut.getRGB(x, y);
				Color c = new Color(rgb);
				int grey = (int) (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114*c.getBlue());
				Color c2 = new Color(grey, grey, grey);
				imgOut.setRGB(x, y, c2.getRGB());
			}
		}
    }	
    
}
