package cz.mm14.src.block;

import cz.mm14.src.rarity.EnumRarity;

public class Blocks 
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
		
		public static void registerAll()
		{
			stone = new Block("stone").setMinable().setRarity(EnumRarity.COMMON).setTextures("stone0", "stone1", "stone2", "stone3").setRandomTexturing().setDisplayName("Stone").setGenMark("stone").setSolid(true).setDisplayName("Stone");
			
			finish = new BlockRegular("finish", "Finish", "finish", "finish", false).setRarity(EnumRarity.NORMAL); //F
			diamond = new BlockRegular("diamond", "Diamond", "diamond", "diamond", false).setRarity(EnumRarity.EPIC); //D
			start = new BlockRegular("start", "Start", "start", "player", false).setRarity(EnumRarity.NORMAL); //P
			
			pickaxePickup = new BlockRegular("pickaxe", "Pickaxe", "pickaxe", "pickaxe", false).setRarity(EnumRarity.UNCOMMON); //1
			
			steelBlock = new BlockRegular("steel", "Steel", "steel", "steel", true).setRarity(EnumRarity.COMMON); //S
			
			redLock = new BlockRegular("redlock", "Red Lock", "lockred", "redlock", true).setRarity(EnumRarity.COMMON); //2
			greenLock = new BlockRegular("greenlock" , "Green Lock", "lockgreen", "greenlock", true).setRarity(EnumRarity.COMMON); //3
			blueLock = new BlockRegular("bluelock", "Blue Lock", "lockblue", "bluelock", true).setRarity(EnumRarity.COMMON); //4
			yellowLock = new BlockRegular("yellowlock", "Yellow Lock", "lockyellow", "yellowlock", true).setRarity(EnumRarity.COMMON); //5
			
			redKey = new BlockRegular("redkey", "Red Key", "redkey", "redkey", false).setRarity(EnumRarity.RARE); //6
			greenKey = new BlockRegular("greenkey", "Green Key", "greenkey", "greenkey", false).setRarity(EnumRarity.RARE); //7
			blueKey = new BlockRegular("bluekey", "Blue Key", "bluekey", "bluekey", false).setRarity(EnumRarity.RARE); //8
			yellowKey = new BlockRegular("yellowkey", "Yellow Key", "yellowkey", "yellowkey", false).setRarity(EnumRarity.RARE); //9
			
			Ores.registerAll();
		}
}
