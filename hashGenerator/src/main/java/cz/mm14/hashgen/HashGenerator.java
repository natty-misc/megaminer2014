package cz.mm14.hashgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashGenerator 
{
	
	public static void main(String[] args)
	{						
		try 
		{
			createNewHashesFile();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void createNewHashesFile() throws IOException, NoSuchAlgorithmException 
	{
		ArrayList<File> toProcess = new ArrayList<File>();
				
		File baseFolder = new File(new File("../client").getCanonicalPath());
		
		String baseFolderPath = baseFolder.getAbsolutePath();
		
		List<File> assets =  new ArrayList<File>(getFilesInFolder(baseFolderPath+"/assets/"));
		
		for(int i = 0; i<assets.size(); i++)
		{
			if(assets.get(i).isDirectory())
			{
				List<File> dirContent = getFilesInFolder(assets.get(i).getAbsolutePath());
				for(int j = 0; j < dirContent.size(); j++)
				{
					assets.add(dirContent.get(j));
				}
			}
			else
			{
				if(assets.get(i).getName().contains("Thumbs.db"))
				{
					continue;
				}
				
				toProcess.add(assets.get(i));
			}
		}
		
		PrintWriter writer = new PrintWriter("md5s", "UTF-8");
		for(int i = 0; i<toProcess.size(); i++)
		{
			String relative = convertToRelativePath(baseFolderPath, toProcess.get(i).getPath());
			
			String md5;
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			try (InputStream is = Files.newInputStream(Paths.get(toProcess.get(i).getAbsolutePath()))) {
				  //DigestInputStream dis = new DigestInputStream(is, md);
				  
				  byte[] buffer = new byte[8192];
		            int numOfBytesRead;
		            while( (numOfBytesRead = is.read(buffer)) > 0){
		                md.update(buffer, 0, numOfBytesRead);
		            }
				  
				  md5 = hexStrFromBytes(md.digest());
				}
			
			writer.println(relative+"-"+md5);
		}
		writer.close();
	}
	
	public static List<File> getFilesInFolder(String s)
	{		
		return Arrays.asList(new File(s).listFiles());
	}
	
	public static String hexStrFromBytes(byte[] b) 
	{
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if ((0xff & b[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & b[i])));
            }
            else {
                hexString.append(Integer.toHexString(0xFF & b[i]));
            }
        }
        return hexString.toString();
    }
	
	public static String convertToRelativePath(String baseFolder, String file) {
		StringBuilder relativePath = null;

		baseFolder = baseFolder.replaceAll("\\\\", "/"); 
		file = file.replaceAll("\\\\", "/");

		if (baseFolder.equals(file) == true) {

		} else {
		String[] absoluteDirectories = baseFolder.split("/");
		String[] relativeDirectories = file.split("/");

		int length = absoluteDirectories.length < relativeDirectories.length ? 
		absoluteDirectories.length : relativeDirectories.length;

		int lastCommonRoot = -1;
		int index;

		for (index = 0; index < length; index++) {
		if (absoluteDirectories[index].equals(relativeDirectories[index])) {
		lastCommonRoot = index;
		} else {
		break;
		}
		}
		if (lastCommonRoot != -1) 
		{
		relativePath = new StringBuilder();

		for (index = lastCommonRoot + 1; index < absoluteDirectories.length; index++) {
		if (absoluteDirectories[index].length() > 0) {
		relativePath.append("../");
		}
		}
		for (index = lastCommonRoot + 1; index < relativeDirectories.length - 1; index++) {
		relativePath.append(relativeDirectories[index] + "/");
		}
		relativePath.append(relativeDirectories[relativeDirectories.length - 1]);
		}
		} 
		return relativePath == null ? null : relativePath.toString();
		}
}
