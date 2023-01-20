package cz.mm14.src.player;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import cz.mm14.src.Game;
import cz.mm14.src.MapMaker;
import cz.mm14.src.inventory.InvSlot;
import cz.mm14.src.item.Items;
import cz.mm14.src.menu.OptionsMenu;
import cz.mm14.src.ui.GameUI;
import cz.mm14.world.World;

public class Player {
	
    public int x, y;
	public static int margW = 0;
	public static int margH = 0;
    public boolean canMoveUp;
	public boolean canMoveDown;
    public boolean canMoveLeft;
	public boolean canMoveRight;
	public int dir;
	public long id;
	public ArrayList<InvSlot> inv = new ArrayList<InvSlot>(9);
	public boolean isControlled;

	public Player setControlled(boolean isControlled) {
		this.isControlled = isControlled;
		
		return this;
	}

	public Player(int x, int y) {
        this.x = x;
        this.y = y;
        if(!Game.multiplayer)
        {
        	this.isControlled = true;
        }
    }
    
    public void update(double d, double dy) {
        x += d;
        y += dy;
    }
    
	public void renderInv()
	{
		for(InvSlot i : this.inv)
		{
			i.item.drawAt(22 + i.slot*55, (int) (Game.height-69 + GameUI.margD));
		}
	}
	
	public void sortInv()
	{
		for(int i = 0; i<this.inv.size(); i++)
		{
		this.inv.get(i).slot = i;
		}
	}
	
	public Player setId(long i)
	{
		this.id = i;
		return this;
	}
    
    public void checkPlayerMovement(int px, int py)
    {
    	// up - 3, right - 2, down - 1, left - 0
    	canMoveDown = true;
    	canMoveRight = true;
    	canMoveUp = true;
    	canMoveLeft = true;    	

    	
    		if(px<0)
    		{
    			canMoveLeft = false;
    		}
    		if(py<0)	
    		{
    			canMoveUp = false;
    		}
    		if(px+50>Game.width)
    		{
    			canMoveRight = false;
    		}
    		if(py+50>Game.height)	
    		{
    			canMoveDown = false;
    		}
    	
    	
    	if(this.canPlayerMoveInDir(dir) == false)
    	{
    		if(dir==2)
    		{
    			canMoveRight = false;
    		}
    		if(dir==0)	
    		{
    			canMoveLeft = false;
    		}    		
    		if(dir==1)
    		{
    			canMoveDown = false;
    		}
    		if(dir==3)	
    		{
    			canMoveUp = false;
    		}
    	}
    }
    
    public boolean canPlayerMoveInDir(int dir)
    {    	
    	if((World.getSolidBlockAt(x+40,y+2) != null || World.getSolidBlockAt(x+8,y+2)!= null)&& dir == 3)
    	{    		
    		return false;
    	}
    	
    	if((World.getSolidBlockAt(x+8, y+48) != null || World.getSolidBlockAt(x+40,y+48)!= null) && dir == 1)
    	{
		return false;
    	}
    	
    	if((World.getSolidBlockAt(x+48, y+8) != null || World.getSolidBlockAt(x+48,y+40)!= null)&& dir == 2)
    	{
		return false;
    	}
    	
    	if((World.getSolidBlockAt(x+2, y+8) != null || World.getSolidBlockAt(x+2,y+40)!= null)&& dir == 0 )
    	{
		return false;
    	}
    	
    	return true;
    }
            
    public void draw() 
    {    	    	
    	if(this.isControlled && OptionsMenu.RANGEINDICATOR)
    	{
    		renderRangeIndicator();
    	}
    	
    	glPushMatrix();
    	GL11.glDisable(GL_TEXTURE_2D);
    	if(this.isControlled && Game.multiplayer)
    	{
            Color.green.bind();
    	}
    	else if(!Game.multiplayer)
    	{
            new Color(0,180,200).bind();
    	}
    	else
    	{
    		Color.red.bind();
    	}
    	
    	glTranslatef(this.x+25,this.y+25,0);
    	
    	glRotatef(this.getAngle(), 0, 0, 1 );
    	
        glBegin(GL11.GL_POLYGON);
        
        glVertex2f(- 8, 0);
        glVertex2f(- 15, - 15);
        glVertex2f(+ 15, 0);
        glVertex2f(- 15, + 15);
        
        glEnd();
        
        glTranslatef(-this.x-25,-this.y-25,0);
        
        glPopMatrix();
    }
    
    public void renderRangeIndicator() 
    {
    	boolean c = false;
		for(InvSlot i : inv)
		{
		if (i.item == Items.pickaxe)
		{
			c = true;
		}
		}
		
		if(c)
		{
    	//new Color(50,50,255).bind();
		glColor4f(0, 1, 0, 0.70f);
		}
		else
		{
		glColor4f(1, 0, 0, 0.70f);
		}
				
    	glEnable(GL_TEXTURE_2D);
    	
    	glTranslatef(this.x+25,this.y+25,0);
    	
    	GL11.glBindTexture(GL_TEXTURE_2D, Game.rangeIndicator);
    	
        glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0,0);
        glVertex2f(-120, -120);
        GL11.glTexCoord2f(1f,0);
        glVertex2f(120, -120);
        GL11.glTexCoord2f(1f,1f);
        glVertex2f(120, 120);
        GL11.glTexCoord2f(0,1f);
        glVertex2f(-120, 120);
        
        glEnd();
        
        glTranslatef(-this.x-25,-this.y-25,0);
        
        glDisable(GL_TEXTURE_2D);
	}

	public float getAngle()
    {
    	switch(dir)
    	{
    	case 0: return 180f;
    	case 1: return 90;
    	case 2: return 0f;
    	case 3: return 270f;
    	}
    	
    	return 0;
    }
    
    public void drawAt(int u, int v) {
    	GL11.glDisable(GL_TEXTURE_2D);
    	if(this.isControlled)
    	{
            Color.green.bind();
    	}
    	else
    	{
            Color.red.bind();
    	}
        glBegin(GL_QUADS);
        glVertex2f(u + 10, v + 10);
        glVertex2f(u + 40, v + 10);
        glVertex2f(u + 40, v + 40);
        glVertex2f(u + 10, v + 40);
        glEnd();
    }

	public boolean tryToMoveScreen(int direc)
	{
	    if(direc == 0)
		{
			if(MapMaker.mapW*55+18>margW+Game.width)
			{											
			margW +=  0.4*PlayerTick.delta;
						
			return true;
			}
		}
		
		else if(direc == 3)
		{
			if(MapMaker.mapH*55+18>margH+Game.height)
			{							
			margH +=  0.4*PlayerTick.delta;
						
			return true;
			}
		}
		
		else if(direc == 1)
		{			
			if(margH>0)
			{						
			margH -=  0.4*PlayerTick.delta;
			
			return true;
			}
		}
		
		else if(direc == 2)
		{
			if(margW>0)
			{				
					margW -=  0.4*PlayerTick.delta;
					
					return true;
				}
		}
					
		return false;
	}

	public static Player getControlledPlayer() 
	{
		if(!Game.multiplayer)
		{
		for(Player p: Game.players)
		{
			if(p.isControlled)
			{
				return p;
			}
		}
		}
		else
		{
			return Game.players.get(0);
		}
		
		return null;
	}

	public static Player getPlayerById(long i) 
	{
	    	for(Player p : Game.players)
	    	{
	    		if(p.id == i)
	    		{
	    		return p;
	    		}
	    	}
	    	
	    	return null;
	}
}