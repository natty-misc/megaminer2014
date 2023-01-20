package cz.mm14.src;

public class BlockRegister 
{
	public static Block stone;
	public static Block diamond;
	public static Block finish;
	public static Block start;
	public static Block pickaxePickup;
	public static Block steelBlock;
	public static Block redLock;
	public static Block greenLock;
	public static Block blueLock;
	public static Block yellowLock;
	public static Block redKey;
	public static Block greenKey;
	public static Block blueKey;
	public static Block yellowKey;
	
	public static Block amethyst;
	public static Block cobalt;
	public static Block copper;
	public static Block diamond1;
	public static Block emerald;
	public static Block gold;
	public static Block iron;
	public static Block platinum;
	public static Block ruby;
	public static Block sapphire;
	public static Block silver;
	public static Block tin;
	public static Block titanium;
	public static Block tungsten;
	public static Block zinc;

	public static void regAll()
	{
		stone = new BlockRegular("Stone", "stone", true).setMinable();
		
		finish = new BlockRegular("Finish", "finish", false);
		diamond = new BlockRegular("Diamond", "diamond", false);
		start = new BlockRegular("Start", "start", false).setGenMark("player");
		
		pickaxePickup = new BlockRegular("Pickaxe", "pickaxe", false);
		
		steelBlock = new BlockRegular("Steel", "steel", true);
		
		redLock = new BlockRegular("Red Lock", "redlock", true);
		greenLock = new BlockRegular("Green Lock", "greenlock", true);
		blueLock = new BlockRegular("Blue Lock", "bluelock", true);
		blueLock = new BlockRegular("Yellow Lock", "yellowlock", true);
		
		redKey = new BlockRegular("Red Key", "redkey", false);
		greenKey = new BlockRegular("Green Key", "greenkey", false);
		blueKey = new BlockRegular("Blue Key", "bluekey", false);
		blueKey = new BlockRegular("Yellow Key", "yellowkey", false);

		amethyst = new BlockRegular("Amethyst Ore", "amethystore", true).setMinable(); //A
		cobalt = new BlockRegular("Cobalt Ore", "cobaltore", true).setMinable(); //B
		copper = new BlockRegular("Copper Ore", "copperore", true).setMinable(); //C
		diamond1 = new BlockRegular("Diamond Ore", "diamondore", true).setMinable(); //Q
		emerald = new BlockRegular("Emerald Ore", "emeraldore", true).setMinable(); //E
		gold = new BlockRegular("Gold Ore", "goldore", true).setMinable(); //R
		iron = new BlockRegular("Iron Ore", "ironore", true).setMinable(); //G
		platinum = new BlockRegular("Platinum Ore", "platinumore", true).setMinable(); //H
		ruby = new BlockRegular("Ruby Ore", "rubyore", true).setMinable(); //I
		sapphire = new BlockRegular("Sapphire Ore", "sapphireore", true).setMinable(); //J
		silver = new BlockRegular("Silver Ore", "silverore", true).setMinable(); //K
		tin = new BlockRegular("Tin Ore", "tinore", true).setMinable(); //L
		titanium = new BlockRegular("Titanium Ore", "titaniumore", true).setMinable(); //M
		tungsten = new BlockRegular("Tungsten Ore", "tungstenore", true).setMinable(); //N
		zinc = new BlockRegular("Zinc Ore", "zincore", true).setMinable(); //T
	}
}
