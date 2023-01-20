package cz.mm14.src.network;

import cz.mm14.src.Game;
import cz.mm14.src.MapMaker;
import cz.mm14.src.block.Block;
import cz.mm14.src.player.Player;
import cz.mm14.world.World;

public class PacketTranslater 
{
	public static void translate(String line) 
	{		
		String[] packet = line.split(";");
		
		for(int i = 0; i < packet.length; i++)
		{
			
		if(packet[i].startsWith("Block:"))
		{
			packet[i] = packet[i].substring(6);
			String[] dataField = packet[i].split(",");
			
			System.out.println(dataField[2]);
			
			World.createNewBlock(Integer.parseInt(dataField[0]), Integer.parseInt(dataField[1]), Block.getBlockByGenMark(dataField[2]).id);
		}
		
		if(packet[i].startsWith("Player:"))
		{			
			packet[i] = packet[i].substring(7);
			String[] dataField = packet[i].split(",");
			
			Game.players.add(new Player(Integer.parseInt(dataField[0]), Integer.parseInt(dataField[1])).setControlled(Boolean.parseBoolean(dataField[5])).setId(Long.parseLong(dataField[2])));
		}
		
		if(packet[i].startsWith("PlayerN:"))
		{
			packet[i] = packet[i].substring(8);
			String[] dataField = packet[i].split(",");
			
			Game.players.add(new Player(Integer.parseInt(dataField[0]), Integer.parseInt(dataField[1])).setControlled(Boolean.parseBoolean(dataField[5])).setId(Long.parseLong(dataField[2])));
		}
		
		if(packet[i].startsWith("Map:"))
		{
			packet[i] = packet[i].substring(4);
			String[] dataField = packet[i].split(",");
			
			MapMaker.mapW = Integer.parseInt(dataField[0]);
			
			MapMaker.mapH = Integer.parseInt(dataField[1]);
		}
		
		if(packet[i].startsWith("PlayerM:"))
		{
			packet[i] = packet[i].substring(8);
			String[] dataField = packet[i].split(",");
			
			Player p1 = Player.getPlayerById(Long.parseLong(dataField[2]));
			
			if(p1 == null) continue;
			
			p1.x = Integer.parseInt(dataField[0]);
			p1.y = Integer.parseInt(dataField[1]);
		}
		
		}
	}

}
