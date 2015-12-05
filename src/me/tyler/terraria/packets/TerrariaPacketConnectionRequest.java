package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;

public class TerrariaPacketConnectionRequest extends TerrariaPacket {

	private static byte[] version;
	
	public TerrariaPacketConnectionRequest(byte t, byte[] p) {
		super(t, p);
	}
	
	public TerrariaPacketConnectionRequest() {
		super(PacketType.CONNECTION_REQUEST.getId(), ByteBuffer.allocate(version.length).order(ByteOrder.LITTLE_ENDIAN).put(version).array());
	}

	public String getVersion(){
		return PacketUtil.readString(getPayload(), 0);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		version = getPayload();
		
		return super.onSending(proxy);
	}
	
	public static String getClientVersion(){
		return PacketUtil.readString(version, 0);
	}
	
}
