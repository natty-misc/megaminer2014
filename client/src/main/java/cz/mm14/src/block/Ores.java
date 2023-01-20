package cz.mm14.src.block;

import cz.mm14.src.rarity.EnumRarity;

public class Ores 
{
	public static Block amethyst;
	public static Block cobalt;
	public static Block copper;
	public static Block diamond;
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
	public static Block coal;
	
	public static void registerAll() 
	{
		amethyst = new BlockRegular("amethystore", "Amethyst Ore", "ores/amethyst", "amethystore", true).setMinable().setRarity(EnumRarity.RARE);
		cobalt = new BlockRegular("cobaltore", "Cobalt Ore", "ores/cobalt", "cobaltore", true).setMinable().setRarity(EnumRarity.EPIC);
		copper = new BlockRegular("copperore", "Copper Ore", "ores/copper", "copperore", true).setMinable().setRarity(EnumRarity.NORMAL);
		diamond = new BlockRegular("diamondore", "Diamond Ore", "ores/diamond", "diamondore", true).setMinable().setRarity(EnumRarity.SUPERIOR);
		emerald = new BlockRegular("emeraldore", "Emerald Ore", "ores/emerald", "emeraldore", true).setMinable().setRarity(EnumRarity.SUPERIOR);
		gold = new BlockRegular("goldore", "Gold Ore", "ores/gold", "goldore", true).setMinable().setRarity(EnumRarity.RARE);
		iron = new BlockRegular("ironore", "Iron Ore", "ores/iron", "ironore", true).setMinable().setRarity(EnumRarity.NORMAL);
		platinum = new BlockRegular("platinumore", "Platinum Ore", "ores/platinum", "platinumore", true).setMinable().setRarity(EnumRarity.RARE);
		ruby = new BlockRegular("rubyore", "Ruby Ore", "ores/ruby", "rubyore", true).setMinable().setRarity(EnumRarity.SUPERIOR);
		sapphire = new BlockRegular("sapphireore", "Sapphire Ore", "ores/sapphire", "sapphireore", true).setMinable().setRarity(EnumRarity.RARE);
		silver = new BlockRegular("silverore", "Silver Ore", "ores/silver", "silverore", true).setMinable().setRarity(EnumRarity.UNCOMMON);
		tin = new BlockRegular("tinore", "Tin Ore", "ores/tin", "tinore", true).setMinable().setRarity(EnumRarity.NORMAL);
		titanium = new BlockRegular("titaniumore", "Titanium Ore", "ores/titanium", "titaniumore", true).setMinable().setRarity(EnumRarity.RARE);
		tungsten = new BlockRegular("tungstenore", "Tungsten Ore", "ores/tungsten", "tungstenore", true).setMinable().setRarity(EnumRarity.EPIC);
		zinc = new BlockRegular("zincore", "Zinc Ore", "ores/zinc", "zincore", true).setMinable().setRarity(EnumRarity.NORMAL);
		coal = new BlockRegular("coal", "Coal Ore", "ores/coal", "coal", true).setMinable().setRarity(EnumRarity.NORMAL);		
	}
}
