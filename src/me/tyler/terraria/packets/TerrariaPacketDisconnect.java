package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;

public class TerrariaPacketDisconnect extends TerrariaPacket {

	public TerrariaPacketDisconnect(byte t, byte[] p) {
		super(t, p);
	}
	
	public TerrariaPacketDisconnect(String reason) {
		super(PacketType.DISCONNECT.getId(), getKickPacket(reason));
	}

	public String getReason(){
		return PacketUtil.readString(getPayload(), 0);
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		System.out.println("Disconnected -> "+getReason());
		
		return super.onReceive(proxy);
	}
	
	private static byte[] getKickPacket(String message){

		ByteBuffer buf = ByteBuffer.allocate(PacketUtil.calculateLength(message) + 1).order(ByteOrder.LITTLE_ENDIAN);
		
		PacketUtil.writeString(buf, message);
		
		return buf.array();
	}

}
