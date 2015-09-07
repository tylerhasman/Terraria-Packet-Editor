package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketPlaceFrame extends TerrariaPacket {
	
	public static TerrariaPacket getPlaceItemFramePacket(short x, short y, short itemId, byte prefix, short amount){
		
		ByteBuffer buf = ByteBuffer.allocate(9).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putShort(x);
		buf.putShort(y);
		buf.putShort(itemId);
		buf.put(prefix);
		buf.putShort(amount);
		
		return new TerrariaPacket(PacketType.SPAWN_ITEM, buf.array());
		
	}
	
}
