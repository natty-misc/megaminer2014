package cz.mm14.src.inventory;

import cz.mm14.src.Item;

public class InvSlot 
{
	public int slot;
	public Item item;

	public InvSlot(int slot, Item item)
	{
		this.slot = slot;
		this.item = item;
	}
}
