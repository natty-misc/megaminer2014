package cz.mm14.src.modloading;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LoadMods 
{
		public static ArrayList<String> mods;
		
		public static void collectMods()
		{
			System.out.println("[Mod Loader] ................................");
			System.out.println("[Mod Loader] Looking for new mods to install.");
			
			File f = new File("mods/");
			if(!f.exists())
	        {
	        	f.mkdir();
	        }
		
			ArrayList<String> files = new ArrayList<String>(Arrays.asList(f.list()));
			if(files.size() == 0)
			{
				System.out.println("[Mod Loader] No mod found.");
			}
			else
			{
				System.out.println("[Mod Loader] Found " + files.size() + " mods.");
			for(String file: files)
			{
				System.out.println("[Mod Loader] Mod found: "+ file + ", installing it.");
				
				try {
					extract(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				new File("mods/"+file).delete();
				
				System.out.println("[Mod Loader] Done.");
			}
			}
		}
		
	    private static void extract(String filepath) throws FileNotFoundException, IOException 
	    {
	    	   byte[] buffer = new byte[2048];
	    	   
	           InputStream theFile = null;

				theFile = new FileInputStream("mods/"+filepath);
	
	           ZipInputStream stream = new ZipInputStream(theFile);
	           String outdir = "modfiles/";
	           
	           if(!new File(outdir).exists())
	           {
	        	   new File(outdir).mkdir();
	           }
	    
	           try
	           {
	        	   new File("modfiles/"+filepath.substring(0, filepath.length()-4)).mkdir();
	        	   
	               ZipEntry entry;
	               while((entry = stream.getNextEntry())!=null)
	               {
	                   String outpath = outdir + filepath.substring(0, filepath.length()-4)+ "/" + entry.getName();
	                   FileOutputStream output = null;
	                   try
	                   {
	                       output = new FileOutputStream(outpath);
	                       int len = 0;
	                       while ((len = stream.read(buffer)) > 0)
	                       {
	                           output.write(buffer, 0, len);
	                       }
	                   }
	                   finally
	                   {
	                       if(output!=null) output.close();
	                   }
	               }
	           }
	           finally
	           {
	               stream.close();
	           }
		}

		public static void loadAll()
	    {
			System.out.println("[Mod Loader] ................................");
			System.out.println("[Mod Loader] Looking for installed mods.");
			
			File f = new File("modfiles/");
			if(!f.exists())
	        {
	        	f.mkdir();
	        }
		
			mods = new ArrayList<String>(Arrays.asList(f.list()));
			
			if(mods.size()==0) 
			{
				System.out.println("[Mod Loader] No mod found.");
			}
			else
			{
				System.out.println("[Mod Loader] Found " + mods.size() + " mods to load.");
			}
	    }
		
		public static void initAll() throws ClassNotFoundException, IOException
		{
			System.out.println("[Mod Loader] ................................");
			int i = 0;
			
			if(mods.size()>0)
			{			
				if(mods.size()==1)
				{
					System.out.println("[Mod Loader] There is one mod to load.");
				}
				else
				{
				System.out.println("[Mod Loader] There are " + mods.size() + " mods to load.");
				}
			for (String modname:mods)
			{				
				if(new File("modfiles/"+modname+"/mod.jar").exists())
				{
					String pathToJar = "modfiles/"+modname+"/mod.jar";
					
		            @SuppressWarnings("resource")
					JarFile jarFile = new JarFile(pathToJar);
		            Enumeration<JarEntry> e = jarFile.entries();

		            URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
		            URLClassLoader cl = URLClassLoader.newInstance(urls);

		            while (e.hasMoreElements()) {
		                JarEntry je = (JarEntry) e.nextElement();
		                if(je.isDirectory() || !je.getName().endsWith(".class")){
		                    continue;
		                }

		                String className = je.getName().substring(0,je.getName().length()-6);
		                className = className.replace('/', '.');
		                @SuppressWarnings("rawtypes")
						Class c = cl.loadClass(className);
		                
		                if(!c.getSuperclass().equals(BaseMod.class)) continue;
		                
		                try {
							c.newInstance();
						} catch (InstantiationException
								| IllegalAccessException e1) {
							//e1.printStackTrace();
						}
		            }
		            
				System.out.println("[Mod Loader] Initialised mod: " + modname + ".");
				i++;
				}
				else
				{
				System.out.println("[Mod Loader] Failed to initialise mod: " + modname + ".");
				System.out.println("[Mod Loader] Reason: Missing mod.jar file.");
				}
			}	
			if(i<=1)
			{
				System.out.println("[Mod Loader] Initialising mods complete, loaded "+ i +" mods.");
			}
			else
			{
				System.out.println("[Mod Loader] Initialising mods complete, loaded "+ i +" mod.");
			}
			}
			else
			{
				System.out.println("[Mod Loader] There aren't any mods, skipping initialising phase.");
			}
			System.out.println("[Mod Loader] My job's done!");
			System.out.println("[Mod Loader] ................................");
		}
}
