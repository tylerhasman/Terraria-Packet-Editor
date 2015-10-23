package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaData;

public class TerrariaPacketDestroyProjectile extends TerrariaPacket {

	public TerrariaPacketDestroyProjectile(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public short getProjectileId(){
		return getPayloadBuffer().getShort();
	}
	
	public byte getOwner(){
		return getPayloadBuffer(2).get();
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		int index = TerrariaData.proj_ids_in_use.indexOf(getProjectileId());
		
		if(index >= 0){
			TerrariaData.proj_ids_in_use.remove(index);
		}
		
		return super.onReceive(proxy, client);
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		int index = TerrariaData.proj_ids_in_use.indexOf(getProjectileId());
		
		if(index >= 0){
			TerrariaData.proj_ids_in_use.remove(index);
		}
		
		
		return super.onSending(proxy, client);
	}

}
