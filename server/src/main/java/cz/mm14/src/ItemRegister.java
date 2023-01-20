package cz.mm14.src;

public class ItemRegister 
{
	public static Item itemPickaxe;
	public static Item redKey;
	public static Item greenKey;
	public static Item blueKey;
	public static Item yellowKey;
	
	public static void regAll()
	{
		itemPickaxe = new ItemPickaxe(0);
		redKey = new ItemGeneric(1, "Red Key");
		blueKey= new ItemGeneric(2, "Green Key");
		greenKey = new ItemGeneric(3, "Blue Key");
		yellowKey = new ItemGeneric(4, "Yellow Key");
	}
}
