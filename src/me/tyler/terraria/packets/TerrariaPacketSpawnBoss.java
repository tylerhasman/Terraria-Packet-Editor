package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketSpawnBoss extends TerrariaPacket {

	public TerrariaPacketSpawnBoss(byte type, byte[] payload) {
		super(type, payload);
	}

	public static TerrariaPacket getSpawnBossPacket(short playerId, short type){
		ByteBuffer buf = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putShort(playerId);
		buf.putShort(type);
		
		return new TerrariaPacket(PacketType.SPAWN_BOSS.getId(), buf.array());
	}
	
}
