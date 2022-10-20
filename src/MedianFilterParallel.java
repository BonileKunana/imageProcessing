import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.awt.Color;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.*;



public class MedianFilterParallel extends RecursiveTask<Integer> {
/**
 * instance variables
 */
static long startTime = 0;
static long runTime = 0;
static int filterSize = 3;
int p =0;
int dividor = 2;
int imageHeight;
int imageWidth;
static File f = null;
static BufferedImage img;
static BufferedImage img1;
static String inputName="";
static String outputName="";
int xy;

static final int SEQUENTIAL_CUTOFF=100;
/**
 * method to start calculating time
 */
private static void tic(){startTime = System.currentTimeMillis();}
/**
 * method to end calculating time
 */
private static void toc(){runTime = (System.currentTimeMillis() - startTime);}
/**
 * Constructor 
 * @param windowSize
 * @param height
 * @param Width
 */
public MedianFilterParallel(int windowSize, int height,int width,int x){
    filterSize = windowSize;
    imageHeight = height;
    imageWidth = width;
    xy =x;
}
/**
 * override compute
 */
protected Integer compute(){
     //System.out.println(imageWidth);
    if(imageWidth/dividor<SEQUENTIAL_CUTOFF){
    
        int quadroMatrixSize = (int) Math.pow(filterSize + filterSize, 2);
        tic(); //start calculating time
        for (int w = filterSize; w < xy - filterSize; w++) {

          for (int h = filterSize; h < imageHeight - filterSize; h++) {
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
            int newPixel = (alpha<<24) | (midRed<<16) | (midGreen<<8) | midBlue;//new Color(midRed, midGreen, midBlue);// (alpha<<24) | (midRed<<16) | (midGreen<<8) | midBlue;
            img1.setRGB(w, h, newPixel);
            toc(); //end calculating time
          }
        }
        try {
          File out = new File("/home/k/knnbon009/testCode/"+outputName);
          ImageIO.write(img1, "jpg", out); 
        } catch (IOException e) {System.out.println(e);}
        
        return 0;
    }
    else{
        try {
        f = new File("/home/k/knnbon009/testCode/"+inputName);
        img = ImageIO.read(f);
        img1 = ImageIO.read(f);
        }
        catch (IOException e) {System.out.println(e);}
        MedianFilterParallel left = new MedianFilterParallel(filterSize, img.getHeight(),0 ,img.getWidth()/2);
        MedianFilterParallel right = new MedianFilterParallel(filterSize, img.getHeight(),img.getWidth()/2 ,img.getWidth()-1);
        left.fork();
        int rightAns = right.compute();
        int leftAns = left.join();
        dividor++;
        return leftAns + rightAns;
    }
        
        
    
}
/**
 * main method
 * @param args
 */
public static void main(String[] args){
   
    try {
        f = new File("/home/k/knnbon009/testCode/"+args[0]);
        img = ImageIO.read(f);
        img1 = ImageIO.read(f);
        inputName = args[0];
        outputName = args[1];
        filterSize = Integer.parseInt(args[2]);
        MedianFilterParallel obj = new MedianFilterParallel(filterSize, img.getHeight(),0, img.getWidth());
        obj.compute();
        System.out.println(img.getWidth());
      } catch (IOException e) {System.out.println(e);}
      //int width = img.getWidth();
      //int height = img.getHeight();
      System.out.println("Run1 took "+ runTime/ 1000.0f +" seconds for Filter Size of "+filterSize);
        System.out.println("Run2 took "+ runTime/ 1000.0f +" seconds for Filter Size of "+filterSize);
     
}

}

