package me.tyler.terraria.packets;

public class TerrariaPacketModifyTile extends TerrariaPacket {

	public TerrariaPacketModifyTile(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public byte getAction(){
		return getPayloadBuffer().get();
	}
	
	public short getX(){
		return getPayloadBuffer(1).getShort();
	}
	
	public short getY(){
		return getPayloadBuffer(3).getShort();
	}
	
	public short getVar1(){
		return getPayloadBuffer(5).getShort();
	}
	
	public byte getVar2(){
		return getPayloadBuffer(7).get();
	}
	

}
