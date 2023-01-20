package cz.mm14.src.menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import cz.mm14.src.Background;
import cz.mm14.src.FontRenderer;
import cz.mm14.src.Game;
import cz.mm14.src.InGame;
import cz.mm14.src.InGameMP;
import cz.mm14.src.network.ServerConnecter;
import cz.mm14.src.player.Player;
import cz.mm14.src.ui.GameUI;
import cz.mm14.world.World;

public class MultiPlayerMenu
{
	public static String textfield = "";
	public static long cooldown;
	private static boolean err;
	private static String resolve;
	private static String errMess;
	private static long errTime;
	
	public static void render() 
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)&&System.currentTimeMillis() - InGame.lastEscHit > 400)
		{
			textfield = "";
			
			Game.menuKey.playAsSoundEffect(1.0f, 1.0f, false);
		    MainMenu.menuIDNow = 0;
		    InGame.lastEscHit = System.currentTimeMillis();
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{
			InGameMP.socket = ServerConnecter.join(textfield.trim());
			if(!err)
			{
			Game.menu = false;
			Game.multiplayer = true;
				
			InGame.diamonds = 0;
			Player.margH = 0;
			Player.margW = 0;
				
			InGameMP.ip = textfield;
			Display.setTitle("Currently playing: Multiplayer");
			
			Game.players.clear();
			Game.floorBlocks.clear();
			World.blockData.clear();
			}
		}
		
		if(Keyboard.next() && Keyboard.getEventKeyState() && MainMenu.menuIDNow == 4)
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)|Keyboard.isKeyDown(Keyboard.KEY_RETURN)|Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)|Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
			{
				
			}
			else
			{
				if(!Keyboard.isKeyDown(Keyboard.KEY_BACK))
				{
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)|Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
				{
					if(Keyboard.getEventKey()==Keyboard.KEY_LSHIFT||Keyboard.getEventKey()==Keyboard.KEY_RSHIFT)
					{
					
					}
					else
					{
					textfield = textfield + Character.toUpperCase(Keyboard.getEventCharacter());
					}
				}
				else
				{
					textfield = textfield + Keyboard.getEventCharacter();
				}
				}
				else
				{
					if(textfield.length()>0)
					{
						textfield = textfield.substring(0, textfield.length()-1);
					}
				}
			}
		}
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

	    Background.draw();
	    
	    FontRenderer.drawString(21, 21, "Multiplayer", Color.black, 1.2f);
	    FontRenderer.drawString(20, 20, "Multiplayer", Color.green.darker(), 1.2f);
	    
	    FontRenderer.drawString(90, 95, "Enter IP and port of a server:", Color.black,0.9f);
	    FontRenderer.drawString(90, 125, textfield, Color.blue,0.9f);
	    
	    if(err)
	    {
	    	FontRenderer.drawString(91, 271, errMess, Color.black,0.85f);
	    	FontRenderer.drawString(90, 270, errMess, Color.red,0.85f);
	    	FontRenderer.drawString(90, 295, resolve, Color.black,0.8f);
	    }
	    
	    if(errTime < System.currentTimeMillis()-20000)
	    {
	    	err = false;
	    }
	    
	    GameUI.drawCursor();
	    
	    Display.update();
	    Display.sync(120);
	}

	public static void reportErr(String errCode) 
	{
		if(errCode=="BadArgs")
		{
		 err = true;
		 errTime = System.currentTimeMillis();
		 errMess = "You have entered IP and/or port in bad format.";
		 resolve = "Right format: IP:port (for example 123.45.67.89:25565)";
		}
		
		if(errCode=="PortNotNumber")
		{
		 err = true;
		 errTime = System.currentTimeMillis();
		 errMess = "You have entered port that was not a valid number.";
		 resolve = "Port is number between 1-65535";
		}
	}

	public static void reportErrWithReason(String errCode, String reason) 
	{
		 err = true;
		 errTime = System.currentTimeMillis();
		 errMess = reason;
		 resolve = errCode;
	}
}
