package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketManaEffect extends TerrariaPacket {

	public TerrariaPacketManaEffect(byte t, byte[] p) {
		super(t, p);
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public short getAmount(){
		return getPayloadBuffer(1).getShort();
	}
	
	public static TerrariaPacket getManaEffectPacket(byte playerId, short amount){
		
		ByteBuffer buf = ByteBuffer.allocate(3).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put(playerId);
		buf.putShort(amount);
		
		return new TerrariaPacket(PacketType.MANA_EFFECT.getId(), buf.array());
		
	}
	
}
