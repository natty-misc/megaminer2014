package cz.mm14.src;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class DeleteTask 
{
	public static void work(Updater updater) 
	{
		Updater.progressBar.setVisible(true);
		
		Updater.out.setText("Deleting old game data...\n");
		
		Updater.progressBar.setValue(0);
		
		if(new File("md5s").exists())
		{
			new File("md5s").delete();
		}
		
		if(new File("Game/").isDirectory() && new File("Game/").list().length > 0)
		{
			ArrayList<File> files = new ArrayList<File>(Arrays.asList(new File("Game/").listFiles()));
			
			for(int i = 0; i < files.size(); i++)
			{
				if(files.get(i).getName().startsWith("maps"))
				{
					continue;
				}
				
				if(files.get(i).isDirectory())
				{
					files.addAll(new ArrayList<File>(Arrays.asList(files.get(i).listFiles())));
				
					if(files.get(i).listFiles().length == 0)
					{
						try 	
						{
							Files.deleteIfExists(files.get(i).toPath());
						}
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
					else
					{					
						files.add(files.get(i));
					}
				}
				else
				{
					try 	
					{
						Files.deleteIfExists(files.get(i).toPath());
					}
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
		else
		{
			Updater.out.setText("The Game folder doesn't exist or it's empty, skipping. \n");
		}
		
		Updater.playB.setEnabled(false);
		Updater.playB.setVisible(true);
		
		Updater.exitB.setEnabled(true);
		Updater.exitB.setVisible(true);
		
		Updater.dlB.setEnabled(true);
		Updater.dlB.setVisible(true);
		Updater.dlB.setText("Download");
		
        Updater.delB.setVisible(true);
        Updater.delB.setEnabled(false);
		
        Updater.progressBar.setVisible(false);
        updater.setCursor(Cursor.getDefaultCursor());
        
        Updater.progressBar.setValue(100);
        
        Updater.out.append("Done!");
	}
}
