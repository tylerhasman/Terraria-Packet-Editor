package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaPlayer;

public class TerrariaPacketInventorySlot extends TerrariaPacket {

	public TerrariaPacketInventorySlot(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketInventorySlot(int playerId, int slot, int stack, int prefix, int itemId){
		super(PacketType.INVENTORY_SLOT.getId(), getPacket((byte) playerId, (byte) slot, (byte) stack, (byte) prefix, (short) itemId).array());
	}
	
	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public int getSlot(){
		return getPayloadBuffer(1).get() & 0xFF;
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
	
	@Override
	public boolean onReceive(Proxy proxy) {
		boolean result = super.onReceive(proxy);
		
		TerrariaPlayer player = proxy.getPlayer(getPlayerId());
		
		player.setInventoryItem(getSlot(), getItemId(), false);
		
		return result;
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		boolean result = super.onSending(proxy);
		
		TerrariaPlayer player = proxy.getThePlayer();
		player.setInventoryItem(getSlot(), getItemId(), false);
		
		return result;
	}
	
}
