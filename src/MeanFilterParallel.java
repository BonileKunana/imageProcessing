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



public class MeanFilterParallel extends RecursiveTask<Integer> {
/**
 * instance variables
 */
static long startTime = 0;
static long runTime = 0;
static int filterSize;
int aveR = 0;
int aveG = 0;
int aveB = 0;
int sumR = 0;
int sumG = 0;
int sumB = 0;
int p =0;
int xy;
int dividor = 10;
int imageHeight;
int imageWidth;
static File f = null;
static BufferedImage img;
static BufferedImage img1;
static String inputName="";
static String outputName="";

static final int SEQUENTIAL_CUTOFF=400;
static int myWidth;
static int myHeight;
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
public MeanFilterParallel(int windowSize, int height,int width,int x){
    filterSize = windowSize;
    imageHeight = height;
    imageWidth = width;
    xy =x;
}
/**
 * override compute
 */
protected Integer compute(){
    if(img.getWidth()/dividor<SEQUENTIAL_CUTOFF){
        int quadroMatrixSize = (int) Math.pow(filterSize + filterSize, 2);
        Color[] PixelArray = new Color[quadroMatrixSize];
        int[] RedArray = new int[quadroMatrixSize];
        int[] BlueArray = new int[quadroMatrixSize];
        int[] GreenArray = new int[quadroMatrixSize];
        tic(); //start calculating time
        //long startTime = System.currentTimeMillis();
        for (int w = filterSize; w < img.getWidth() - filterSize; w++) {
          for (int h = filterSize; h < img.getHeight() - filterSize; h++) {
           
            int counter = 0;
            for (int i = w - filterSize; i < w + filterSize; i++) {
              for (int j = h - filterSize; j < h + filterSize; j++) {
                p = img.getRGB(i, j);
             int R = (p>>16) & 0xff;//pixel.getRed();
             sumR +=R; 
            int G = (p>>8) & 0xff;//pixel.getGreen();
            sumG +=G;
            int B = p & 0xff;//pixel.getBlue();
            sumB += B;
            counter++;
                //System.out.println("here"); 
              }
              
            }
        int alpha = (p>>24) & 0xff;
        aveR = sumR/filterSize*filterSize;
        aveG = sumG/filterSize*filterSize;
        aveB = aveB/filterSize*filterSize;
        int newPixel = (alpha<<24) | (aveR<<16) | (aveG<<8) | aveB;// new Color(aveR, aveG, aveB);// 
        toc(); //end calculating time
        //long endTime =System.currentTimeMillis() - startTime;
        //System.out.println(endTime);
        img1.setRGB(w, h, newPixel);
        
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
        
        MeanFilterParallel left = new MeanFilterParallel(filterSize, img.getHeight(), 0,img.getWidth()/2);
        MeanFilterParallel right = new MeanFilterParallel(filterSize, img.getHeight(), img.getWidth()/2,img.getWidth()-1);
        
        left.fork();
        int rightAns = right.compute();
        int leftAns = left.join();
        
        dividor +=10;
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
        myWidth = img.getWidth();
        MeanFilterParallel obj = new MeanFilterParallel(filterSize, img.getHeight(), 0,img.getWidth());
        obj.compute();
      } catch (IOException e) {System.out.println(e);}
      //int width = img.getWidth();
      //int height = img.getHeight();
      System.out.println("Run1 took "+ runTime/ 1000.0f +" seconds for Filter Size of "+filterSize);
        System.out.println("Run2 took "+ runTime/ 1000.0f +" seconds for Filter Size of "+filterSize);
     
}

}
