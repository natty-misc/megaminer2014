package cz.mm14.src;

import java.util.ListIterator;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import cz.mm14.src.block.Blocks;
import cz.mm14.src.menu.MainMenu;
import cz.mm14.src.player.Player;
import cz.mm14.src.player.PlayerTick;
import cz.mm14.src.ui.GameUI;
import cz.mm14.world.BlockInWorld;
import cz.mm14.world.World;

public class InGame
{		    
	public static long lastEscHit;

	public static long startTime;
	
	public static int frames;
	
	public static int framesToShow = 1;
	
	public static String time;
	
	public static long lastMeas = System.currentTimeMillis()/1000;

	public static int diamonds;

	public static boolean willRender;
	
	public static Player playerSP;
		    
	public static void render() throws LWJGLException
	{
	playerSP = Player.getControlledPlayer();
	
	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

    Background.drawGame();
    
    //Background.drawMap();

	if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) 
	{
	 Game.menu = true;
     MainMenu.menuIDNow = 1;
     lastEscHit = System.currentTimeMillis();
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
	
	playerSP.draw();
	
	PlayerTick.doPlayerWork(InGame.playerSP);
	
    if(World.getBlockAt(playerSP.x + 20, playerSP.y + 20) == Blocks.finish)
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
	
	if(playerSP.y>Game.height-145 && (GameUI.opened || (!GameUI.opened && GameUI.working)))	
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
	playerSP.renderInv();
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
}
