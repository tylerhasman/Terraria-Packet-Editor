package me.tyler.terraria.packets;

public class TerrariaPacketSpawnPlayer extends TerrariaPacket {

	public TerrariaPacketSpawnPlayer(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public byte getId(){
		return getPayloadBuffer().get();
	}
	
	public short getSpawnX(){
		return getPayloadBuffer(1).getShort();
	}
	
	public short getSpawnY(){
		return getPayloadBuffer(3).getShort();
	}

}
