package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketAddBuff extends TerrariaPacket {
	
	public TerrariaPacketAddBuff(byte t, byte[] p) {
		super(t, p);
	}
	
	public TerrariaPacketAddBuff(int playerId, int buffId, int time) {
		super(PacketType.ADD_BUFF.getId(), getBuffPacket(playerId, buffId, time));
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public int getBuff(){
		return getPayloadBuffer(1).get() & 0xFF;
	}
	
	public short getTime(){
		return getPayloadBuffer(2).getShort();
	}

	private static byte[] getBuffPacket(int playerId, int buff, int time){
		ByteBuffer buf = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put((byte) playerId);
		buf.put((byte) buff);
		buf.putShort((short) time);
		
		return buf.array();
	}
	
}
