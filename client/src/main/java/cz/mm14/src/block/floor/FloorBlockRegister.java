package cz.mm14.src.block.floor;

import cz.mm14.src.FloorBlock;

public class FloorBlockRegister 
{
	public static FloorBlock stone;
	
	public static void registerAll()
	{
		stone = new FloorBlock(0, "Stone", "stone");
	}
}
