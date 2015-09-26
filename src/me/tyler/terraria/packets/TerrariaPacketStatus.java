package me.tyler.terraria.packets;

import me.tyler.terraria.PacketUtil;

public class TerrariaPacketStatus extends TerrariaPacket {

	public TerrariaPacketStatus(byte type, byte[] payload) {
		super(type, payload);
	}

	public int getStatusMax(){
		return getPayloadBuffer().getInt();
	}
	
	public String getStatus(){
		return PacketUtil.readString(getPayload(), 4);
	}
	
}
