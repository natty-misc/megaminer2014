package cz.mm14.src.ui;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import cz.mm14.src.FontRenderer;
import cz.mm14.src.Game;
import cz.mm14.src.MapMaker;
import cz.mm14.src.block.Block;
import cz.mm14.src.menu.OptionsMenu;
import cz.mm14.src.player.Player;
import cz.mm14.world.World;

public class GameUI 
{
	public static double margD = 0;
	
	static float rot = 0;
	
	public static boolean opened = true;
	
	public static boolean working = false;
	static long lastClick = System.currentTimeMillis();
	
	static int i;

	private static boolean mouseDown;	
	
	public static void drawUIbg()
	{
		glTranslated(0, margD, 0);	
		
		float alpha = 0.5f;	
		
		if(Player.getControlledPlayer().y>Game.height-145 && (GameUI.opened || (!GameUI.opened && GameUI.working)))	
		{
			alpha = (float) (0.2f + (float) GameUI.margD/400);
		}		
			
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		glColor4f(0.1f,0.1f,0.1f,(float) (alpha*1.8));
		
		glBegin(GL_LINES);
		
		glVertex2f(0, Game.height-140);
        glVertex2f(100, Game.height-140);
		
		glVertex2f(100, Game.height-140);
        glVertex2f(100, Game.height-120);
		
        glVertex2f(100, Game.height-120);
        glVertex2f(Game.width, Game.height-120);
        
        glEnd();
		
		glColor4f(0.1f,0.1f,0.1f,alpha);
		
		glBegin(GL_QUADS);
        glVertex2f(0, Game.height-120);
        glVertex2f(Game.width, Game.height-120);
        glVertex2f(Game.width, Game.height);
        glVertex2f(0, Game.height);
        glEnd();
        
        glBegin(GL_QUADS);
        glVertex2f(0, Game.height-140);
        glVertex2f(100, Game.height-140);
        glVertex2f(100, Game.height-120);
        glVertex2f(0, Game.height-120);
        glEnd();
                
        glTranslated(0, -margD, 0);	
	}
	
	public static void drawHotbar()
	{		
	glTranslated(0, margD, 0);	
		
	float alpha = 1f;	
		
	if(Player.getControlledPlayer().y>Game.height-145 && (GameUI.opened || (!GameUI.opened && GameUI.working)))	
	{
		alpha = (float) (0.2f + (float) GameUI.margD/150);
	}		
		
	for(int i = 0; i <= 8; i++)
	{
	glDisable(GL11.GL_TEXTURE_2D);
	
	//Shadow
	glColor4f(0f, 0f, 0f, alpha);
	
    glBegin(GL_QUADS);
    glVertex2f(21 + i*55, Game.height-69);
    glVertex2f(75 + i*55, Game.height-69);
    glVertex2f(75 + i*55, Game.height-15);
    glVertex2f(21 + i*55, Game.height-15);
    glEnd();

    //Slot border
	glColor4f(0f, 0.867f, 1f, alpha);
	
    glBegin(GL_QUADS);
    glVertex2f(20 + i*55, Game.height-71);
    glVertex2f(74 + i*55, Game.height-71);
    glVertex2f(74 + i*55, Game.height-16);
    glVertex2f(20 + i*55, Game.height-16);
    glEnd();
    
    //Slot
    glColor4f(0.7f, 0.7f, 0.7f, alpha);
	
    glBegin(GL_QUADS);
    glVertex2f(22 + i*55, Game.height-69);
    glVertex2f(72 + i*55, Game.height-69);
    glVertex2f(72 + i*55, Game.height-18);
    glVertex2f(22 + i*55, Game.height-18);
    glEnd();
    glEnable(GL11.GL_TEXTURE_2D);
	}
	
	glTranslated(0, -margD, 0);	
	}
	
