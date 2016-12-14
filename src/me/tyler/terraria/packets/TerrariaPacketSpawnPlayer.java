package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketSpawnPlayer extends TerrariaPacket {

	public TerrariaPacketSpawnPlayer(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketSpawnPlayer(byte id, int sx, int sy) {
		super(PacketType.SPAWN_PLAYER.getId(), ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN).put(id).putShort((short) sx).putShort((short) sy).array());
	}
	
	public byte getId(){
		return getPayloadBuffer().get();
	}
	
	public short getSpawnX(){
		return getPayloadBuffer(1).getShort();
	}
	
	public short getSpawnY(){
		return getPayloadBuffer(3).getShort();
	}

}
