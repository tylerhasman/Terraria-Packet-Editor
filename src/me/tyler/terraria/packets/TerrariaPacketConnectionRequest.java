package me.tyler.terraria.packets;

import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;

public class TerrariaPacketConnectionRequest extends TerrariaPacket {

	public TerrariaPacketConnectionRequest(byte t, byte[] p) {
		super(t, p);
	}

	public String getVersion(){
		return PacketUtil.readString(getPayload(), 0);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		System.out.println("Client running version "+getVersion());
		
		return super.onSending(proxy);
	}
	
}
