package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.Proxy;

public class TerrariaPacketUpdatePlayer extends TerrariaPacket {

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
		
		//System.out.println(getPositionX()+" "+getPositionY());
		
		proxy.getThePlayer().setX(getPositionX());
		proxy.getThePlayer().setY(getPositionY());
		
		return super.onSending(proxy, client);
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		proxy.getPlayer(getPlayerId()).setX(getPositionX());
		proxy.getPlayer(getPlayerId()).setY(getPositionY());
		
		return super.onReceive(proxy, client);
	}
	
}
