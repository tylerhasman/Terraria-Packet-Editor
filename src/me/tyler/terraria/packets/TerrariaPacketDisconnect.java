package me.tyler.terraria.packets;

import java.io.IOException;
import java.net.Socket;

import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;

public class TerrariaPacketDisconnect extends TerrariaPacket {

	public String getReason(){
		return PacketUtil.readString(getPayload(), 0);
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		System.out.println("Kicked: "+getReason());
		
		try {
			proxy.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
