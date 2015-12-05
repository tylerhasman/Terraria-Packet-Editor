package me.tyler.terraria.packets;

import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaPlayerLocal;

public class TerrariaPacketContinue extends TerrariaPacket {

	public TerrariaPacketContinue(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public byte getPlayerId(){
		return getPayload()[0];
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		
		proxy.setThePlayer(new TerrariaPlayerLocal(getPlayerId(), proxy));
		
		return super.onReceive(proxy);
	}

}
