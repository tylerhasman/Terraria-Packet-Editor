package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketGetSection extends TerrariaPacket {

	public TerrariaPacketGetSection(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketGetSection(int x, int y) {
		this(PacketType.GET_SECTION.getId(), getPacket(x, y));
	}
	
	public TerrariaPacketGetSection(float x, float y) {
		this((int) x, (int) y);
	}
	
	public int getX(){
		return getPayloadBuffer().getInt();
	}
	
	public int getY(){
		return getPayloadBuffer(4).getInt();
	}
	
	private static byte[] getPacket(int x, int y){
		
		ByteBuffer buf = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putInt(x);
		buf.putInt(y);
		
		return buf.array();
		
	}

}
