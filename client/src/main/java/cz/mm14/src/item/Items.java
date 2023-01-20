package cz.mm14.src.item;

import cz.mm14.src.Item;

public class Items 
{
	public static Item pickaxe;
	public static Item redKey;
	public static Item greenKey;
	public static Item blueKey;
	public static Item yellowKey;
	
	public static void registerAll()
	{
		pickaxe = new ItemPickaxe();
		redKey = new ItemGeneric("redKey", "block/redkey", "Red Key");
		blueKey= new ItemGeneric("blueKey", "block/bluekey", "Blue Key");
		greenKey = new ItemGeneric("greenKey", "block/greenkey", "Green Key");
		yellowKey = new ItemGeneric("yellowKey", "block/yellowkey", "Yellow Key");
	}
}
