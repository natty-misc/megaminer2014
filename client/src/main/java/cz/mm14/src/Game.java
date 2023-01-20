package cz.mm14.src;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.opengl.ImageIOImageData;

import cz.mm14.src.block.Blocks;
import cz.mm14.src.block.floor.FloorBlockRegister;
import cz.mm14.src.item.Items;
import cz.mm14.src.menu.LoadingScreen;
import cz.mm14.src.menu.MainMenu;
import cz.mm14.src.menu.MapSelect;
import cz.mm14.src.menu.MultiPlayerMenu;
import cz.mm14.src.menu.OptionsMenu;
import cz.mm14.src.menu.WinScreen;
import cz.mm14.src.modloading.LoadMods;
import cz.mm14.src.player.Player;
import cz.mm14.src.ui.ButtonClickable;
import cz.mm14.src.ui.CheckBox;

public class Game
{		
	public static List<FloorBlock> floorBlocks = new ArrayList<FloorBlock>(256);
	public static List<Player> players = new ArrayList<Player>(8);
	
	public static Audio unlock;
    public static int side;
	public static int bgtex;
	public static int logo;
	public static int winImg;
	public static String version = "Beta 1";
	public static int menubg;
	public static Audio menuKey;
	public static Audio diamondPickup;
	public static Audio pickup;
	public static Audio victory;
	public static Audio mine;
	public static int gamebg;
	public static int arrows;
	
	public static int rangeIndicator;
	
	public static boolean isCycling = false;

	public static int font;
	public static boolean multiplayer;
	
	public static int width;
	public static int height;
		
