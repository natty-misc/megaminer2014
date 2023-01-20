package cz.mm14.src.network;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import cz.mm14.src.Game;
import cz.mm14.src.menu.MultiPlayerMenu;

public class ServerConnecter {

	private static String[] part;

	public static Socket join(String ip) 
	{
		ip = ip.trim();
		part = ip.split(":");
		
		if(part.length != 2) 
		{
		MultiPlayerMenu.reportErr("BadArgs");
		return null;
		}
		
		  try {
			Socket sock = new Socket(part[0], Integer.parseInt(part[1]));
			return sock;
		} 
		  catch (NumberFormatException e) 
			{
			  MultiPlayerMenu.reportErr("PortNotNumber");
			  return null;
			}
		  	catch (IllegalArgumentException e) 
			{
			  MultiPlayerMenu.reportErr("BadArgs");
			  return null;
			}
			catch (ConnectException e) 
			{
				Game.resetWindowName();
				
				MultiPlayerMenu.reportErrWithReason(e.toString(), "Could not connect to the server: " + ip);
				return null;
			}
		  
		  catch (IOException e) {
			Game.resetWindowName(); 
			  
			e.printStackTrace();
		}
		 return null;
	}

}
