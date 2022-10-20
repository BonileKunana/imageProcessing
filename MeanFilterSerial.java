import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MeanFilterSerial{
  public static void main(String args[])throws IOException{
    BufferedImage img = null;
    File f = null;
    //read image
    try{
      f = new File("C:\\Users\\Makabongwe\\Desktop\\javaCode\\uct.jpg");
      img = ImageIO.read(f);
    }catch(IOException e){
      System.out.println(e);
    }
    
    //get image width and height
    int width = img.getWidth();
    int height = img.getHeight();
    System.out.println(height);
    System.out.println(width);//debug
    
    //Start algorithm
    for(int k=0; k<width; k++){
    for(int t=0; t<height; t++)
    for(int i=0; i<3; i++){
      for(int j=0; j<3; j++){
        int p = img.getRGB(i,j);
        //System.out.println(p);
        //get Alpha
        int a = (p>>24) & 0xff;
      System.out.println("alpha "+a);
    //get red
    int r = (p>>16) & 0xff;
      System.out.println("red "+r);
    //get green
    int g = (p>>8) & 0xff;
       System.out.println("green "+g);
    //get blue
    int b = p & 0xff;
        System.out.println("blue "+b);
       }
       System.out.println("done");
      }
     }
     
  }  
}