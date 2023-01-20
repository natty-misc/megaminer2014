package cz.mm14.src;

import java.util.ArrayList;
import java.util.List;

public class Item 
{
	static final List<Item> items = new ArrayList<Item>(256);
	
	public int id;
	public String name;

	public Item(int id)
	{
		this.id = id;
		items.add(this);
	}
	
	public static Item getItemByID(int id)
	{
		for(Item i : items)
		{
		if(i.id == id)
		return i;
		}
		return null;
	}
	
	public Item setItemName(String name)
	{
		this.name = name;
		return this;
	}
}
