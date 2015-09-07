package me.tyler.terraria.packets;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacketTeleport extends TerrariaPacket {

	public TerrariaPacketTeleport(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketTeleport() {
		
	}
	
	public byte getFlags(){
		return getPayloadBuffer().get();
	}
	
	public short getEntityId(){
		return getPayloadBuffer(1).getShort();
	}
	
	public float getX(){
		return getPayloadBuffer(3).getFloat();
	}
	
	public float getY(){
		return getPayloadBuffer(7).getFloat();
	}
	
	public static TerrariaPacketTeleport getTeleportPacket(byte flag, short entityId, float x, float y){
		
		ByteBuffer buf = ByteBuffer.allocate(11).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put(flag);
		buf.putShort(entityId);
		buf.putFloat(x);
		buf.putFloat(y);
		
		TerrariaPacketTeleport packet = new TerrariaPacketTeleport(PacketType.TELEPORT, buf.array());
		
		return packet;
		
	}
	
}