    public static void main(String[] args) throws InterruptedException{ 
    	try {
    		
    	System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
    	     
    	System.out.println("=========|| Welcome to MegaMiner2014! ||=========");
    	System.out.println("[INFO] Warning! You are in beta version " + version + ".");
    	System.out.println("[INFO] You might encounter bugs.");
		
            Display.setDisplayMode(new DisplayMode(800, 600));
            
            width = 800;
            height = 600;
            
            Display.setInitialBackground(0.7f, 0.7f, 0.7f);
            
            ByteBuffer[] iconByteBuffer = new ByteBuffer[2];
            iconByteBuffer[0] = new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("assets/icons/icon16.png")), false, false, null);
            iconByteBuffer[1] = new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("assets/icons/icon32.png")), false, false, null);
            Display.setIcon(iconByteBuffer);
            
			resetWindowName();
            Display.create();
            Display.setResizable(true);
            
            TexLoader.setup();
            
            glMatrixMode(GL_PROJECTION);
            glOrtho(0, 800, 600, 0, 1, -1);
            glMatrixMode(GL_MODELVIEW);
    		
            
		gamebg = TexLoader.load("gamebg");
				
		menubg = TexLoader.load("background");
        bgtex = TexLoader.load("background0");
        
        arrows = TexLoader.load("gui/arrows");	
		font = TexLoader.load("gui/font");	
						
		Background.drawGame();
		FontRenderer.drawString(310, 260, "Loading...", Color.white, 1.2f);
		FontRenderer.drawString(200, 300, "Loading mods...", Color.white, 1f);
		Display.update();
		
		LoadMods.collectMods();
    	LoadMods.loadAll();
    	LoadMods.initAll();
    	
		Background.drawGame();
		FontRenderer.drawString(310, 260, "Loading...", Color.white, 1.2f);
		FontRenderer.drawString(200, 300, "Loading sound and textures...", Color.white, 1f);
		Display.update();
		
        logo = TexLoader.load("gui/logo");
        winImg = TexLoader.load("gui/win");
        
        rangeIndicator = TexLoader.load("gui/rangeindicator");
        
        menuKey = Sound.loadWav("sound/key2");
        mine = Sound.loadWav("sound/mine");
        unlock = Sound.loadWav("sound/unlock");
        diamondPickup = Sound.loadWav("sound/diamondpickup");
        pickup = Sound.loadWav("sound/pickup");
        victory = Sound.loadWav("sound/win");	
        

		new CheckBox(480, 80, 0, 5).setName("tooltips");
		
		new CheckBox(370, 110, 1, 5).setName("rangeindicator");
		
		new CheckBox(470, 140, 2, 5).setName("showid");
		
		Background.drawGame();
		FontRenderer.drawString(310, 260, "Loading...", Color.white, 1.2f);
		FontRenderer.drawString(200, 300, "Loading config...", Color.white, 1f);
		Display.update();
		
		OptionsMenu.loadConfig();
		
		Background.drawGame();
		FontRenderer.drawString(310, 260, "Loading...", Color.white, 1.2f);
		FontRenderer.drawString(200, 300, "Loading random things...", Color.white, 1f);
		Display.update();
		
		MainMenu.loadSplashes();
    	
    	Background.drawGame();
		FontRenderer.drawString(310, 260, "Loading...", Color.white, 1.2f);
		FontRenderer.drawString(200, 300, "Loading blocks and items...", Color.white, 1f);
		Display.update();
    	  
		Items.registerAll();
		Blocks.registerAll();
		FloorBlockRegister.registerAll();
		
		Background.drawGame();
		FontRenderer.drawString(310, 260, "Loading...", Color.white, 1.2f);
		FontRenderer.drawString(200, 300, "Looking for version file...", Color.white, 1f);
		Display.update();
		
    	VersionChecker.downloadVer();

    	VersionChecker.check();
    	
		initGame();
        
    	}
    	catch (LWJGLException | IOException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) 
    	{
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
    }

        public static void initGame() throws IOException, LWJGLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
        {                
        	Cursor emptyCursor = null;
        	
        	emptyCursor = new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null);
        	Mouse.setNativeCursor(emptyCursor);
        	
            ButtonClickable.regButton(300, 320, 480, 345, 0);
                        
            ButtonClickable.regButton(300, 355, 480, 380, 0);
            
            ButtonClickable.regButton(300, 390, 480, 415, 0);
            
            ButtonClickable.regButton(300, 425, 480, 450, 0);
                        
        	Display.update();
                    
            while(!Display.isCloseRequested())
            {
                if (Display.wasResized())
                {
                    width = Math.max(Display.getWidth(), 1) ;
                    height = Math.max(Display.getHeight(), 1);
                    
                    System.out.println("[INFO] Resized to: " + width + "x" + height);

                    GL11.glViewport(0, 0, width, height);
                    GL11.glLoadIdentity();
                    GL11.glMatrixMode(GL11.GL_PROJECTION);
                    GL11.glLoadIdentity();
                    GL11.glOrtho(0.0f, width, height, 0.0f, 1.0f, -1.0f);
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glLoadIdentity();
                }
                
            	if(Game.menu)
            	{
            	if(MainMenu.menuIDNow == 0)
            	{
            	MainMenu.renderMenu();	
            	}
            	if(MainMenu.menuIDNow == 1)
            	{
            	MapSelect.getMaps();
            	MapSelect.renderMenu();
            	}
            	if(MainMenu.menuIDNow == 2)
            	{
            	WinScreen.create();
            	}
            	if(MainMenu.menuIDNow == 3)
            	{
            	LoadingScreen.render();
            	}
            	if(MainMenu.menuIDNow == 4)
            	{
            	MultiPlayerMenu.render();
            	}
            	if(MainMenu.menuIDNow == 5)
            	{
            	OptionsMenu.render();
            	}
            	}
            	else
            	{
				if(multiplayer)
            	{
					if(!isCycling)
					{
					isCycling = true;
            		InGameMP.cycle();
					}
            	}
            	else
            	{
					InGame.render();
            	}
            	}
            }
         Display.destroy();
     }
     
        public static void resetWindowName()
        {
        	Display.setTitle("MegaMiner v. " + version);
        }
        
        public static float getXRatio()
        {
        	return Math.max((float) width/800, 1);
        }
        
        public static float getYRatio()
        {
        	return Math.max((float) height/600, 1);
        }

		public static boolean menu = true; 
 }