package cz.mm14.src;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

public class Item 
{
	public int tex;
	static final List<Item> items = new ArrayList<Item>(256);
	
	public String id;
	public String name;

	public Item(String id)
	{
		this.id = id;
		items.add(this);
	}
	
	public static Item getItemByID(String id)
	{
		for(Item i : items)
		{
		if(i.id == id)
		return i;
		}
		return null;
	}
	
	public Item setItemName(String name)
	{
		this.name = name;
		return this;
	}
	
	public int getTexture()
	{
		return this.tex;
	}
	
	public void drawAt(int x, int y)
	{
    	glColor3f(1,1,1);
    	glEnable(GL_TEXTURE_2D); 
        GL11.glBindTexture(GL_TEXTURE_2D, this.getTexture());     	
        glBegin(GL_QUADS);
        GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(x,y);
		GL11.glTexCoord2f(1F,0);
		GL11.glVertex2f(x+50,y);
		GL11.glTexCoord2f(1F,1F);
		GL11.glVertex2f(x+50,y+50);
		GL11.glTexCoord2f(0,1F);
		GL11.glVertex2f(x,y+50);
        glEnd();
	}
}
