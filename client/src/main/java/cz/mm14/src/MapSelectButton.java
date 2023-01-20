package cz.mm14.src;

import java.util.ArrayList;
import java.util.List;

public class MapSelectButton 
{
	public static final List<MapSelectButton> buttons = new ArrayList<MapSelectButton>(16);
	public int id;
	public String link;
	
	public MapSelectButton(int id, String link)
	{
		this.id = id;
		this.link = link;
	}
}
