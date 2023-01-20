package cz.mm14.src.menu;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;

import cz.mm14.src.Background;
import cz.mm14.src.FontRenderer;
import cz.mm14.src.Game;
import cz.mm14.src.InGame;
import cz.mm14.src.ui.GameUI;

public class WinScreen {

	public static void create() 
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

	    Background.draw();
	    
	    renderImg();
	    
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) 
			{
			Game.multiplayer = false;
		     MainMenu.menuIDNow = 0;
		     InGame.lastEscHit = System.currentTimeMillis();
		     Game.menuKey.playAsSoundEffect(1.0f, 1.0f, false);
			}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{			
			Game.menuKey.playAsSoundEffect(1.0f, 1.0f, false);
			MainMenu.menuIDNow = 1;
			if(Game.multiplayer)
			{
			Game.multiplayer = false;
			MainMenu.menuIDNow = 0;
			}
		}
				
		FontRenderer.drawString(200, 280, "Your score: " + InGame.diamonds, Color.black,1);
		FontRenderer.drawString(200, 310, "Time: " + InGame.time, Color.black,1);
		FontRenderer.drawString(200, 340, "Press Return to continue...", Color.black,1);
		
		GameUI.drawCursor();
		
		Display.update();
		Display.sync(120);
		
		SoundStore.get().poll(0);		
	}
	
	public static void renderImg()
	{
		GL11.glColor3f(1, 1, 1);
		glEnable(GL_TEXTURE_2D); 
        GL11.glBindTexture(GL_TEXTURE_2D, Game.winImg);  	
        glBegin(GL_QUADS);
        
        int x = 100;
        int y = 100;
        
        GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(x,y);
		
		GL11.glTexCoord2f(1f,0);
		GL11.glVertex2f(x+600,y);
		
		GL11.glTexCoord2f(1f,1f);
		GL11.glVertex2f(x+600,y+150);
		
		GL11.glTexCoord2f(0,1f);
		GL11.glVertex2f(x,y+150);
		
        glEnd();
	}

}
