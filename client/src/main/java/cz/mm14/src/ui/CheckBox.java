package cz.mm14.src.ui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;

import org.newdawn.slick.Color;

public class CheckBox 
{
	public static ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
	
	public int x;
	public int y;
	public int id;
	
	public String name;

	public int menu;
	
	public boolean checked;

	public CheckBox(int x, int y,int id,int menu)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		this.menu = menu;
		
		checkboxes.add(this);
	}
	
	public CheckBox setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public static CheckBox getByName(String n)
	{
		for(CheckBox c:checkboxes)
		{
		if(c.name.startsWith(n))
		{
		return c;
		}
		}
		
		return null;
	}
	
	public static CheckBox get(int id)
	{
		for(CheckBox c:checkboxes)
		{
		if(c.id == id)
		{
		return c;
		}
		}
		
		return null;
	}
	
	public static void drawAll(int menu)
	{
		for(CheckBox c:checkboxes)
		{
		if(c.menu == menu)
		{
			c.draw();
		}
		}
	}
	
	public boolean isInBounds(int mouseX, int mouseY) {
        return (mouseX > this.x-14) && (mouseX < this.x+14) && (mouseY > this.y-14) && (mouseY < this.y+14);
    }
	
	//340,80
	
	public void draw()
	{
	    glDisable(GL_TEXTURE_2D);
	    
	    glTranslatef(x, y, 0);
	    
	    if(checked)
	    {
	    	Color.green.bind();	
	    }  
	    else
	    {
	    	Color.red.bind();
	    }
	    
	    glBegin(GL_QUADS);
	    glVertex2f(-14, -14);
	    glVertex2f(14, -14);
	    glVertex2f(14, 14);
	    glVertex2f(-14, 14);
	    glEnd();
	    	    
	    Color.black.bind();
		
	    if(checked)
	    {
	    glBegin(GL_QUADS);
	    glVertex2f(-11, -2);
	    glVertex2f(-8, -2);
	    glVertex2f(1, 8);
	    glVertex2f(-2, 8);
	    glEnd();

	    glBegin(GL_QUADS);
	    glVertex2f(8, -10);
	    glVertex2f(11, -10);
	    glVertex2f(1, 8);
	    glVertex2f(-2, 8);
	    glEnd();
	    }
	    
	    glBegin(GL_QUADS);
	    glVertex2f(-14, -14);
	    glVertex2f(14, -14);
	    glVertex2f(14, -12);
	    glVertex2f(-14, -12);
	    glEnd();

	    glBegin(GL_QUADS);
	    glVertex2f(-14, -14);
	    glVertex2f(-12, -14);
	    glVertex2f(-12, 14);
	    glVertex2f(-14, 14);
	    glEnd();

	    glBegin(GL_QUADS);
	    glVertex2f(12, -14);
	    glVertex2f(14, -14);
	    glVertex2f(14, 14);
	    glVertex2f(12, 14);
	    glEnd();

	    glBegin(GL_QUADS);
	    glVertex2f(-14, 12);
	    glVertex2f(14, 12);
	    glVertex2f(14, 14);
	    glVertex2f(-14, 14);
	    glEnd();
	    
	    glTranslatef(-x, -y, 0);
	}
}
