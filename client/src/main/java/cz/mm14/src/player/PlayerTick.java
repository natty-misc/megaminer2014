package cz.mm14.src.player;

import java.util.Iterator;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cz.mm14.src.Game;
import cz.mm14.src.InGame;
import cz.mm14.src.Item;
import cz.mm14.src.block.Block;
import cz.mm14.src.block.Blocks;
import cz.mm14.src.inventory.InvSlot;
import cz.mm14.src.item.Items;
import cz.mm14.world.World;

public class PlayerTick 
{
	public static long delta;

	private static long lastTime;
	
	private static long getTime() 
	{		
		return Sys.getTime();
	}
	
	private static void getDelta() 
	{
		long currentTime = getTime();
		int ddelta = (int)(currentTime - lastTime);
		lastTime = getTime();
		
		delta = ddelta;
	}
	
	public static void doPlayerWork(Player p)
	{		
		getDelta();
		
		boolean result = false;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) | Keyboard.isKeyDown(Keyboard.KEY_D) ) 
		{			
	  		 p.dir = 2;
	    	 p.checkPlayerMovement((int) (p.x+1*delta), p.y);
	    	 if(p.canMoveRight)
	    	 {
	    	if(p.x > Game.width*0.7)
	    	{
	    		result = p.tryToMoveScreen(0);
	    	}
	    	if(!result)
	    	{
	     	p.update(0.4f * delta, 0);
	    	}
	    	 }
	     }
		else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) | Keyboard.isKeyDown(Keyboard.KEY_A))
		{	
	  		 p.dir = 0;
	    	 p.checkPlayerMovement((int) (p.x-1*delta), p.y);
	    	 if(p.canMoveLeft)
	    	 {
	    		 if(p.x < Game.width*0.3)
	 	    	{
	    			 result = p.tryToMoveScreen(2);
	 	    	}
	    		 if(!result)
	    		{  		 
	     	p.update(-0.4f * delta, 0);
	    		}
	    	 }
	     } 
		else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) | Keyboard.isKeyDown(Keyboard.KEY_S)) 
		{
	  		 p.dir = 1;
	    	 p.checkPlayerMovement(p.x, (int) (p.y+1*delta));
	    	 if(p.canMoveDown)
	    	 {
	    		 if(p.y > Game.height*0.7)
		 	    	{
	    			 result = p.tryToMoveScreen(3);
		 	    	}
	    		 if(!result)
		    		{
	     	p.update(0, 0.4f * delta);
		    		}
	    	 }
	     } 
		else if (Keyboard.isKeyDown(Keyboard.KEY_UP) | Keyboard.isKeyDown(Keyboard.KEY_W)) 
	     {	
	  		 p.dir = 3;
	    	 p.checkPlayerMovement(p.x, (int) (p.y-1*delta));
	    	 if(p.canMoveUp)
	    	 {
	    		 if(p.y < Game.height*0.3)
		 	    	{
	    			 result = p.tryToMoveScreen(1);
		 	    	}
		    		if(!result)
		    		{	 
	     	p.update(0, -0.4f * delta);
		    		}
	    	 }
	     }
	    		
	     if(World.getBlockAt(p.x + 20, p.y + 20) == Blocks.diamond)
	     {
	    	 World.destroyBlock(p.x + 20, p.y + 20);
	    	 InGame.diamonds++;
	    	 Game.diamondPickup.playAsSoundEffect(1f, 1f, false);
	     }
	     
	     if(World.getBlockAt(p.x + 20, p.y + 20) == Blocks.pickaxePickup)
	     {
	    	 World.destroyBlock(p.x + 20, p.y + 20);
	    	 p.inv.add(new InvSlot(p.inv.size(),Items.pickaxe));
	    	 Game.pickup.playAsSoundEffect(1f, 1f, false);
	     }
	     
	     
	    if(Mouse.isButtonDown(0))
	    {
	    	boolean c = false;
	    	
	    	Iterator<InvSlot> iterator = p.inv.iterator();
	    	
	    	while(iterator.hasNext()) 
	    	{
	    		if(iterator.next().item==Items.pickaxe)
	    		{
	    			c = true;
	    			
	    			break;
	    		}
			}
			
			if(c == true)
			{
					double range = 120;
					
					double mouseDistX = 1;
					double mouseDistY = 1;
										
					mouseDistX = p.x+25 - Mouse.getX();
					mouseDistY = p.y+25 - (Game.height - Mouse.getY());
				 
	    			if(Math.sqrt(Math.pow(mouseDistX, 2)+Math.pow(mouseDistY, 2))<range)
	    			{
	    				if(World.getBlockAt(Mouse.getX(), Game.height-Mouse.getY())!=null)
	    				{
	    					if(World.getBlockAt(Mouse.getX(), Game.height-Mouse.getY()).minable)
	    					{
	    					World.destroyBlock(Mouse.getX(), Game.height-Mouse.getY());
	    					Game.mine.playAsSoundEffect(1f, 1f, false);
	    					}
	    				}
	    			}
			}
	    }
	    
	    Block[] keyblocks = {Blocks.redKey,Blocks.blueKey,Blocks.greenKey,Blocks.yellowKey};
	    Item[] keys = {Items.redKey,Items.blueKey,Items.greenKey,Items.yellowKey};
	    Block[] locks = {Blocks.redLock,Blocks.blueLock,Blocks.greenLock,Blocks.yellowLock};
	    
	    for(int a = 0; a<=3;a++)
	    {	    	
	    	if(World.getBlockAt(p.x + 20, p.y + 20) == keyblocks[a])
		    {
		    	World.destroyBlock(p.x + 20, p.y + 20);
		    	p.inv.add(new InvSlot(p.inv.size(),keys[a]));
		    	Game.pickup.playAsSoundEffect(1f, 1f, false);
		    }
	    	
	    	boolean hasKey = false;
	    	InvSlot slot = null;
			for(InvSlot i : p.inv)
			{
			if (i.item == keys[a])
			{
				hasKey = true;
				slot = i;
			}
			}
			
			if(hasKey == true)
			{
				if(World.getBlockAt(p.x+60, p.y+20)==locks[a])
				{
					World.destroyBlock(p.x+60, p.y+20);
					Game.unlock.playAsSoundEffect(1f, 1f, false);
					p.inv.remove(slot);
					p.sortInv();
				}
	    		
	    		if(World.getBlockAt(p.x, p.y+20)==locks[a])
				{
	    			World.destroyBlock(p.x, p.y+20);
					Game.unlock.playAsSoundEffect(1f, 1f, false);
					p.inv.remove(slot);
					p.sortInv();
				}
	    		
	    		if(World.getBlockAt(p.x+20, p.y) == locks[a])
				{
	    			World.destroyBlock(p.x+20, p.y);
					Game.unlock.playAsSoundEffect(1f, 1f, false);
					p.inv.remove(slot);
					p.sortInv();
				}
	    		
	    		if(World.getBlockAt(p.x+20, p.y+60) == locks[a])
				{
	    			World.destroyBlock(p.x+20, p.y+60);
					Game.unlock.playAsSoundEffect(1f, 1f, false);
					p.inv.remove(slot);
					p.sortInv();
				}	    	    
			}
	    }	
	}
}
