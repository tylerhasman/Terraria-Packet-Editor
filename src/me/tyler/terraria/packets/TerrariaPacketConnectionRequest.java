package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;

public class TerrariaPacketConnectionRequest extends TerrariaPacket {

	public TerrariaPacketConnectionRequest(byte t, byte[] p) {
		super(t, p);
	}

	public String getVersion(){
		return PacketUtil.readString(getPayload(), 0);
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		System.out.println("Client running version "+getVersion());
		
		return true;
	}
	
}
