package me.tyler.terraria.packets;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacketPortalTeleport extends TerrariaPacket {

	public TerrariaPacketPortalTeleport(byte type, byte[] buffer) {
		super(type, buffer);
	}
	
	public TerrariaPacketPortalTeleport() {
		
	}
	
	
	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public short getPortalColorIndex(){
		return getPayloadBuffer(1).getShort();
	}
	
	public float getNewX(){
		return getPayloadBuffer(3).getFloat();
	}
	
	public float getNewY(){
		return getPayloadBuffer(7).getFloat();
	}
	
	public float getVelocityX(){
		return getPayloadBuffer(11).getFloat();
	}
	
	public float getVelocityY(){
		return getPayloadBuffer(15).getFloat();
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		return super.onReceive(proxy, client);
	}
	
	public static TerrariaPacketPortalTeleport getPortalTeleportPacket(int playerId, int portalIndex, float x, float y, float velx, float vely)
	{
		ByteBuffer buf = ByteBuffer.allocate(20).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put((byte) playerId);
		buf.putShort((short) portalIndex);
		buf.putFloat(x);
		buf.putFloat(y);
		buf.putFloat(velx);
		buf.putFloat(vely);
		
		return new TerrariaPacketPortalTeleport(PacketType.PORTAL_TELEPORT, buf.array());
		
	}
}
