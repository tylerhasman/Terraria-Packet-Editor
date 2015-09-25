package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketStrikeNpc extends TerrariaPacket {

	public TerrariaPacketStrikeNpc(byte type, byte[] payload) {
		super(type, payload);
	}

	public static TerrariaPacket getStrikePacket(int npcid, int damage, float knockback, byte direction, boolean crit){
		
		ByteBuffer buf = ByteBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putShort((short) npcid);
		buf.putShort((short) damage);
		buf.putFloat(knockback);
		buf.put(direction);
		buf.put((byte) (crit ? 1 : 0));
		
		return new TerrariaPacket(PacketType.STRIKE_NPC.getId(), buf.array());
		
	}
	
}
