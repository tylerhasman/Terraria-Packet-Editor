package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketEmoteBubble extends TerrariaPacket {

	private static int nextId = Integer.MAX_VALUE;
	
	public TerrariaPacketEmoteBubble(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketEmoteBubble(byte anchor, int metadata, byte lifetime, byte emote) {
		super(PacketType.EMOTE_BUBBLE.getId(), getPacket(nextId--, anchor, metadata, lifetime, emote).array());
	}

	public int getEmoteId(){
		return getPayloadBuffer().getInt();
	}
	
	public byte getAnchorType(){
		return getPayloadBuffer(4).get();
	}
	
	public int getMetadata(){
		int data = getPayloadBuffer(5).getShort() & 0xFFFF;
		
		return data;
	}
	
	public byte getLifetime(){
		return getPayloadBuffer(7).get();
	}
	
	public byte getEmote(){
		return getPayloadBuffer(8).get();
	}
	
	private static ByteBuffer getPacket(int id, byte anchor, int metadata, byte lifetime, byte emote){
		ByteBuffer buf = null;
		
		buf = ByteBuffer.allocate(9);
		
		buf = buf.order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putInt(id);
		buf.put(anchor);
		
		buf.putShort((short) metadata);
		buf.put(lifetime);
		buf.put(emote);
		
		return buf;
	}
	
}
