package cz.mm14.src;

import cz.mm14.src.world.World;

public class MapMaker {
	public static int mapW;
	public static int mapH;

	public static void createMap(String map) 
	{
		map = map.trim();
		
		String[] mapData;
		
		String[] dataField;
		
		mapData = map.split("]");
		
		mapData[0] = mapData[0].trim();
		
		mapData[1] = mapData[1].trim();
		
		mapData[0] = mapData[0].substring(1);
		
		dataField = mapData[0].split(":");	
		
		String[] row = mapData[1].split("//");
		
		System.out.println();
		System.out.println("It looks that map has successfully loaded.");
		System.out.println("Map name: " + dataField[2]);
		System.out.println("Map dimensions: " + dataField[0] + " x " + dataField[1]);
				
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
						
			if(genMark[i].startsWith("0")&&genMark[i].endsWith("0"))
			{
				
			}
			else
			{
				if(genMark[i].startsWith("player")&&genMark[i].endsWith("player"))
				{				
					Game.setSpawn(i*55 + 10, 10 + j*55);
				}	
				
				World.createNewBlock(i*55 + 10, 10 + j*55, Block.getBlockByGenMark(genMark[i]).id);
			}
		}
		}
	}
}
