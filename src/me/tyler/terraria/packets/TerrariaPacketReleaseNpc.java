package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketReleaseNpc extends TerrariaPacket {

	public TerrariaPacketReleaseNpc(byte type, byte[] payload) {
		super(type, payload);
	}

	public int getX(){
		return getPayloadBuffer().getInt();
	}
	
	public int getY(){
		return getPayloadBuffer(4).getInt();
	}
	
	public short getNpcType(){
		return getPayloadBuffer(8).getShort();
	}
	
	public byte getStyle(){
		return getPayloadBuffer(10).get();
	}
	
	
	public static TerrariaPacket getReleaseNpcPacket(int x, int y, short type, byte style){
		
		ByteBuffer buf = ByteBuffer.allocate(11).order(ByteOrder.LITTLE_ENDIAN);
		
		
		buf.putInt(x);
		buf.putInt(y);
		buf.putShort(type);
		buf.put(style);
		
		return new TerrariaPacket(PacketType.RELEASE_NPC.getId(), buf.array());
	}
	
}
