package cz.mm14.src;

import java.util.ArrayList;
import java.util.List;

public class Block {
		public String id;
		static final List<Block> blocks = new ArrayList<Block>(256);
	
		public String name;

		public String genMark;
		
        public boolean selected = false;
		public boolean solid;
		public boolean c;
		public boolean minable;

		//Block
		public Block(String id)
		{
			this.id = id;
			blocks.add(this);
		}
        
        public Block setMinable()
        {
        	this.minable = true;
			return this;
        }

        public static Block getBlockByGenMark(String code)
        {
        	for(Block b :blocks)
        	{
        		if(b.genMark.startsWith(code)&&b.genMark.endsWith(code))
        		{
        			return b;
        		}
        	}
			return blocks.get(0);
        }
        
    	public static Block getBlockByID(String id)
    	{
    		for(Block b: blocks)
    		{
    		if(b.id == id)
    		return b;
    		}
    		return null;
    	}
}
