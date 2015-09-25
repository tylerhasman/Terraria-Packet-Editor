package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;

public class TerrariaPacketStatus extends TerrariaPacket {

	public TerrariaPacketStatus(byte type, byte[] payload) {
		super(type, payload);
	}

	public int getStatusMax(){
		return getPayloadBuffer().getInt();
	}
	
	public String getStatus(){
		return PacketUtil.readString(getPayload(), 4);
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		System.out.println("Status "+getStatusMax()+": "+getStatus());
		
		return true;
	}
	
}
