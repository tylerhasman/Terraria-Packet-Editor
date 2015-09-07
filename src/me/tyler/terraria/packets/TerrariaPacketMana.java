package me.tyler.terraria.packets;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacketMana extends TerrariaPacket {

	public byte getPlayerID(){
		return getPayloadBuffer().get();
	}
	
	public short getMana(){
		return getPayloadBuffer(1).getShort();
	}
	
	public short getMaxMana(){
		return getPayloadBuffer(3).getShort();
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		if(getPlayerID() == proxy.getThePlayer().getId()){
			proxy.sendPacketToClient(client, getManaPacket(getPlayerID(), getMaxMana(), getMaxMana()));
			
			return false;
		}
		
		
		
		return true;
	}
	
	public static TerrariaPacket getManaPacket(byte playerId, short mana, short maxMana){
		
		ByteBuffer buf = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put(playerId);
		buf.putShort(mana);
		buf.putShort(maxMana);
		
		return new TerrariaPacket(PacketType.MANA, buf.array());
		
	}
	
}
