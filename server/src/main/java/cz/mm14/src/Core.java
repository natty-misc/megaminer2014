package cz.mm14.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import cz.mm14.src.network.Data;

public class Core 
{
	public static void load()
	{
		System.out.println("Registering blocks...");
		BlockRegister.regAll();
		System.out.println("Registering items...");
		ItemRegister.regAll();
	}

	public static void runGameLoop() 
	{    
	try {
	System.out.println("========= Starting server... =========");
		
	System.out.println("Enter server port:");
	System.out.print(">");
	
	String s = "dummy";
	
	try 
	{
		s = Game.reader.readLine();
	} 
	catch (IOException e) {
		e.printStackTrace();
	}
	
	System.out.println("Trying to set server port to:" + s + ".");   
	ServerSocket ss = new ServerSocket(Integer.parseInt(s));
	System.out.println("Done.");
	System.out.println("========= Loading done... =========");
	
	System.out.println("Listening for clients...");
		
	int running = 1;
	
	while(running == 1) 
	{    	   
		/*
		System.out.print(">");
		
		String s1 = "dummy";
		
	    try 
	    {
			s1 = br.readLine();
		} 
	    catch (IOException e) {
			e.printStackTrace();
		}
	    
	    if(s1.startsWith("/exit"))
	    {
	    	System.out.println("Exiting...");
	    	break;
	    }
	    */
	    
	    final Socket sock = ss.accept();
	    
	    System.out.println(sock.getRemoteSocketAddress().toString()+" has connected.");
	    
	    Game.clients.add(sock.getRemoteSocketAddress().toString());
	            
	    class ClientThread extends Thread
	    {
	    	private boolean sentInitialData = false;
			private String line;
	
			ClientThread()
	    	{
	    		super();
	    	}
	    	
			public void run() {
	            try
	            {
	            	 Game.players.add(new Player(Game.spawnX, Game.spawnY, this.getId()));
	            	
	        	    BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	        	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
	        	    
	                while(sock.isConnected())
	                {                 
	                	if(!sentInitialData)
	                	{
	                		bw.write("Map:"+MapMaker.mapW+","+MapMaker.mapH+";");
	                    	bw.flush();
	                    	bw.newLine();
	                		
	                		bw.write(Data.getBlockInitialData());
	                    	bw.flush();
	                    	bw.newLine();
	                    	
	                    	bw.write(Data.getPlayerData(this.getId()));
	                    	bw.flush();
	                    	bw.newLine();
	                    	
	                    	sentInitialData = true;
	                	}
	
	                	if((line = br.readLine()) != null)
	                	{
	                	SPacketTranslater.translate(line);
	                	}
	                	
	                	bw.write(Data.getUnsentPlayerData(this.getId()));
	                	bw.flush();
	                	bw.newLine();
	                	
	                	bw.write(Data.getPlayerMData(this.getId()));
	                	bw.flush();
	                	bw.newLine();
	                	
	                	bw.write("KeepConnected;");
	                	bw.flush();
	                	bw.newLine();	
	                }
	
	                Game.players.remove(Player.getPlayerByID(this.getId()));
	                Game.clients.remove(sock.getRemoteSocketAddress().toString());
	            	System.out.println(sock.getRemoteSocketAddress().toString()+" has left.");
	            	
	            	sock.close();
	            	
	             } 
	            catch (IOException e) 
	            	{
	            	Game.players.remove(Player.getPlayerByID(this.getId()));
	                Game.clients.remove(sock.getRemoteSocketAddress().toString());
	            	System.out.println(sock.getRemoteSocketAddress().toString()+" has left.");
	            	
	            	System.err.println(e.toString());
	            	
	            	try {
						sock.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	            	
	            	}
	        }
	    };
	    Thread t = new ClientThread();
	    t.start();
		}
		System.out.println("Everyone has left, closing server socket...");
		ss.close();
	}
	catch (Exception e) {
	e.printStackTrace();
	}
	}

	
}
