package cz.mm14.src;

import static org.lwjgl.opengl.GL11.*;

public class Background
{		
    public static void draw()
    {    	   	
    	glColor3f(1,1,1);
    	glEnable(GL_TEXTURE_2D);  
    	glBindTexture(GL_TEXTURE_2D, Game.bgtex);    	    	
        glBegin(GL_QUADS);
       	glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1,0);
		glVertex2f(800*Game.getXRatio(),0);
		glTexCoord2f(1,1);
		glVertex2f(800*Game.getXRatio(),600*Game.getYRatio());
		glTexCoord2f(0,1);
		glVertex2f(0,600*Game.getYRatio());
        glEnd();
   }
    
   public static void drawMenu()
   {
       glEnable(GL_TEXTURE_2D);
       glColor3f(1,1,1);
	  	glBindTexture(GL_TEXTURE_2D, Game.menubg); 
       glBegin(GL_QUADS);
      	glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1,0);
		glVertex2f(800*Game.getXRatio(),0);
		glTexCoord2f(1,1);
		glVertex2f(800*Game.getXRatio(),600*Game.getYRatio());
		glTexCoord2f(0,1);
		glVertex2f(0,600*Game.getYRatio());
       glEnd();
   }
   
   static void drawGame()
   {
       glEnable(GL_TEXTURE_2D);
       glColor3f(1,1,1);
       glBindTexture(GL_TEXTURE_2D, Game.gamebg);    	
       glBegin(GL_QUADS);
     	glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1,0);
		glVertex2f(800*Game.getXRatio(),0);
		glTexCoord2f(1,1);
		glVertex2f(800*Game.getXRatio(),600*Game.getYRatio());
		glTexCoord2f(0,1);
		glVertex2f(0,600*Game.getYRatio());
       glEnd();
   }

   /*
public static void drawMap() 
{
	int w = Game.mapbg.getTextureWidth();
	int h = Game.mapbg.getTextureHeight();
	
	glEnable(GL_TEXTURE_2D);
		Color.white.bind();
	   Game.mapbg.bind(); 
	   glBegin(GL_QUADS);
     	glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(0.98f,0);
		glVertex2f(w*Game.getXRatio(),0);
		glTexCoord2f(0.98f,0.98f);
		glVertex2f(w*Game.getXRatio(),h*Game.getYRatio());
		glTexCoord2f(0,0.98f);
		glVertex2f(0,h*Game.getYRatio());
    glEnd();
}
*/
}
