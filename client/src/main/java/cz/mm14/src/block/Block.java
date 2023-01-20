package cz.mm14.src.block;

import static org.lwjgl.opengl.GL11.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.mm14.src.TexLoader;
import cz.mm14.src.player.Player;
import cz.mm14.src.rarity.EnumRarity;

public class Block {
		public int tex;
		
		public boolean hasMoreTextures = false;
		
		public int[] secTex = {};
		
		static Random random;
				
		public String id;
		static final List<Block> blocks = new ArrayList<Block>(256);
		
		public String name;

		public String genMark;
		
		public boolean solid;
		public boolean minable;
		
		public EnumRarity rarity;

		public boolean randomTexturing;

		public Block(String bid)
		{
			this.id = bid;
			
			this.rarity = EnumRarity.NORMAL;
			
			blocks.add(this);
		}
		
		public Block setMinable()
        {
        	this.minable = true;
			return this;
        }
		
		public Block setSolid(boolean solid)
        {
        	this.solid = solid;
			return this;
        }
		
		public Block setGenMark(String genMark)
        {
        	this.genMark = genMark;
			return this;
        }
        
        public Block setRarity(EnumRarity rarity)
        {
        	this.rarity = rarity;
			return this;
        }
        
        public EnumRarity getRarity()
        {
        	return this.rarity;
        }
        
        public Block setDisplayName(String name) 
		{
			this.name = name;
			
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
			return null;
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
    	
    	public int getTexture()
    	{    		
    		random = new Random(this.hashCode());
    		
    		if(hasMoreTextures)
    		{
    		return secTex[0];
    		}
    		
    		return tex;
    	}
    	
    	public Block setTextures(String... texloc)
    	{
    		if(texloc.length>1)
			{
			hasMoreTextures = true;
			
			secTex = new int[texloc.length];
			
			for(int i=0; i<texloc.length;i++)
			{
				secTex[i] = TexLoader.load("block/"+texloc[i]);
			}
			
			}
			else
			{
				this.tex = TexLoader.load("block/"+texloc[0]);
			}
    		
    		return this;
    	}
    	
    	public void draw(int x, int y)
    	{
    		draw(x,y,this.getTexture());
    	}
    	    	
        public void draw(int x, int y, int texture) 
        {	  		
        	int x1 = x - Player.margW;
        	int y1 = y - Player.margH;
        	
        	glTranslatef(x1, y1, 0);
            
        	glColor4f(1f,1f,1f,1f);            
            
        	glBindTexture(GL_TEXTURE_2D, texture);
        	
            glBegin(GL_QUADS);            
        	
            glTexCoord2f(0,0);
    		glVertex2f(0,0);
    		glTexCoord2f(0,1);
    		glVertex2f(0,50);
			glTexCoord2f(1,1);
    		glVertex2f(50,50);
			glTexCoord2f(1,0);
    		glVertex2f(50,0);
            glEnd();
            
            glTranslatef(-x1, -y1, 0);
        }

		public Block setRandomTexturing() 
		{
			this.randomTexturing = true;
			
			return this;
		}

		public void drawRandTexturing(int x, int y, int seed) 
		{
			draw(x,y,secTex[new Random(seed).nextInt(secTex.length)]);
		}
}
