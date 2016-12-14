package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketUpdatePlayerBuff extends TerrariaPacket{

	private static final int MAX_BUFFS = 22;
	
	public TerrariaPacketUpdatePlayerBuff(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketUpdatePlayerBuff(int id, byte[] buffs) {
		super(PacketType.UPDATE_BUFF.getId(), ByteBuffer.allocate(MAX_BUFFS+1).order(ByteOrder.LITTLE_ENDIAN).put((byte) id).put(buffs).array());
	}
	
	public int[] getBuffs(){
		int[] buffs = new int[MAX_BUFFS];
		for(int i = 0; i < buffs.length;i++){
			buffs[i] = getPayloadBuffer().get(i + 1) & 0xFF;
		}
		
		return buffs;
	}
	
	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}

}
