package me.tyler.terraria.packets;

public class TerrariaPacketSendTileSquare extends TerrariaPacket {

	public TerrariaPacketSendTileSquare(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public short getSize(){
		return getPayloadBuffer().getShort();
	}
	
	private int getSizeSquared(){
		return getSize() * getSize();
	}
	
	public int getTileX(){
		return getPayloadBuffer(2).getInt();
	}
	
	public int getTileY(){
		return getPayloadBuffer(6).getInt();
	}
}
