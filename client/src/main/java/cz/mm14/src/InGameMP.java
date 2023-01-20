package cz.mm14.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ListIterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import cz.mm14.src.block.Blocks;
import cz.mm14.src.menu.MainMenu;
import cz.mm14.src.network.PacketTranslater;
import cz.mm14.src.player.Player;
import cz.mm14.src.player.PlayerTick;
import cz.mm14.src.ui.GameUI;
import cz.mm14.world.BlockInWorld;
import cz.mm14.world.World;

public class InGameMP {

	static Player player;
	public static Socket socket;
	public static String ip;
	public static long startTime;
	public static int frames;
	public static int framesToShow;
	public static String time;
	public static long lastMeas = System.currentTimeMillis()/1000;
	public static int diamonds;
	public static boolean shouldRender;

	static void doMyStuff() throws IOException
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
		 socket.close();
		 Game.menu = true;
	     MainMenu.menuIDNow = 4;
	     Game.isCycling = false;
	     InGame.lastEscHit = System.currentTimeMillis();
	     Game.menuKey.playAsSoundEffect(1.0f, 1.0f, false);
		}       
	                      
		player = Player.getControlledPlayer();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

	    Background.drawGame();

		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) 
		{
		 Game.menu = true;
	     MainMenu.menuIDNow = 1;
	     InGame.lastEscHit = System.currentTimeMillis();
	     Game.menuKey.playAsSoundEffect(1.0f, 1.0f, false);
	     
	     Game.resetWindowName();
	     
		 GameUI.margD = (GameUI.opened? 0 : 110);
		 GameUI.working = false;
		}       	
		
		ListIterator<FloorBlock> itfb = Game.floorBlocks.listIterator();
			
		while(itfb.hasNext()) 
		{
		FloorBlock b = itfb.next();
		
		if(b.x - Player.margW < Game.width + 55 && b.y - Player.margH < Game.height + 55 && b.x - Player.margW > -55 && b.y - Player.margH > -55)
		{
			b.draw();
		}
		}

		ListIterator<BlockInWorld> itb = World.blockData.listIterator();
		while(itb.hasNext())
		{
		BlockInWorld b = itb.next();
		
		//ONLY DEBUG
		//System.out.println(b.toString());
		
		if(b.x - Player.margW < Game.width + 55 && b.y - Player.margH < Game.height + 55 && b.x - Player.margW > -55 && b.y - Player.margH > -55)
		{			
			if(b.block.randomTexturing)
			{
				b.block.drawRandTexturing(b.x, b.y, b.hashCode());
			}
			else
			{
				b.block.draw(b.x, b.y);
			}
		}
		}
		
		player.draw();
		
		PlayerTick.doPlayerWork(player);
		
	    if(World.getBlockAt(player.x + 20, player.y + 20) == Blocks.finish)
	    {	
	    	Game.victory.playAsSoundEffect(1f, 1f, false);	
	    	
		    MainMenu.menuIDNow = 2;
	    	Game.menu = true;
	    	Game.resetWindowName();	    	
	    }
	    	
		long systemTime = System.currentTimeMillis()/1000;
		
		String additional0 = "";
		
		if((systemTime - startTime) - (((systemTime - startTime)/60)*60)<10)
		{
		additional0 = "0";
		}
		
		time = (systemTime - startTime)/60 + ":" + additional0 + ((systemTime - startTime) - (((systemTime - startTime)/60)*60));
		
		String timer = "Time:" + time;
		
		GameUI.checkClick();
		
		GameUI.getMargD();
		
		GameUI.drawUIbg();
		
		float alpha = 1f;	
		
		float r = 0f;
		float g = 0.867f;
		float b = 1f;
		
		if(player.y>Game.height-145 && (GameUI.opened || (!GameUI.opened && GameUI.working)))	
		{
			r = 1 - (float) ((float) GameUI.margD/96);
			g = 1 - (float) GameUI.margD/902;
			b = 1f;
			alpha = (float) (0.42f + (float) GameUI.margD/96);
			//alpha = 0.2f;
		}
		
		FontRenderer.drawString(10, (int) (Game.height-138+GameUI.margD), "Open/Close", 1, 1, 1, alpha, 0.65f);
		
		FontRenderer.drawString(21, (int) (Game.height-110+GameUI.margD), timer, 0, 0, 0, alpha, 1);
			
		FontRenderer.drawString(201, (int) (Game.height-110+GameUI.margD), "FPS:"+framesToShow,  0, 0, 0, alpha,1);
		
		FontRenderer.drawString(341, (int) (Game.height-110+GameUI.margD), "Diamonds:"+ diamonds, 0, 0, 0, alpha,1);
		
		FontRenderer.drawString(20, (int) (Game.height-111+GameUI.margD), timer, r, g, b, alpha,1);
		
		FontRenderer.drawString(200, (int) (Game.height-111+GameUI.margD), "FPS:"+framesToShow,  r, g, b, alpha,1);
		
		FontRenderer.drawString(340, (int) (Game.height-111+GameUI.margD), "Diamonds:"+ diamonds,  r, g, b, alpha,1);
		
		GameUI.drawHotbar();
		player.renderInv();
		GameUI.drawGameBorder();
		//MUST BE LAST RENDERED THING
		GameUI.drawCursor();
		SoundStore.get().poll(0);	
		Display.update();
		Display.sync(120);
		
		frames++;
		
		if(systemTime - lastMeas > 1)
		{
		framesToShow = frames/2;
		frames = 0;
		lastMeas = System.currentTimeMillis()/1000;
		}
	}
	
	public static void cycle() throws IOException
	{
		try 
		{
			
	    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

	    String line;
	    
	    socket.setSoTimeout(100);
	    
	    startTime = System.currentTimeMillis()/1000;
	    
	    while (((line = br.readLine()) != null) || socket.isConnected() && !Display.isCloseRequested() && !Game.menu && Game.multiplayer) 
	    {        
	        if (line != null)
	        {
	        	PacketTranslater.translate(line);
	        }        
	        
	        if(!Game.players.isEmpty())
	        {
    		bw.write("PlayerC:"+(Player.getControlledPlayer().x+Player.margW)+","+(Player.getControlledPlayer().y+Player.margH)+","+String.valueOf(Player.getControlledPlayer().id)+";");
        	bw.flush();
        	bw.newLine();
	        }
	        
    		bw.write("KeepConnected;");
        	bw.flush();
        	bw.newLine();
        	
        	if(Game.players.size() != 0)
        	{
        	doMyStuff();
        	}
	    }
	    return;
	    } 
		catch (ConnectException e) 
		{		
			socket.close();
			System.err.println("You were disconnected: " + e.toString());
			Game.menu = true;
			MainMenu.menuIDNow = 0;
			Game.multiplayer = false;
			Game.isCycling = false;
			
			Game.resetWindowName();
		}
		catch (SocketException e) 
		{
			socket.close();
			System.err.println("You were disconnected: " + e.toString());
			Game.menu = true;
			Game.multiplayer = false;
			MainMenu.menuIDNow = 0;
			Game.isCycling = false;
			
			Game.resetWindowName();
		}
		catch (IOException e) 
		{
			socket.close();
			Game.menu = true;
			Game.multiplayer = false;
			MainMenu.menuIDNow = 0;
			Game.isCycling = false;
			
			Game.resetWindowName();
			
			e.printStackTrace();
		}
	}
}
