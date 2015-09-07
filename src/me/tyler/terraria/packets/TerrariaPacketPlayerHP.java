package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.Proxy;

public class TerrariaPacketPlayerHP extends TerrariaPacket {

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public short getLife(){
		return getPayloadBuffer(1).getShort();
	}
	
	public short getMaxLife(){
		return getPayloadBuffer(3).getShort();
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		if(getPlayerId() == proxy.getThePlayer().getId()){
			if(Cheats.BLOCK_DAMAGE){
				proxy.sendPacketToClient(client, TerrariaPacketHealOther.getHealthOtherPacket(getPlayerId(), (short) (getMaxLife()-getLife())));
				return false;
			}
		}
		return super.onSending(proxy, client);
	}
	
}
