package cz.mm14.src.menu;

import java.io.File;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;

import cz.mm14.src.Background;
import cz.mm14.src.FontRenderer;
import cz.mm14.src.Game;
import cz.mm14.src.InGame;
import cz.mm14.src.MapLoader;
import cz.mm14.src.MapSelectButton;
import cz.mm14.src.player.Player;
import cz.mm14.src.ui.GameUI;

public class MapSelect {
	
	public static boolean pressed = false;
	static int i = 1;
	static File mapFolder;
	static ArrayList<String> names = new ArrayList<String>();
	
	public static void getMaps()
	{
	names.clear();	
		
	if(!new File("maps/").exists())
	{
		new File("maps/").mkdir();
		return;
	}
		
	mapFolder = new File("maps/"); 
	
	for(String file: mapFolder.list())
	{
		if(!file.startsWith("!-"))
		{
			names.add(file);
		}
	}
		 
	for(String map: names)
    {
    	MapSelectButton.buttons.add(new MapSelectButton(i, map));
    	i++;	
    }
    i = 1;
	}
	
	public static void renderMenu()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

	    Background.draw();
	    
	    FontRenderer.drawString(21, 21, "Singleplayer", Color.black, 1.2f);
	    FontRenderer.drawString(20, 20, "Singleplayer", Color.green.darker(), 1.2f);
	    
	    FontRenderer.drawString(185, 65, "Maps found ", Color.black,1.1f);
	    //FontRenderer.drawString(180, 100, "(max. 8 maps)", Color.black,0.8f);
	    FontRenderer.drawString(185, 95, "Select by pressing number:", Color.black,0.9f);
	    	    
	    i = 1;
	    for(final String map : names)
	    {
	    	FontRenderer.drawString(240, 123+i*28, "["+ i + "] " + map, Color.green.darker().darker(),0.9f);
	    	i++;
	    }
	    i = 0;
	    		    
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)&&System.currentTimeMillis() - InGame.lastEscHit > 400)
		{
			Game.menuKey.playAsSoundEffect(1.0f, 1.0f, false);
		    MainMenu.menuIDNow = 0;
		    InGame.lastEscHit = System.currentTimeMillis();
		} 

			for(int j=1; j<=names.size(); j++)
			{
			if(Keyboard.isKeyDown(j+1))
			{	
			if(pressed == false)
			{
			Game.multiplayer = false;
			pressed = true;
			MainMenu.menuIDNow = 3;
			InGame.diamonds = 0;
			Player.margH = 0;
			Player.margW = 0;			
			InGame.startTime = System.currentTimeMillis()/1000;
			MapLoader.loadMap(MapSelectButton.buttons.get(j-1).link);
			}
			}
		}
			
		GameUI.drawCursor();
		
		SoundStore.get().poll(0);
		
		Display.update();
		Display.sync(120);
	}
}
