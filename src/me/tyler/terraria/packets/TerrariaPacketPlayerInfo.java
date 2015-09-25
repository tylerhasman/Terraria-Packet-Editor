package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaPlayer;

public class TerrariaPacketPlayerInfo extends TerrariaPacket {

	public TerrariaPacketPlayerInfo(byte type, byte[] payload) {
		super(type, payload);
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public byte getSkinVariant(){
		return getPayloadBuffer(1).get();
	}
	
	public byte getHair(){
		return getPayloadBuffer(2).get();
	}
	
	public String getName(){
		return PacketUtil.readString(getPayload(), 3);
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		TerrariaPlayer tp = new TerrariaPlayer(getPlayerId());
		tp.setName(getName());
		proxy.addPlayer(tp);
		return true;
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		proxy.setThePlayer(new TerrariaPlayer(getPlayerId()));
		proxy.getThePlayer().setName(getName());
		
		return true;
	}
	
}
