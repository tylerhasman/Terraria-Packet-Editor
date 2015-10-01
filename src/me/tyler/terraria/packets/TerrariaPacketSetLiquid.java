package me.tyler.terraria.packets;

public class TerrariaPacketSetLiquid extends TerrariaPacket {

	public TerrariaPacketSetLiquid(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public short getX(){
		return getPayloadBuffer().getShort();
	}
	
	public short getY(){
		return getPayloadBuffer(2).getShort();
	}
	
	public byte getLiquid(){
		return getPayloadBuffer(4).get();
	}
	
	public byte getLiquidType(){
		return getPayloadBuffer(5).get();
	}

}
