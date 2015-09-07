package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;

public class TerrariaPacketNpcPortalTeleport extends TerrariaPacket {

	public static TerrariaPacket getPortalTeleportPacket(byte index, short portalIndex, float x, float y, float velx, float vely)
	{
		ByteBuffer buf = ByteBuffer.allocate(19).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put(index);
		buf.putShort(portalIndex);
		buf.putFloat(x);
		buf.putFloat(y);
		buf.putFloat(velx);
		buf.putFloat(vely);
		
		return new TerrariaPacket(PacketType.NPC_PORTAL_TELEPORT, buf.array());
		
	}
	
}
