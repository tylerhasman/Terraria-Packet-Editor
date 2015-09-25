package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketHealOther extends TerrariaPacket {

	public TerrariaPacketHealOther(byte t, byte[] p) {
		super(t, p);
	}

	public static TerrariaPacket getHealthOtherPacket(byte playerId, short amount){
		
		ByteBuffer buf = ByteBuffer.allocate(3).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put(playerId);
		buf.putShort(amount);
		
		return new TerrariaPacket(PacketType.HEAL_OTHER.getId(), buf.array());
		
	}
	
}
