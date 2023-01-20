package cz.mm14.src.inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory
{
	public List<InvSlot> inventory = new ArrayList<InvSlot>(16);
	public String owner;
				
	public Inventory(String player)
	{
		this.owner = player;
	}
	
	public void sort()
	{
		for(int i = 0; i<this.inventory.size(); i++)
		{
		this.inventory.get(i).slot = i;
		}
	}
}
