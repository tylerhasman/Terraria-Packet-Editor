package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.Proxy;

public class TerrariaPacketContinue extends TerrariaPacket {

	public TerrariaPacketContinue(byte type, byte[] payload) {
		super(type, payload);
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		return true;
	}

}
