package cz.mm14.src;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import cz.mm14.src.player.Player;

public class FloorBlock 
{
	public int bid;
	public String name;
	public int x;
	public int y;
	public int id;
	public int tex;
	public int rot;
	
	static final List<FloorBlock> floorblocktypes = new ArrayList<FloorBlock>(256);

	public FloorBlock(int bid, String name, String texloc)
	{
		this.bid = bid;
		this.name = name;
		this.tex = TexLoader.load("block/"+texloc);
		
		floorblocktypes.add(this);
	}
	
    public FloorBlock(int x, int y, int id) 
    {
		Random random = new Random();
		
    	this.tex = getFloorBlockByID(id).tex;
    	
        this.x = x;
        this.y = y;
        this.id = id;
        
        this.rot = random.nextInt(4)*90;
    }

    boolean isInBounds(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + 50 && mouseY > y && mouseY < y + 50;
    }

    void update(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
	public static FloorBlock getFloorBlockByID(int id)
	{
		for(FloorBlock b: floorblocktypes)
		{
		if(b.id == id)
		return b;
		}
		return null;
	}
	
	public int getTexture()
	{
		return this.tex;
	}
	
	   public void draw() 
       {			        	
       	int x1 = x+55;
       	int y1 = y+55;
       	
       	Color.white.bind();
       	glEnable(GL_TEXTURE_2D); 
           GL11.glBindTexture(GL_TEXTURE_2D, this.getTexture());           
           
           glBegin(GL_QUADS);
           GL11.glTexCoord2f(0,0);
   		GL11.glVertex2f(x-Player.margW,y-Player.margH);
			GL11.glTexCoord2f(1f,0);
   		GL11.glVertex2f(x1-Player.margW,y-Player.margH);
			GL11.glTexCoord2f(1f,1f);
   		GL11.glVertex2f(x1-Player.margW,y1-Player.margH);
   		GL11.glTexCoord2f(0,1f);
   		GL11.glVertex2f(x-Player.margW,y1-Player.margH);
           glEnd();
       }
}
