package me.tyler.terraria.packets;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;

public class TerrariaPacketUUID extends TerrariaPacket {

	private static UUID fakeId = null;
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		System.out.println("Client tried to send UUID "+getUUID());
		
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
		
		ByteBuffer buf = ByteBuffer.allocate(str.length() + 1).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put((byte) str.length());
		
		try {
			buf.put(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;//This will never happen
		}
		
		System.out.println("Fake UUID: "+fakeId.toString());
		
		return new TerrariaPacket(PacketType.CLIENT_UUID, buf.array());
		
	}
	
	
}
