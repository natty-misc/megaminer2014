package cz.mm14.src;

import java.util.ArrayList;

import cz.mm14.src.world.World;

public class Player {
	
    public int x, y;
    public boolean canMoveUp;
	public boolean canMoveDown;
    public boolean canMoveLeft;
	public boolean canMoveRight;
	public int dir;
	public int moving;
	public long id;
	public boolean loggedIn;
	public ArrayList<Long> sent = new ArrayList<Long>();

    Player(int x, int y, long id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
    
    void update(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    
    public void checkMove(int px, int py)
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
    		if(px+50>540)
    		{
    			canMoveRight = false;
    		}
    		if(py+50>460)	
    		{
    			canMoveDown = false;
    		}
    	
    	
    	if(this.canPlayerMoveInDir(this.dir) == false)
    	{
    		if(this.dir==2)
    		{
    			canMoveRight = false;
    		}
    		if(this.dir==0)	
    		{
    			canMoveLeft = false;
    		}    		
    		if(this.dir==1)
    		{
    			canMoveDown = false;
    		}
    		if(this.dir==3)	
    		{
    			canMoveUp = false;
    		}
    	}
    }
    
    public boolean canPlayerMoveInDir(int dir)
    {
    	int px = this.x;
    	int py = this.y;
    	    	
    	if((World.getSolidBlockAt(px+40,py+4) != null || World.getSolidBlockAt(px+8,py+4) != null)&& dir == 3)
    	{
		return false;
    	}
    	
    	if((World.getSolidBlockAt(px+8, py+46) != null || World.getSolidBlockAt(px+40,py+46) != null) && dir == 1)
    	{
		return false;
    	}
    	
    	if((World.getSolidBlockAt(px+46, py+8) != null || World.getSolidBlockAt(px+46,py+40) != null)&& dir == 2)
    	{
		return false;
    	}
    	
    	if((World.getSolidBlockAt(px+4, py+8) != null || World.getSolidBlockAt(px+4,py+40) != null)&& dir == 0 )
    	{
		return false;
    	}
    	
    	return true;
    }
    
    public static Player getPlayer(int i, int j, long l)
    {    	 
    	if(Game.players.indexOf(new Player(i,j,l))==-1) return null;
    	
    	return Game.players.get(Game.players.indexOf(new Player(i,j,l)));
    }
    
    public static Player getPlayerByID(long l)
    {    	
    	for(Player p : Game.players)
    	{
    		if(p.id == l)
    		{
    		return p;
    		}
    	}
    	
    	return null;
    }
    
    public boolean sentTo(long i)
    {
    	for(long l : this.sent)
    	{
    		if(l == i)
    		{
    			return true;
    		}
    	}
    	
		return false;
    }
}