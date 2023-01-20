package cz.mm14.src.network;

import cz.mm14.src.Game;
import cz.mm14.src.Player;
import cz.mm14.src.world.BlockInWorld;
import cz.mm14.src.world.World;

public class Data 
{
	static String s = "";
	
	public static String getBlockInitialData()
	{		
		s = "";
		for(BlockInWorld b : World.blockData)
		{				
			s = s.concat("Block:"+b.x+","+b.y+","+b.block.genMark+";");
		
		}
		
		return s;
	}
	
	public static String getBlockDestroyData(BlockInWorld block)
	{		
		s = "";
		
		BlockInWorld b = block;
		
		s = s.concat("BlockDestroy:"+b.x+","+b.y+";");
				
		return s;
	}
	
	
	public static String getPlayerData(long l)
	{
		s = "";
		
		for(Player p : Game.players)
		{
			boolean j = false;
			
			if(p.id == l)
			{
				j = true;
			}
			
			p.sent.add(l);
			
			s = s.concat("Player:"+p.x+","+p.y+","+p.id+","+p.dir+","+p.moving+","+String.valueOf(j)+";");
		}
		
		return s;
	}
	
	public static String getPlayerMData(long l)
	{
		s = "";
		
		for(Player p : Game.players)
		{			
			if(p.id == l)
			{
				continue;
			}
			
			s = s.concat("PlayerM:"+p.x+","+p.y+","+p.id+";");
		}
		
		return s;
	}
	
	public static String getUnsentPlayerData(long l)
	{
		s = "";
		
		for(Player p : Game.players)
		{
			boolean j = false;
			
			if(p.sentTo(l)) continue;
			
			if(p.id == l)
			{
				j = true;
			}
			
			p.sent.add(l);
			
			s = s.concat("PlayerN:"+p.x+","+p.y+","+p.id+","+p.dir+","+p.moving+","+String.valueOf(j)+";");
		}
		
		return s;
	}
}
