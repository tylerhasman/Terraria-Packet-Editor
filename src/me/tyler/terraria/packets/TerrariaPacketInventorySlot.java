package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacketInventorySlot extends TerrariaPacket {

	public TerrariaPacketInventorySlot(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketInventorySlot(byte playerId, byte slot, short stack, byte prefix, short itemId){
		super(PacketType.INVENTORY_SLOT.getId(), getPacket(playerId, slot, stack, prefix, itemId).array());
	}
	
	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public byte getSlot(){
		return getPayloadBuffer(1).get();
	}
	
	public short getStack(){
		return getPayloadBuffer(2).getShort();
	}
	
	public byte getPrefix(){
		return getPayloadBuffer(4).get();
	}
	
	public short getItemId(){
		return getPayloadBuffer(5).getShort();
	}

	private static ByteBuffer getPacket(byte pid, byte slot, short stack, byte prefix, short itemId){
		ByteBuffer buf = ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put(pid);
		buf.put(slot);
		buf.putShort(stack);
		buf.put(prefix);
		buf.putShort(itemId);
		
		return buf;
	}

}
