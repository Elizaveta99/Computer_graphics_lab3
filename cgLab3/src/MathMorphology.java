import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class MathMorphology {
    
    public static BufferedImage img;
    public static BufferedImage imgOut;

    public static int[] structBox = {1, 1, 1, 1, 1, 1, 1, 1, 1}; 
    public static int[] structDisk = {0, 1, 0, 1, 1, 1, 0, 1, 0};
    
    public static void readImage(String filename)
    {
	img = null;
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
    
    public static void dilation()
    {
        int[] struct;
        if (Main.structElement == 1)
            struct = structBox;
        else
            struct = structDisk;
        
        BufferedImage image = img;

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
                int mx = 0, cnt = 0;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                        if (i != 0 && j != 0)
                        {
                            if (struct[cnt] == 1)
                                mx = Math.max(mx, input_2d[row + i][col + j]);
                            cnt++;
                        }
                        else cnt++;
            
                output_2d[row - 1][col - 1] = mx;
                output[cnt_o] = output_2d[row - 1][col - 1];
                cnt_o++;
        }
    }


        BufferedImage result = new BufferedImage(width, height, image.getType());
        //BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY); // ??
        result.setRGB(0, 0, width, height, output, 0, width);
        imgOut = result;
    }
    
    public static void erosion()
    {
        int[] struct;
        if (Main.structElement == 1)
            struct = structBox;
        else
            struct = structDisk;
        
        BufferedImage image = img;

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
                int mn = Integer.MAX_VALUE, cnt = 0;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                        if (i != 0 && j != 0)
                        {
                            if (struct[cnt] == 1)
                                mn = Math.min(mn, input_2d[row + i][col + j]);
                            cnt++;
                        }
                        else cnt++;
            
                output_2d[row - 1][col - 1] = mn;
                output[cnt_o] = output_2d[row - 1][col - 1];
                cnt_o++;
        }
    }


        BufferedImage result = new BufferedImage(width, height, image.getType());
        //BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY); // ??
        //BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        result.setRGB(0, 0, width, height, output, 0, width);
        imgOut = result;
    }
}
