package cz.mm14.src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.opengl.Texture;

public class TexLoader
{
	static Texture tex;
	public static int load(String texName)
	{
		return loadTexture(loadImage(texName+".png"));
	}
	
	public static void setup()
    {
	    glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
	
	private static final int BYTES_PER_PIXEL = 4;
    public static int loadTexture(BufferedImage image){
    	GL11.glEnable(GL_TEXTURE_2D);
    	
       int[] pixels = new int[image.getWidth() * image.getHeight()];
         image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

         ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL);

         for(int y = 0; y < image.getHeight(); y++){
             for(int x = 0; x < image.getWidth(); x++){
                 int pixel = pixels[y * image.getWidth() + x];
                 buffer.put((byte) ((pixel >> 16) & 0xFF));     
                 buffer.put((byte) ((pixel >> 8) & 0xFF));     
                 buffer.put((byte) (pixel & 0xFF));             
                 buffer.put((byte) ((pixel >> 24) & 0xFF));  
             }
         }

         buffer.flip(); 

       int textureID = glGenTextures();
       
       glBindTexture(GL_TEXTURE_2D, textureID);

       glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
       glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
       
       glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
       
       glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
       
              
       glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
       
       return textureID;
    }

    public static BufferedImage loadImage(String loc)
    {    	       	
    	if(new File("assets/"+loc).exists())
    	{		
         try {
        	 System.out.println("Loaded: "+new File("assets/"+loc).getAbsolutePath());
            return ImageIO.read(new File("assets/"+loc));
         } catch (IOException e) {
             e.printStackTrace();
         }
    	}
    	else
    	{    	
    	System.out.println("Texture not found: assets/"+loc+", trying to use missingTex instead.");
		  
		  try {
			  return ImageIO.read(new File("assets/missingTex.png"));
		  	} 
		  catch (IOException e1) {
			  System.out.println("Texture not found: assets/missingTex.png");
			  e1.printStackTrace();
		  	}
    	}
		return null;
    }
}
