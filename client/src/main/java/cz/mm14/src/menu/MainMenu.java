package cz.mm14.src.menu;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;

import cz.mm14.src.Background;
import cz.mm14.src.FontRenderer;
import cz.mm14.src.Game;
import cz.mm14.src.InGame;
import cz.mm14.src.VersionChecker;
import cz.mm14.src.ui.ButtonClickable;
import cz.mm14.src.ui.GameUI;

public class MainMenu 
{
	public static int menuIDNow = 0;	
	static float alpha = 0;
	
	static Random random = new Random();
	
	static ArrayList<String> splashes;
	
	static boolean loaded;
	
	private static String splashNow = "!!replace!!";
	private static int x = 0;
	private static boolean mouseDown;
	
	public static void renderMenu()
	{		
		if(alpha<1)
		{
			alpha += 0.01;
		}
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

	    Background.drawMenu();
	    renderLogo(alpha);
	    
	    renderStripes();
	    
	    ButtonClickable.buttonclickable.get(0).draw(alpha);
	    
	    ButtonClickable.buttonclickable.get(1).draw(alpha);
	    
	    ButtonClickable.buttonclickable.get(2).draw(alpha);
	    
	    ButtonClickable.buttonclickable.get(3).draw(alpha);
	    
	    if(loaded)
	    	if(splashes.size()>0)
	    	{
	    			if(0-splashNow.length()*11>Game.width-x || splashNow == "!!replace!!")
	    			{
	    				x=0;
	    				
	    				String splashBefore = splashNow;
	    				
	    				splashNow = splashes.get(random.nextInt(splashes.size()));
	    				
	    				if(splashBefore==splashNow)
	    				{
	    					splashNow = splashes.get(random.nextInt(splashes.size()));
	    				}
	    			}
	    			
	    			x++;
	    		
	    			FontRenderer.drawString(Game.width-x, 2, splashNow,"#000000",0.8f);
	    	}
	    		
		FontRenderer.drawString(Game.width-300, Game.height-25, "Your version: " + Game.version, new Color(0,0,0,alpha),0.87f);
		
		FontRenderer.drawString(Game.width-300, Game.height-50, "Latest version: " + VersionChecker.latest, new Color(0,0,0,alpha),0.87f);
		
		if(ButtonClickable.buttonclickable.get(0).isInBounds(Mouse.getX(), Game.height - Mouse.getY()))
		{
			FontRenderer.drawString(305, 322, "Singleplayer", new Color(255,0,0) ,0.9f);
		}
		else
		{
			FontRenderer.drawString(305, 322, "Singleplayer", new Color(0,0,0,alpha),0.9f);
		}
		
		if(ButtonClickable.buttonclickable.get(1).isInBounds(Mouse.getX(), Game.height - Mouse.getY()))
		{
			FontRenderer.drawString(305, 357, "Multiplayer", new Color(255,0,0),0.9f);
		}
		else
		{
			FontRenderer.drawString(305, 357, "Multiplayer", new Color(0,0,0,alpha),0.9f);
		}
		
		if(ButtonClickable.buttonclickable.get(2).isInBounds(Mouse.getX(), Game.height - Mouse.getY()))
		{
			FontRenderer.drawString(305, 392, "Options", new Color(255,0,0),0.9f);
		}
		else
		{
			FontRenderer.drawString(305, 392, "Options", new Color(0,0,0,alpha),0.9f);
		}
		
		if(ButtonClickable.buttonclickable.get(3).isInBounds(Mouse.getX(), Game.height - Mouse.getY()))
		{
			FontRenderer.drawString(305, 427, "Exit", new Color(255,0,0),0.9f);
		}
		else
		{
			FontRenderer.drawString(305, 427, "Exit", new Color(0,0,0,alpha),0.9f);
		}
				
		FontRenderer.drawString(5, Game.height-25, "Copyright Natty", new Color(0,0,0,alpha),0.87f);
	    
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)&&System.currentTimeMillis() - InGame.lastEscHit > 400)
		{
		    Display.destroy();
		    System.exit(0);
		} 
		
		if(!mouseDown && Mouse.isButtonDown(0))
		{		
		mouseDown = true;	
			
		if (Mouse.isButtonDown(0)&&ButtonClickable.buttonclickable.get(3).isInBounds(Mouse.getX(), Game.height - Mouse.getY()))
		{
			System.out.println("Closing game...");
		    Display.destroy();
		    System.exit(0);
		} 
		
		if (Mouse.isButtonDown(0)&&ButtonClickable.buttonclickable.get(0).isInBounds(Mouse.getX(), Game.height - Mouse.getY()))
		{
			Game.menuKey.playAsSoundEffect(1.0f, 1.0f, false);
			menuIDNow = 1;
		} 
		
		if (Mouse.isButtonDown(0)&&ButtonClickable.buttonclickable.get(1).isInBounds(Mouse.getX(), Game.height - Mouse.getY()))
		{
			Game.menuKey.playAsSoundEffect(1.0f, 1.0f, false);
			MultiPlayerMenu.textfield = "";
			menuIDNow = 4;
		} 
		
		if (Mouse.isButtonDown(0)&&ButtonClickable.buttonclickable.get(2).isInBounds(Mouse.getX(), Game.height - Mouse.getY()))
		{
			Game.menuKey.playAsSoundEffect(1.0f, 1.0f, false);
			menuIDNow = 5;
		} 
		}
		else if(!Mouse.isButtonDown(0))
		{
			mouseDown = false;
		}
		
		GameUI.drawCursor();
		
		Display.update();
		Display.sync(60);
		
		SoundStore.get().poll(0);
	}
	
	static void renderStripes()
	{
		GL11.glColor4f(0f,0.6f,1f,0.7f);
		glDisable(GL_TEXTURE_2D); 
        glBegin(GL_QUADS);
		glVertex2f(0,0);
		glVertex2f(Game.width,0);
		glVertex2f(Game.width,25);
		glVertex2f(0,25);
		
		glBegin(GL_QUADS);
		glVertex2f(0,Game.height-55);
		glVertex2f(Game.width,Game.height-55);
		glVertex2f(Game.width,Game.height);
		glVertex2f(0,Game.height);
        glEnd();
	}
	
	public static void renderLogo(float a)
	{
		GL11.glTranslatef(Game.width/2-400,0,0);
		
		GL11.glColor4f(1,1,1,a);
		glEnable(GL_TEXTURE_2D); 
        glBindTexture(GL_TEXTURE_2D, Game.logo); 	
        glBegin(GL_QUADS);
        glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1f,0);
		glVertex2f(800,0);
		glTexCoord2f(1f,1f);
		glVertex2f(800,320);
		glTexCoord2f(0,1f);
		glVertex2f(0,320);
        glEnd();

		GL11.glTranslatef(-(Game.width/2-400),0,0);
	}
	
	public static void loadSplashes()
	{
		splashes = new ArrayList<String>();
		
		if(new File("assets/splashtext.txt").exists())
		{	
			
		String line;
        BufferedReader in;

        try 
        {
			in = new BufferedReader(new FileReader("assets/splashtext.txt"));
			while((line = in.readLine())!=null)
	        {
	        	splashes.add(line);
	        }
	        	
	        in.close();
	        
	        loaded = true;
			
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
}