	public static void drawCursor()
	{		
		glPushMatrix();
		
		boolean blockExists = true;
		
		Block blockC = World.getBlockAt(Mouse.getX(), Game.height - Mouse.getY());
		
		if(blockC == null)
		{
			blockExists = false;
		}
		
		if(blockExists)
		{
		if(!Game.menu && OptionsMenu.TOOLTIPS)
		{
			FontRenderer.drawString(Mouse.getX()+26, Game.height-Mouse.getY()-24, blockC.name, Color.black, 0.95f);
			FontRenderer.drawString(Mouse.getX()+25, Game.height-Mouse.getY()-25, blockC.name, blockC.rarity.getColor(), 0.95f);
		}
		
		if(blockC.solid && !Game.menu && OptionsMenu.TOOLTIPS)
		{
		if(OptionsMenu.SHOWID)
		{
		FontRenderer.drawString(Mouse.getX()+26, Game.height-Mouse.getY()+26, "ID:"+blockC.id, Color.black, 0.95f);
		FontRenderer.drawString(Mouse.getX()+25, Game.height-Mouse.getY()+25, "ID:"+blockC.id, Color.white, 0.95f);
		}
		
		if(blockC.minable)
		{			
		FontRenderer.drawString(Mouse.getX()+26, Game.height-Mouse.getY()+1, "minable", Color.black, 0.9f);
		FontRenderer.drawString(Mouse.getX()+25, Game.height-Mouse.getY(), "minable", Color.green, 0.9f);
		Color.green.bind();
		
		rot++;
		}
		else
		{			
		rot = 0;	
			
		FontRenderer.drawString(Mouse.getX()+26, Game.height-Mouse.getY()+1, "unminable", Color.black, 0.9f);
        FontRenderer.drawString(Mouse.getX()+25, Game.height-Mouse.getY(), "unminable", Color.red, 0.9f);
		
        Color.red.bind();
		}
		}
		else
		{
		if(!Game.menu && OptionsMenu.TOOLTIPS && OptionsMenu.SHOWID)
		{
		FontRenderer.drawString(Mouse.getX()+26, Game.height-Mouse.getY()+1, "ID:"+blockC.id, Color.black, 0.9f);
		FontRenderer.drawString(Mouse.getX()+25, Game.height-Mouse.getY(), "ID:"+blockC.id, Color.white, 0.9f);
		}
		
			Color.yellow.bind();
			
			rot = 0;	
		}
		}
		else
		{
			Color.yellow.bind();
			
			rot = 0;	
		}
		
		glPushMatrix();
		glTranslatef(Mouse.getX(),Game.height-Mouse.getY(),0);
				
		glDisable(GL11.GL_TEXTURE_2D);
		
		int z = 1;
		
		if(Mouse.isButtonDown(0)||Mouse.isButtonDown(1)||Mouse.isButtonDown(2))
		{
		z = 2;
		}
		
		if(Game.menu) 
		{
		Color.white.bind();
		//z = 2;
		}
		
		glBegin(GL_QUADS);
	    glVertex2f(-1, -5*z);
	    glVertex2f(1, -5*z);
	    glVertex2f(1, 5*z);
	    glVertex2f(-1, 5*z);
	    glEnd();
		
		glBegin(GL_QUADS);
	    glVertex2f(-5*z, -1);
	    glVertex2f(5*z, -1);
	    glVertex2f(5*z, 1);
	    glVertex2f(-5*z, 1);
	    glEnd();
	    
	    GL11.glRotatef(rot,0,0,1);
	        
		glBegin(GL_QUADS);
	    glVertex2f(-15, -15);
	    glVertex2f(15, -15);
	    glVertex2f(15, -12);
	    glVertex2f(-15, -12);
	    glEnd();
	    
	    glBegin(GL_QUADS);
	    glVertex2f(-15, -15);
	    glVertex2f(-12, -15);
	    glVertex2f(-12, 15);
	    glVertex2f(-15, 15);
	    glEnd();
	    
	    glBegin(GL_QUADS);
	    glVertex2f(12, -15);
	    glVertex2f(15, -15);
	    glVertex2f(15, 15);
	    glVertex2f(12, 15);
	    glEnd();
	    
	    glBegin(GL_QUADS);
	    glVertex2f(-15, 12);
	    glVertex2f(15, 12);
	    glVertex2f(15, 15);
	    glVertex2f(-15, 15);
	    glEnd();
	   
	    GL11.glRotatef(-rot,0,0,1);
	    
	    glEnable(GL11.GL_TEXTURE_2D);
	    
	    glTranslatef(-Mouse.getX(),-(Game.height-Mouse.getY()),0);
	    glPopMatrix();
	}
	
	public static void drawGameBorder()
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		if(Player.margH>0)
		{
			new Color(0,150,255).bind();
		}
		else
		{
			new Color(0,221,255).bind();
		}
		glBegin(GL_QUADS);
        glVertex2f(0, 0);
        glVertex2f(Game.width, 0);
        glVertex2f(Game.width, 7);
        glVertex2f(0, 7);
        glEnd();
        
		if(Player.margW>0)
		{
			new Color(0,150,255).bind();
		}
		else
		{
			new Color(0,221,255).bind();
		}
        glBegin(GL_QUADS);
        glVertex2f(0, 0);
        glVertex2f(7, 0);
        glVertex2f(7, Game.height);
        glVertex2f(0, Game.height);
        glEnd();
        
		if(MapMaker.mapH*55>Player.margH+Game.height)
		{
			new Color(0,150,255).bind();
		}
		else
		{
			new Color(0,221,255).bind();
		}
        glBegin(GL_QUADS);
        glVertex2f(0, Game.height-7);
        glVertex2f(Game.width, Game.height-7);
        glVertex2f(Game.width, Game.height);
        glVertex2f(0, Game.height);
        glEnd();
        
		if(MapMaker.mapW*55>Player.margW+Game.width)
		{
			new Color(0,150,255).bind();
		}
		else
		{
			new Color(0,221,255).bind();
		}
        glBegin(GL_QUADS);
        glVertex2f(Game.width-7, 0);
        glVertex2f(Game.width, 0);
        glVertex2f(Game.width, Game.height);
        glVertex2f(Game.width-7, Game.height);
        glEnd();
	}
	
	public static void checkClick()
	{		
		boolean inBounds = false;
		
		if(!mouseDown && Mouse.isButtonDown(0))
		{		
		mouseDown = true;	
		
		if(!opened)
		{		
		if(Mouse.getX()>10 && Mouse.getX()<100 && Mouse.getY() > 10 && Mouse.getY() < 30)
		{						
			inBounds = true;
		}
		}
		else
		{			
		if(Mouse.getX()>10 && Mouse.getX()<100 && Mouse.getY() > 120 && Mouse.getY() < 140)
		{						
			inBounds = true;
		}
		}
		
		if(inBounds && !working)
		{		
			if(opened == true)
			{
				lastClick = System.currentTimeMillis();
				working = true;
				opened = false;
			}
			else if(opened == false)
			{
				lastClick = System.currentTimeMillis();
				working = true;
				opened = true;
			}
		}
		}
		else if(!Mouse.isButtonDown(0))
		{
			mouseDown = false;
		}
	}

	public static void getMargD() 
	{
		long sysT = System.currentTimeMillis();
		
		if((sysT - lastClick)<900 && working)
		{
		if(opened == false)
		{
		margD = Math.sin(Math.toRadians((sysT - lastClick)/10))*110;
		}
		if(opened == true)
		{
		margD = Math.cos(Math.toRadians((sysT - lastClick)/10))*110;
		}
		}
		else
		{
			working = false;
		}
	}
	
}
