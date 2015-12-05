package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;

public class TerrariaPacketUUID extends TerrariaPacket {

	private static UUID fakeId = null;

	public TerrariaPacketUUID(byte type, byte[] payload) {
		super(type, payload);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		proxy.sendPacketToServer(getFakeUUIDPacket());
		
		return false;
	}
	
	public String getUUID(){
		return PacketUtil.readString(getPayload(), 0);
	}
	
	public static TerrariaPacket getFakeUUIDPacket(){
		
		if(fakeId == null){
			fakeId = UUID.randomUUID();
		}

		String str = fakeId.toString();
		
		ByteBuffer buf = ByteBuffer.allocate(PacketUtil.calculateLength(str) + 1).order(ByteOrder.LITTLE_ENDIAN);
		
		PacketUtil.writeString(buf, str);
		
		return new TerrariaPacket(PacketType.CLIENT_UUID.getId(), buf.array());
		
	}
	
	
}
