package cz.mm14.src;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import cz.mm14.src.menu.MapSelect;

public class MapLoader {
	static String readFile(String path, Charset encoding) 
	{
		try
		{
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return encoding.decode(ByteBuffer.wrap(encoded)).toString();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;	  
	}
	
	public static void loadMap(String name)
	{
		String map = readFile("maps/" + name, StandardCharsets.US_ASCII);
		MapMaker.createMap(map);
		MapSelect.pressed = false;
	}
	
}
