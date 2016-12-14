package me.tyler.terraria.packets;


public class TerrariaPacketUpdateItemOwner extends TerrariaPacket {

	public TerrariaPacketUpdateItemOwner(byte type, byte[] payload) {
		super(type, payload);
	}

	public short getItemId() {
		return getPayloadBuffer().getShort();
	}

	public byte getOwner() {
		return getPayloadBuffer(2).get();
	}

}
