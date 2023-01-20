package cz.mm14.src.item;

import cz.mm14.src.Item;
import cz.mm14.src.TexLoader;

public class ItemPickaxe extends Item
{
	public ItemPickaxe() 
	{
		super("pickaxe");
		this.tex = TexLoader.load("block/pickaxe");
		this.setItemName("Pickaxe");
	}
}
