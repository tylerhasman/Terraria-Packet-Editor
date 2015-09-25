package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.Proxy;

public class TerrariaPacketAddBuff extends TerrariaPacket {

	public TerrariaPacketAddBuff(byte t, byte[] p) {
		super(t, p);
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public int getBuff(){
		return getPayloadBuffer(1).get() & 0xFF;
	}
	
	public short getTime(){
		return getPayloadBuffer(2).getShort();
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		if(getPlayerId() == proxy.getThePlayer().getId()){
			return false;
		}
		
		return true;
	}
	
}
