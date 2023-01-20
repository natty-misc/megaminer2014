package cz.mm14.src;

public class SPacketTranslater {

	public static void translate(String line) 
	{
		String[] packet = line.split(";");
				
		for(int i = 0; i < packet.length; i++)
		{
		if(packet[i].startsWith("PlayerC:"))
		{
			if(!Game.players.isEmpty()) continue;
			
			packet[i] = packet[i].substring(8);
		
			String[] dataField = packet[i].split(",");
						
			Player p1 = Player.getPlayerByID(Long.parseLong(dataField[2]));
			
			if(p1==null) continue;
						
			p1.x = Integer.parseInt(dataField[0]);
			p1.y = Integer.parseInt(dataField[1]);
		}
		
		if(packet[i].startsWith("BlockDestroy:"))
		{
			if(Game.players.isEmpty()) continue;
			
			packet[i] = packet[i].substring(8);
		
			String[] dataField = packet[i].split(",");
						
			Player p1 = Player.getPlayerByID(Long.parseLong(dataField[2]));
			
			if(p1==null) continue;
						
			p1.x = Integer.parseInt(dataField[0]);
			p1.y = Integer.parseInt(dataField[1]);
		}
		}
	}
}
