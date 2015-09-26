package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.Npc;
import me.tyler.terraria.Proxy;

public class TerrariaPacketUpdateNpc extends TerrariaPacket {

	public TerrariaPacketUpdateNpc(byte type, byte[] payload) {
		super(type, payload);
	}

	public short getId(){
		return getPayloadBuffer().getShort();
	}
	
	public float getPositionX(){
		return getPayloadBuffer(2).getFloat();
	}
	
	public float getPositionY(){
		return getPayloadBuffer(6).getFloat();
	}
	
	public float getVelocityX(){
		return getPayloadBuffer(10).getFloat();
	}
	
	public float getVelocityY(){
		return getPayloadBuffer(14).getFloat();
	}
	
	public byte getTarget(){
		return getPayloadBuffer(18).get();
	}
	
	public byte getFlags(){
		return getPayloadBuffer(19).get();
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		if(proxy.getNpc(getId()) == null){
			Npc npc = new Npc(getId());
			npc.setX(getPositionX());
			npc.setY(getPositionY());
			
			proxy.addNpc(npc);
		}else{
			proxy.getNpc(getId()).setX(getPositionX());
			proxy.getNpc(getId()).setY(getPositionY());
		}
		
		return true;
	}
	
	
}
