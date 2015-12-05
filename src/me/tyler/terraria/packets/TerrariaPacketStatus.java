package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;

public class TerrariaPacketStatus extends TerrariaPacket {

	public TerrariaPacketStatus(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketStatus(int max, String message){
		super(PacketType.STATUS.getId(), create(max, message));
	}
	
	private static byte[] create(int max, String message){
		ByteBuffer buf = ByteBuffer.allocate(5 + PacketUtil.calculateLength(message)).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putInt(max);
		PacketUtil.writeString(buf, message);
		
		return buf.array();
	}

	public int getStatusMax(){
		return getPayloadBuffer().getInt();
	}
	
	public String getStatus(){
		return PacketUtil.readString(getPayload(), 4);
	}
	
}
