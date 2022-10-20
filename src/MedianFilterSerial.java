import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.awt.Color;

public class MedianFilterSerial {
  /**
   * global variables
   */
  static long startTime = 0;
	static long runTime = 0;
  static String inputName="";
  static String outputName="";
  static int filterSize = 3;
  /**
   * methods to calculate time
   */
  private static void tic(){startTime = System.currentTimeMillis();}
	private static void toc(){runTime = (System.currentTimeMillis() - startTime);}
  /**
   * main method
   * @param args
   * @throws IOException
   */
  public static void main(String args[]) throws IOException {
    /**
     * local varibles of main method
     */
    BufferedImage img = null;
    BufferedImage img1 = null;
    File f = null;
    inputName = args[0];
    outputName = args[1];
    filterSize = Integer.parseInt(args[2]);
    int p = 0;
    // read image
    try {
      f = new File("/home/k/knnbon009/testCode/"+args[0]);
      img = ImageIO.read(f);
      img1 = ImageIO.read(f);
    } catch (IOException e) {System.out.println(e);}
    /**
     * get width and height of an image
     */
    int width = img.getWidth();
    int height = img.getHeight();
    //varible for array size
    int quadroMatrixSize = (int) Math.pow(filterSize + filterSize, 2);
    tic(); //start calculating time
    for (int w = filterSize; w < width - filterSize; w++) {
      for (int h = filterSize; h < height - filterSize; h++) {
        Color[] PixelArray = new Color[quadroMatrixSize];
        int[] RedArray = new int[quadroMatrixSize];
        int[] BlueArray = new int[quadroMatrixSize];
        int[] GreenArray = new int[quadroMatrixSize];
        int counter = 0;
        for (int i = w - filterSize; i < w + filterSize; i++) {
          for (int j = h - filterSize; j < h + filterSize; j++) {
            // int RGB = img.getRGB(i,j);
            // PixelArray[counter] = new Color(img.getRGB(i,j));
            //Color pixel = new Color(img.getRGB(i, j));
            p = img.getRGB(i, j);
            RedArray[counter] = (p>>16) & 0xff;//pixel.getRed();
            GreenArray[counter] = (p>>8) & 0xff;//pixel.getGreen();
            BlueArray[counter] = p & 0xff;//pixel.getBlue();
            counter++;
          }
        }
        int alpha = (p>>24) & 0xff;
        Arrays.sort(RedArray);
        Arrays.sort(GreenArray);
        Arrays.sort(BlueArray);
        int midRed = RedArray[quadroMatrixSize / 2];
        int midGreen = GreenArray[quadroMatrixSize / 2];
        int midBlue = BlueArray[quadroMatrixSize / 2];
        int newPixel = (alpha<<24) | (midRed<<16) | (midGreen<<8) | midBlue;//new Color(midRed, midGreen, midBlue);// 
        img1.setRGB(w, h, newPixel);
        toc(); //end calculating time
      }
    }
    // write an image 
    try {
      File out = new File("/home/k/knnbon009/testCode/"+args[1]);
      ImageIO.write(img1, "jpg", out); 
    } catch (IOException e) {System.out.println(e);}
    System.out.println("Run1 took "+ runTime/ 1000.0f +" seconds for Filter Size of "+filterSize);
    System.out.println("Run2 took "+ runTime/ 1000.0f +" seconds for Filter Size of "+filterSize);
  }
}

