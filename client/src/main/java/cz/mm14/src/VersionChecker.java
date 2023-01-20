package cz.mm14.src;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VersionChecker 
{
	public static String latest;

	public static void check()
	{
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get("latest"));
			latest = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
		} 
		catch (IOException e)
		{
			latest = "Na/N";
		}
	}
	
	public static void downloadVer()
	{
	try {
	URL website = new URL("http://megaminer.8u.cz/latest");
	ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	FileOutputStream fos = new FileOutputStream("latest");
	fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	fos.close();
	} 
	catch (IOException e)
	{
		e.printStackTrace();
	}
	}
}
