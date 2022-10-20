import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*; 
import java.util.*; 

public class MeanBranch{
  public static void main(String args[])throws IOException{
    BufferedImage img = null;
    File f = null;
    int filterSize = 3;
    int start=0;
    //read image
    try{
     f = new File("/home/k/knnbon009/Assignment1/uct.jpg");
      img = ImageIO.read(f);
    }catch(IOException e){
      System.out.println(e);
    }
    int width = img.getWidth();
    int height = img.getHeight();
    ArrayList<Integer> pixelArr = new ArrayList<Integer>();
    //int[] pixelArr = new [heigtht*width];
     for(int w=0; w<width; ++w){
     for(int h=0; h<height; ++h){
     if(w==0 || w == width-1 || h==0 || h==height-1){System.out.println("border");}
     else{
     start = filterSize/2;
     }
     System.out.println(start);
     /*for(int i=0; i<filterSize; i++){
          int p = img.getRGB(w,h);
          pixelArr.add(p);
         }*/
       int p = img.getRGB(w,h);
       int r = (p>>16) & 0xff;
       int g = (p>>8) & 0xff;
       int b = p & 0xff;
       //System.out.println("red);
       /*for(int n=k; n<k+3; n++){
       for(int m=k; m<k+3; k++){
         int p = img.getRGB(n,m);
         int a = (p>>24) & 0xff;
        // System.out.println("alpha "+a);
         int r = (p>>16) & 0xff;
         System.out.println("red "+r);

        }
       }*/
        //System.out.println(p);
        /*int a = (p>>24) & 0xff;
        System.out.println("alpha "+a);
      //get red
      int r = (p>>16) & 0xff;
      System.out.println("red "+r);
       //get green
       int g = (p>>8) & 0xff;
       System.out.println("green "+g);
       //get blue
    int b = p & 0xff;
        System.out.println("blue "+b);*/
    }
    }
  }
}
