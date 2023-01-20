package cz.mm14.src.item;

import cz.mm14.src.Item;
import cz.mm14.src.TexLoader;

public class ItemGeneric extends Item
{

		public ItemGeneric(String id, String tex, String name) 
		{
			super(id);
			this.tex = TexLoader.load(tex);
			this.setItemName(name);
		}
}
