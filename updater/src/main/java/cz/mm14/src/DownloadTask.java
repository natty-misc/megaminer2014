package cz.mm14.src;

import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DownloadTask
{
	static boolean success;
	
	public static String[] libs = 
		{
		"natives/windows/jinput-dx8.dll",
		"natives/windows/jinput-dx8_64.dll",
		"natives/windows/jinput-raw.dll",
		"natives/windows/jinput-raw_64.dll",
		"natives/windows/lwjgl.dll",
		"natives/windows/lwjgl64.dll",
		"natives/windows/OpenAL32.dll",
		"natives/windows/OpenAL64.dll",
		"lib/lwjgl.jar",
		"lib/slick-util.jar"
		};
	
	static HashMap<String, String> hashesLocal = new HashMap<String, String>();
	
	static HashMap<String, String> hashesLatest = new HashMap<String, String>();
	
	static ArrayList<String> assetsToDL = new ArrayList<String>();

	public static void work(Updater updater)
	{
    	Updater.out.append("Starting updating...\n");
    		
		hashesLocal.clear();
		hashesLatest.clear();
		assetsToDL.clear();
		
		boolean nomd5s = false;
		
		Updater.progressBar.setVisible(true);
		
		int progress = 0;
		
		Updater.out.setText("");
		
        Updater.progressBar.setValue(0);
        
    		ArrayList<String> files = new ArrayList<String>();
    		
    		for(int i = 0; i < libs.length; i++)
    		{    			
    			if(!new File("Game/"+libs[i]).exists())
    			{
    				files.add(libs[i]);
    			}
    		}
    		
    		files.add("MegaMiner2014.jar");    		
    		
    		try {
    		Updater.out.append("Deleting old game data...\n");
    		    		
    		if(!Files.exists(Paths.get("Game/")))
    		{
    			new File("Game/").mkdir();
    		}    		
    		    		
    		Updater.progressBar.setIndeterminate(true);
    		
    		Updater.out.append("Connecting to the server and downloading file list...\n");
    		
    		URL remotemd5s = new URL("http://megaminer.8u.cz/data/md5s");
            BufferedReader md5sNew = new BufferedReader(
            new InputStreamReader(remotemd5s.openStream()));
            
            String inputLine;
            
            String[] part;
            
            BufferedReader md5sOld;
                        
            if(new File("md5s").exists())
            {
            try
            {
            URL localmd5s = new File("md5s").toPath().toUri().toURL();
            md5sOld  = new BufferedReader(
            new InputStreamReader(localmd5s.openStream()));
            }
            catch (IOException e)
    		{
    			success = false;
    			
    			Updater.progressBar.setIndeterminate(false);
    			
    			Updater.playB.setVisible(false);
    			
    			Updater.out.append("Something went wrong...\n");
    			Updater.out.append("Got IOException while looking for md5s file...\n");    		
    			
            	Updater.playB.setEnabled(false);
            	Updater.exitB.setVisible(true);
                Updater.playB.setVisible(true);
                Updater.exitB.setVisible(true);
                Updater.progressBar.setVisible(false);
                updater.setCursor(Cursor.getDefaultCursor());
    			
    			e.printStackTrace();
    			
    			return;
    		} 
            
            while ((inputLine = md5sOld.readLine()) != null)
    	    {
            	part = inputLine.split("-");
            	
            	hashesLocal.put(part[0], part[1]);
    	    }
            
            md5sOld.close();   
            }
            else
            {
            	nomd5s = true;
            }           
            
            PrintWriter writer = new PrintWriter("md5s", "UTF-8");
            
            while ((inputLine = md5sNew.readLine()) != null)
    	    {
            	part = inputLine.split("-");
            	
            	hashesLatest.put(part[0], part[1]);
            	
    	    	writer.println(inputLine);
    	    }		
            
    	    
    		writer.close();	
    		
            md5sNew.close();
            
    		} 
    		catch (IOException e)
    		{
    			success = false;
    			
    			Updater.progressBar.setIndeterminate(false);
    			
    			Updater.playB.setVisible(false);
    			
    			Updater.out.append("Something went wrong...\n");
    			Updater.out.append("The server is probably down...\n");    		
    			
            	Updater.playB.setEnabled(false);
            	Updater.exitB.setVisible(true);
                Updater.playB.setVisible(true);
                Updater.exitB.setVisible(true);
                Updater.progressBar.setVisible(false);
                updater.setCursor(Cursor.getDefaultCursor());
    			
    			e.printStackTrace();
    			
    			return;
    		} 
    		
    		Updater.out.append("Calculating what files are needed to download...\n");
    		
    		if(!nomd5s)
    		{
    			Iterator<String> it = hashesLatest.keySet().iterator();

    			while(it.hasNext()){
    			  String key = it.next();
    			  	
    			  if(!hashesLocal.containsValue(hashesLatest.get(key)))
    			  {
    				  assetsToDL.add(key);
    			  }
    			}
    		}
    		else
    		{
    			Iterator<String> it = hashesLatest.keySet().iterator();

    			while(it.hasNext()){
    			  String key = it.next();
    			  assetsToDL.add(key);
    			}
    		}
    		
    		files.addAll(assetsToDL);
    		
    		try 
    		{
    			if(files.size() == 0)
    			{
    				Updater.out.append("No files are needed to download...\n");
    			}
    			else
    			{
        			Updater.out.append("Total files to download: "+files.size()+"\n");
    			}
    			
    			Updater.progressBar.setIndeterminate(false);
    			
    			int i = 0;
    			
    			for(String s:files)
    			{ 	   
    				i++;
    				
    				progress = 95/files.size()*i-1;
    				
    				Updater.progressBar.setValue(progress);
    				
    				Updater.out.append("Downloading: /"+s+"\n");
    				URL website = new URL("http://megaminer.8u.cz/data/"+s);
    				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
    				String[] s1 = s.split("/");
    				if(s1.length == 2)
    				{
    					new File("Game/"+s1[0]).mkdirs();
    				}
    				if(s1.length == 3)
    				{
    					new File("Game/"+s1[0]+"/"+s1[1]).mkdirs();
    				}
    				if(s1.length == 4)
    				{
    					new File("Game/"+s1[0]+"/"+s1[1]+"/"+s1[2]).mkdirs();
    				}
    				FileOutputStream fos = new FileOutputStream("Game/"+s);
    				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    				fos.close();
    				Updater.out.append("Download done.\n");
    			}
    			success = true;
    		} 
    		catch (IOException e) {
    			Updater.out.append(e.getMessage()+"\n");
    		}
    		if(success)
    		{
    		Updater.progressBar.setValue(95);
    			
    		Updater.out.append("Deleting temporary files...\n");
    		new File("latest").delete();
    		new File("toDL").delete();
    		Updater.out.append("Done.\n");
    		Updater.out.append("Update complete!\n");
    		
        	Updater.playB.setEnabled(true);
        	Updater.exitB.setEnabled(true);
            Updater.playB.setVisible(true);
            Updater.exitB.setVisible(true);
            Updater.delB.setVisible(true);
            Updater.delB.setEnabled(true);
            
            Updater.progressBar.setVisible(false);
            updater.setCursor(Cursor.getDefaultCursor());
    		
    		Updater.progressBar.setValue(100);
    		}
	}
}
