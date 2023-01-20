package cz.mm14.src;

import java.awt.event.*;
import java.io.*;


public class PlayB implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{	
		Updater.delB.setEnabled(false);
		Updater.dlB.setEnabled(false);
		Updater.progressBar.setVisible(false);;
		Updater.isGameRunning = false;
		if(Updater.p!=null)
		{
		Updater.p.destroy();
		}
		
		try {
			Updater.runGame();
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
	}
	
	public static void toConsole(BufferedReader reader) throws IOException
	{

    String line;
    if ((line = reader.readLine()) != null)
    {
    Updater.out.append(line+"\n");
    }
	}	
}
