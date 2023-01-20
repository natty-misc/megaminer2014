package cz.mm14.src;

import org.lwjgl.opengl.Display;

import cz.mm14.src.block.Block;
import cz.mm14.src.player.Player;
import cz.mm14.world.World;


public class MapMaker {
	public static int mapW;
	public static int mapH;

	public static void createMap(String map) 
	{
		World.blockData.clear();
		Game.floorBlocks.clear();
		Game.players.clear();
		
		map = map.trim();
		
		String[] mapData;
		
		String[] dataField;
		
		mapData = map.split("]");
		
		mapData[0] = mapData[0].trim();
		
		mapData[1] = mapData[1].trim();
		
		mapData[0] = mapData[0].substring(1);
		
		dataField = mapData[0].split(":");	
		
		String[] row = mapData[1].split("//");
		
		Display.setTitle("Currently playing: "+dataField[2]);
		
		for(int i = 0; i<row.length; i++)
		{
		row[i] = row[i].trim();
		}
		
		String[] genMark;
		
		mapH = Integer.parseInt(dataField[1]);
		mapW = Integer.parseInt(dataField[0]);
		
		for(int j = 0; j < mapH; j++)
		{
		genMark = row[j].split(">");
	
		for(int i = 0; i < mapW; i++)
		{
			genMark[i] = genMark[i].substring(1);
			genMark[i] = genMark[i].trim();
						
			if(genMark[i].equals("0"))
			{
				Game.floorBlocks.add(new FloorBlock(i*55 + 5, 5 + j*55,0));
			}
			else
			{
			if(genMark[i].equals("player"))
			{				
				Game.floorBlocks.add(new FloorBlock(i*55 + 5, 5 + j*55,0));
				World.createNewBlock(i*55 + 10, 10 + j*55, Block.getBlockByGenMark(genMark[i]).id);
				Game.players.add(new Player(i*55 + 10, 10 + j*55));
			}
			else
			{				
				Game.floorBlocks.add(new FloorBlock(i*55 + 5, 5 + j*55,0));
				
				World.createNewBlock(i*55 + 10, 10 + j*55, Block.getBlockByGenMark(genMark[i]).id);
			}
			}
		}
		}
		
		Game.menu = false;
	}
}
