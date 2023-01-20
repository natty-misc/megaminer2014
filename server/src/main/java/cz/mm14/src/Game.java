package cz.mm14.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Game {	
	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static int spawnX;
	public static int spawnY;
	
	public static final List<Block> blocks = new ArrayList<Block>(256);
	public static final List<Player> players = new ArrayList<Player>(8);
	
	public static final List<String> clients = new ArrayList<String>(50);

	public static void main(String[] args)
	{
    	System.out.println("=========|| Welcome to MegaMiner2014! ||=========");
    	System.out.println("[INFO] Early Beta! Expect bugs!");
    	System.out.println("[INFO] Starting server...");
    	System.out.println("=========|| Loading... ||=========");
    	Core.load();
    	System.out.println("=========|| Loading done. ||=========");
    	ask();
	}
	
	public static void ask()
	{
		blocks.clear();
		
    	System.out.println("Enter name of the map:");
    	System.out.print(">");
        	
    	String s = "dummy";
    	
        try 
        {
			s = reader.readLine();
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
         
    	System.out.println("You have selected a map, trying to load it.");
    	String map = MapLoader.loadMap(s);
    	MapMaker.createMap(map);
    	System.out.println("Done.");

		Core.runGameLoop();
	}

	public static void setSpawn(int i, int j) 
	{
		spawnX = i;
		spawnY = j;
	}
}
