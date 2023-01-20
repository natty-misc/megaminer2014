package cz.mm14.src.menu;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;

import cz.mm14.src.Background;
import cz.mm14.src.FontRenderer;
import cz.mm14.src.ui.GameUI;

public class LoadingScreen
{
	public static void render()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

	    Background.draw();

	    FontRenderer.drawString(240, 200, "Please wait...", Color.green.darker().darker(),1.2f);

	    GameUI.drawCursor();
	    
		Display.update();
		
		SoundStore.get().poll(0);
	}
}
