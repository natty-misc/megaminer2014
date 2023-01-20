package cz.mm14.src.ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

public class ButtonClickable
{
	public int xEnd;
	public int yEnd;
	public int x;
	public int y;
	public int menu;
	
	public static final List<ButtonClickable> buttonclickable = new ArrayList<ButtonClickable>(16);
	
	public ButtonClickable(int x, int y, int xEnd, int yEnd, int menu)
	{
		this.x = x;
		this.xEnd = xEnd;
		this.y = y;
		this.yEnd = yEnd;
		this.menu = menu;
	}
	
	public static void regButton(int x, int y, int x1, int y1, int menu)
	{
	buttonclickable.add(new ButtonClickable(x, y, x1, y1, menu));
	}
	
    public boolean isInBounds(int xCoord, int yCoord) {
        return (xCoord > this.x) && (xCoord < this.xEnd) && (yCoord > this.y) && (yCoord < this.yEnd);
    }
    
    public void draw(float a)
    {
	GL11.glDisable(GL11.GL_TEXTURE_2D);    
	
    GL11.glBegin(GL11.GL_QUADS);
    
    GL11.glColor4f(0f,0f,0f,a);
    
	GL11.glVertex2f(this.x-4,this.y-4);
	GL11.glVertex2f(this.xEnd+4,this.y-4);
	GL11.glVertex2f(this.xEnd+4,this.yEnd+4);
	GL11.glVertex2f(this.x-4,this.yEnd+4);
    
	GL11.glColor4f(0.14f,0.71f,1f,a);
    
	GL11.glVertex2f(this.x-3,this.y-3);
	GL11.glVertex2f(this.xEnd+3,this.y-3);
	GL11.glVertex2f(this.xEnd+3,this.yEnd+3);
	GL11.glVertex2f(this.x-3,this.yEnd+3);
    
    GL11.glColor4f(0.7f,0.7f,0.7f,a);
    
    GL11.glVertex2f(this.x,this.y);
	GL11.glVertex2f(this.xEnd,this.y);
	GL11.glVertex2f(this.xEnd,this.yEnd);
	GL11.glVertex2f(this.x,this.yEnd);  
	
	
    GL11.glEnd();
    }
}
