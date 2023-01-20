package cz.mm14.src;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;

public class FontRenderer 
{	
	public static char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890-+/*)([]\\<>ˇ´=^.!?:@#~&,;%".toCharArray();
	public static int charsInRow = 16;
	public static int rows = 6;
	public static Color blue = new Color(36,182,255);
	
	public static void drawString(int x, int y, String text, Object color, float size)
	{    
		glDisable(GL_TEXTURE_2D);
	    
	    glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
	    
	    //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	    
	    //Slick-util instance of the color
	    if(color instanceof Color)
	    {
	    	Color col  = (Color) color;
	    	glColor4f(col.r,col.g,col.b,col.a);
	    }
	    
	    //Hexadecimal color
	    if(color instanceof String)
	    {
	    	String col = (String) color;
	    	
	    	glColor4f
	    	(
            Integer.valueOf(col.substring(1,3),16),
            Integer.valueOf(col.substring(3,5),16),
            Integer.valueOf(col.substring(5,7),16),
            1
            );
	    }
		
		glEnable(GL_TEXTURE_2D);
		
		int textRow = 0;
		
		int totalSpacing = 0;
		
		for(int i = 0; i < text.length();i++)
		{
			int collumn = 0;
			
			int row = 0;
			
			int j = 0;
			
			if(text.charAt(i)=='\\' && text.length() > i+1) 
			{
				if(text.charAt(i+1)=='n')
				{
					i++;
					totalSpacing = (int) (0 - 20/(1/size));
					textRow += 30/(1/size);
					continue;
				}		
			}
			
			if(text.charAt(i)==' ') 
			{
			totalSpacing += 15/(1/size);
			continue;
			}	

			while(chars[j]!=text.charAt(i))
			{
			j++;
			collumn=j;
			if(j >= chars.length)
			{
				collumn=79;
				break;
			}
			}
			
			while(collumn>=16)
			{
				collumn = collumn-16;
				row++;
			}			   
			   
			   float dx = 1f/16;
			   float dy = 1f/6;

			   float u = collumn*dx;
			   float v = row*dy;	
			   
			   //Shift down for letters that go under line
			   float s = 0;
			   
			   if(text.charAt(i)=='g'||text.charAt(i)=='y'||text.charAt(i)=='p'||text.charAt(i)=='j')
			   {
				   s = 6*size;
			   }
			   
			   int spaceUppercase = (int) (15/(1/size));
			   
			   int spaceLowercase = (int) (12/(1/size));
			   
			   if(i>0)
			   {
			   if(Character.isLowerCase(text.charAt(i)))
			   {
				   totalSpacing += spaceLowercase;
			   }
			   else
			   {
				   totalSpacing += spaceUppercase;
			   }
			   }
			   
			   int charW = (int) (16/(1/size));
			   int charH = (int) (24/(1/size));
			   
			   glBindTexture(GL_TEXTURE_2D, Game.font);
				
			   glBegin(GL_QUADS);
		    	glTexCoord2d(u,v);
				glVertex2f(x+i+totalSpacing,y+s+textRow);
				
				glTexCoord2d(u,v+dy);
				glVertex2f(x+i+totalSpacing,y+charH+s+textRow);
				
				glTexCoord2d(u+dx,v+dy);
				glVertex2f(x+i+totalSpacing+charW,y+charH+s+textRow);
				
				glTexCoord2d(u+dx,v);
				glVertex2f(x+i+totalSpacing+charW,y+s+textRow);		
			 glEnd();
		}
		
		 glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_LINEAR);
	}
	
	public static void drawString(int x, int y, String text, float r, float g, float b, float a, float size)
	{    
		drawString(x,y, text, new Color(r,g,b,a), size);
	}
	
	public static int getUppercaseLettersToChar(String s,int i)
	{
		int n = 0;
		
		char[] ch = s.toCharArray();
		
		for(int j = 0;j<i;j++)
		{
		if(Character.isUpperCase(ch[j]))
		{
			n++;
		}
		}
		System.out.println(n);
		return n;
	}
}
