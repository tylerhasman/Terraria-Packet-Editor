package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.Proxy;

public class TerrariaPacketUpdatePlayer extends TerrariaPacket {

	public TerrariaPacketUpdatePlayer(byte type, byte[] payload) {
		super(type, payload);
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public byte getControl(){
		return getPayloadBuffer(1).get();
	}
	
	public byte getPulley(){
		return getPayloadBuffer(2).get();
	}
	
	public byte getSelectedItem(){
		return getPayloadBuffer(3).get();
	}
	
	public float getPositionX(){
		return getPayloadBuffer(4).getFloat();
	}
	
	public float getPositionY(){
		return getPayloadBuffer(8).getFloat();
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		proxy.getThePlayer().setX(getPositionX());
		proxy.getThePlayer().setY(getPositionY());
		
		proxy.setConnectionIniatializationDone(true);
		
		return true;
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		proxy.getPlayer(getPlayerId()).setX(getPositionX());
		proxy.getPlayer(getPlayerId()).setY(getPositionY());
		
		return true;
	}
	
}
