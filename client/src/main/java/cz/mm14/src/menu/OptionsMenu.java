package cz.mm14.src.menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
import cz.mm14.src.ui.CheckBox;
import cz.mm14.src.ui.GameUI;

public class OptionsMenu 
{
	public static boolean mouseDown;
	
	public static boolean TOOLTIPS;
	public static boolean SHOWID;
	public static boolean RANGEINDICATOR;
	
	public static void render() throws IOException, IllegalArgumentException, IllegalAccessException, SecurityException
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)&&System.currentTimeMillis() - InGame.lastEscHit > 400)
		{
			Game.menuKey.playAsSoundEffect(1.0f, 1.0f, false);
		    MainMenu.menuIDNow = 0;
		    InGame.lastEscHit = System.currentTimeMillis();
		} 
		
	    Background.draw();

	    FontRenderer.drawString(21, 21, "Options", Color.black, 1.2f);
	    FontRenderer.drawString(20, 20, "Options", Color.green.darker(), 1.2f);
	    
	    FontRenderer.drawString(40, 70, "Enable block tooltips and smart cursor", Color.black, 0.8f);
	    
	    FontRenderer.drawString(40, 100, "Show range indicator", Color.black, 0.8f);
	    
	    FontRenderer.drawString(40, 130, "Show block ID (needs block tooltips)", Color.black, 0.8f);
	    
	    FontRenderer.drawString(5, 493, "Everything is automatically saved. To return to the main menu, press Esc.", Color.black, 0.8f);
	    
	    CheckBox.drawAll(5);
	    
	    for(CheckBox cb: CheckBox.checkboxes)
	    {
	    if(Mouse.isButtonDown(0) && cb.isInBounds(Mouse.getX(), Game.height-Mouse.getY()) && !mouseDown && cb.menu == 5)
	    {
		if(cb.checked)
		{
			mouseDown = true;
			
			cb.checked = false;
			
			try {
				OptionsMenu.class.getDeclaredField(cb.name.toUpperCase()).setBoolean(OptionsMenu.class, cb.checked);
			} catch (NoSuchFieldException e) {
				System.out.println("[ERROR] No such option: " + cb.name + ", probably wrong checkbox name.");
			}
		}
		else
		{
			mouseDown = true;
			
			cb.checked = true;
			
			try {
				OptionsMenu.class.getDeclaredField(cb.name.toUpperCase()).setBoolean(OptionsMenu.class, cb.checked);
			} catch (NoSuchFieldException e) {
				System.out.println("[ERROR] No such option: " + cb.name + ", probably wrong checkbox name.");
			}
		}
		saveConfig();
	    }
	    else
	    {
	    	if(!Mouse.isButtonDown(0))
	    	{
	    	mouseDown = false;
	    	}
	    }
	    }
	    
	    GameUI.drawCursor();
	    
		Display.update();
		Display.sync(120);
		
		SoundStore.get().poll(0);
	}
	
	private static void saveConfig() throws FileNotFoundException, UnsupportedEncodingException
	{
	    Game.menuKey.playAsSoundEffect(0.5f, 0.5f, false);
	    PrintWriter writer = new PrintWriter("config/options.txt", "UTF-8");
	    
	    for(CheckBox cb: CheckBox.checkboxes)
	    {
	    	writer.println(cb.name+"="+String.valueOf(cb.checked));
	    }		
	    
		writer.close();		
	}

	public static void loadConfig() throws IOException, IllegalArgumentException, IllegalAccessException, SecurityException
	{
		if(!new File("config/").exists())
		{	        
			new File("config").mkdir();
		}	
		
		if(!new File("config/options.txt").exists())
		{	
			PrintWriter writer = new PrintWriter("config/options.txt", "UTF-8");
			writer.println("tooltips=true");
			writer.println("rangeindicator=false");
			writer.println("showid=false");
			writer.close();
		}
		
		String line;
        BufferedReader in;
        int row = 0;        
        
        in = new BufferedReader(new FileReader("config/options.txt"));
        while((line = in.readLine())!=null)
        { 
        	row++;
        	
        	String data[] = line.split("=");
        	
        	if(Boolean.parseBoolean(data[1])!=false|true)
        	{        	
        	//Should be checkbox
			try {
				OptionsMenu.class.getDeclaredField(data[0].toUpperCase()).setBoolean(OptionsMenu.class, Boolean.parseBoolean(data[1]));
			} catch (NoSuchFieldException e) 
			{
				System.out.println("[Warning] Non-existing option in config file at line " + row);
				System.out.println(e.getLocalizedMessage());
			}
				
        	CheckBox.getByName(data[0]).checked = Boolean.parseBoolean(data[1]);
        	}
        }
        	
        in.close();
	}
}
