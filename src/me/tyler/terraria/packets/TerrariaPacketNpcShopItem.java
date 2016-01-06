package me.tyler.terraria.packets;

public class TerrariaPacketNpcShopItem extends TerrariaPacket {

	public TerrariaPacketNpcShopItem(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public byte getSlot(){
		return getPayload()[0];
	}
	
	public short getItemType(){
		return getPayloadBuffer(1).getShort();
	}
	
	public short getAmount(){
		return getPayloadBuffer(3).getShort();
	}
	
	public byte getPrefix(){
		return getPayloadBuffer(5).get();
	}
	
	public int getValue(){
		return getPayloadBuffer(6).getInt();
	}
	
	public byte getFlags(){
		return getPayloadBuffer(10).get();
	}

}
