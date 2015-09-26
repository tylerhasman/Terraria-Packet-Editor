package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.Proxy;

public class TerrariaPacketTogglePvp extends TerrariaPacket {
	
	public TerrariaPacketTogglePvp(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public boolean isPvpOn(){
		return getPayloadBuffer(1).get() > 0;
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		proxy.getPlayer(getPlayerId()).setPvpEnabled(isPvpOn());
		
		return super.onReceive(proxy, client);
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		proxy.getThePlayer().setPvpEnabled(isPvpOn());
		
		return super.onSending(proxy, client);
	}

}
