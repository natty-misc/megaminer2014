package cz.mm14.src.rarity;

import org.newdawn.slick.Color;

public enum EnumRarity 
{	
	COMMON(Color.lightGray,0),
	NORMAL(Color.white,1),
	UNCOMMON(Color.yellow,2),
	RARE(Color.blue,3),
	EPIC(Color.cyan,4),
	SUPERIOR(Color.orange,5),
	LEGENDARY(Color.magenta,6);
	
	Color color;
	int tier;
	
	EnumRarity(Color color, int tier)
	{
		this.color = color;
		this.tier = tier;
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public int getTier()
	{
		return this.tier;
	}
}
