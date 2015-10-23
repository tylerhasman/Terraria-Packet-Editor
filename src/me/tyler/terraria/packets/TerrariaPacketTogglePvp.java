package me.tyler.terraria.packets;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacketTogglePvp extends TerrariaPacket {
	
	public TerrariaPacketTogglePvp(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketTogglePvp(byte playerId, boolean pvp){
		super(PacketType.TOGGLE_PVP.getId(), getPvpPacket(playerId, pvp).array());
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
	
	private static ByteBuffer getPvpPacket(byte playerId, boolean pvp){
		ByteBuffer buf = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put(playerId);
		buf.put((byte) (pvp ? 1 : 0));
		
		return buf;
	}

}
