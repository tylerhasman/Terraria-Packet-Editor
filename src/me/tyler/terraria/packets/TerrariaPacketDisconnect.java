package me.tyler.terraria.packets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;

public class TerrariaPacketDisconnect extends TerrariaPacket {

	public TerrariaPacketDisconnect(byte t, byte[] p) {
		super(t, p);
	}

	public String getReason(){
		return PacketUtil.readString(getPayload(), 0);
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		
		System.out.println("Disconnected -> "+getReason());
		
		try {
			proxy.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return super.onReceive(proxy);
	}
	
	public static TerrariaPacket getKickPacket(String message){
		
		byte[] bytes = null;
		try {
			bytes = message.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			//This will never occur
		}
				
		
		ByteBuffer buf = ByteBuffer.allocate(bytes.length+1).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put((byte) bytes.length);
		buf.put(bytes);
		
		return new TerrariaPacket(PacketType.DISCONNECT.getId(), buf.array());
		
		
	}

}
