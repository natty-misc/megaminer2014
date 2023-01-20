package cz.mm14.world;

import java.util.ArrayList;
import java.util.List;

import cz.mm14.src.block.Block;
import cz.mm14.src.player.Player;

public class World 
{
	public static final List<BlockInWorld> blockData = new ArrayList<BlockInWorld>();
	
	public static Block getBlockAt(int x, int y)
	{
		x = x + Player.margW;
    	y = y + Player.margH;
    	
    	for(final BlockInWorld block : blockData)
    	{ 	
        if(x > block.x && x < block.x + 51 && y > block.y && y < block.y + 51) 
        {
        	return block.block;
        }	
    	}
		return null;	
	}
	
	public static Block getSolidBlockAt(int x, int y)
	{
		x = x + Player.margW;
    	y = y + Player.margH;
    	
    	for(final BlockInWorld block : blockData)
    	{ 	
        if(x > block.x && x < block.x + 51 && y > block.y && y < block.y + 51) 
        {
        	if(block.block.solid)
        	{
        	return block.block;
        	}
        }	
    	}
		return null;	
	}
		
	public static boolean destroyBlock(int x, int y)
	{
		x = x + Player.margW;
    	y = y + Player.margH;
    	
    	for(final BlockInWorld block : blockData)
    	{ 	
        if(x > block.x && x < block.x + 51 && y > block.y && y < block.y + 51) 
        {
        	blockData.remove(block);
        	return true;
        }	
    	}
		return false;	
	}

	public static void createNewBlock(int x, int y, String id) 
	{
		Block block = Block.getBlockByID(id);
		
		if(!(getBlockAt(x,y) instanceof Block))
		{
		blockData.add(new BlockInWorld(x,y,block));
		}
	}
}