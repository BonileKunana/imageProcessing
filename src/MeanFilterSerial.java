import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.awt.Color;

public class MeanFilterSerial {
  /**
   * global instance variables
   */
  static long startTime = 0;
	static long runTime = 0;
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
     * local variables of main method
     */
    BufferedImage img = null;
    BufferedImage img1 = null;
    File f = null;
    int p = 0;
    //int aveR=0;
    //int aveG=0;
    //int aveB=0;
    //int aveA=0;
  
    
    int filterSize = Integer.parseInt(args[2]);
    // read image
    try {
      f = new File("/home/k/knnbon009/testCode/"+args[0]);
      img = ImageIO.read(f);
      img1 = ImageIO.read(f);
    } catch (IOException e) {System.out.println(e);}
    //get width and height of an image
    int width = img.getWidth();
    int height = img.getHeight();
    // variable for an array size 
    int sumR = 0;
    int sumG = 0;
    int sumB = 0;
    int sumA = 0;
    int fSize = (filterSize-1)/2;
    //int quadroMatrixSize = (int) Math.pow(filterSize + filterSize, 2);
    tic(); //start calculating time
    
     for (int w = fSize; w < width - fSize; w++) {
           
       for (int h = fSize; h < height - fSize; h++) {  
      
       // Color[] PixelArray = new Color[quadroMatrixSize];
        //int[] RedArray = new int[quadroMatrixSize];
        //int[] BlueArray = new int[quadroMatrixSize];
        //int[] GreenArray = new int[quadroMatrixSize];
        //int counter = 0;
          sumR = 0;
          sumG = 0;
          sumB = 0;
          sumA = 0;
         
        for (int i = w - fSize; i < w + fSize; i++) {
          for (int j = h - fSize; j <h + fSize; j++) {
            p = img.getRGB(i, j);
            int alpha = (p>>24) & 0xff;
            sumA =sumA+alpha;
             int R = (p>>16) & 0xff;//pixel.getRed();
             sumR +=R; 
            int G = (p>>8) & 0xff;//pixel.getGreen();
            sumG +=G;
            int B = p & 0xff;//pixel.getBlue();
            sumB +=B;
            
            //counter++;
          }
          
        }
        //int alpha = (p>>24) & 0xff;
        int aveR = sumR/filterSize*filterSize;
        int aveG = sumG/filterSize*filterSize;
        int aveB = sumB/filterSize*filterSize;
        int aveA = sumA/filterSize*filterSize;
        p = (aveA<<24)| (aveR<<16) | (aveG<<8) | (aveB);// new Color(aveR, aveG, aveB);//// 
        img1.setRGB(w, h, p);
        toc(); //end calculating time
        
      }
     
     
      
    }
    //write an image 
    try {
      File out = new File("/home/k/knnbon009/testCode/"+args[1]);
      ImageIO.write(img1, "jpg", out); 
    } catch (IOException e) {System.out.println(e);}
    // report time taken 
    System.out.println("Run1 took "+ runTime/ 1000.0f +" seconds for Filter Size of "+filterSize);
    System.out.println("Run2 took "+ runTime/ 1000.0f +" seconds for Filter Size of "+filterSize);
  
}
}


