package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketSpawnBoss extends TerrariaPacket {
	
	public TerrariaPacketSpawnBoss() {
		
	}
	
	public TerrariaPacketSpawnBoss(byte type, byte[] array) {
		super(type, array);
	}
	
	public static TerrariaPacketSpawnBoss getSpawnBossPacket(short playerId, short type){
		ByteBuffer buf = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putShort(playerId);
		buf.putShort(type);
		
		return new TerrariaPacketSpawnBoss(PacketType.SPAWN_BOSS, buf.array());
	}
	
}
