package cz.mm14.src;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MapLoader {
	static String readFile(String path, Charset encoding) 
	{
		if(new File(path).exists() && !new File(path).isDirectory())
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
		}
		else
		{
			System.out.println("Map not found: " + path+ ". \nPlease, try it again.");
			Game.ask();
		}
		return null;	  
	}
	
	static String loadMap(String name)
	{
		String map = readFile("maps/" + name, StandardCharsets.US_ASCII);
		return map;
	}
	
}
