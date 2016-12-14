package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.PacketType;
import me.tyler.terraria.Prefix;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaData;
import me.tyler.terraria.TerrariaItemDrop;

public class TerrariaPacketUpdateItemDrop extends TerrariaPacket {

	public TerrariaPacketUpdateItemDrop(byte type, byte[] buffer) {
		super(type, buffer);
	}

	public short getItemId() {
		return getPayloadBuffer().getShort();
	}

	public float getPositionX() {
		return getPayloadBuffer(2).getFloat();
	}

	public float getPositionY() {
		return getPayloadBuffer(6).getFloat();
	}

	public float getVelocityX() {
		return getPayloadBuffer(10).getFloat();
	}

	public float getVelocityY() {
		return getPayloadBuffer(14).getFloat();
	}

	public short getStacks() {
		return getPayloadBuffer(18).getShort();
	}

	public byte getPrefix() {
		return getPayloadBuffer(20).get();
	}

	public byte getNoDelay() {
		return getPayloadBuffer(21).get();
	}

	public short getItemNetId() {
		return getPayloadBuffer(22).getShort();
	}

	@Override
	public boolean onReceive(Proxy proxy) {

		if (getItemNetId() == 0) {
			proxy.removeDroppedItem(getItemId());
			return super.onReceive(proxy);
		}

		TerrariaItemDrop item = new TerrariaItemDrop(proxy, getItemId(), getItemNetId(), getPositionX(), getPositionY(), getVelocityX(), getVelocityY(), getStacks(), getPrefix(), getNoDelay());

		proxy.setDroppedItem(item);
		
		return super.onReceive(proxy);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		if(getItemNetId() == 0){
			proxy.removeDroppedItem(getItemId());
			
			return super.onSending(proxy);
		}
		
		
		return super.onSending(proxy);
	}
	
	public static TerrariaPacketUpdateItemDrop getItemDropPacket(int itemId, float x, float y, float velX, float velY, int stacks, int prefix, int nodelay, int netid) {

		ByteBuffer buf = ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN);

		buf.putShort((short) itemId);
		buf.putFloat(x);
		buf.putFloat(y);
		buf.putFloat(velX);
		buf.putFloat(velY);
		buf.putShort((short) stacks);
		buf.put((byte) prefix);
		buf.put((byte) nodelay);
		buf.putShort((short) netid);

		TerrariaPacketUpdateItemDrop packet = new TerrariaPacketUpdateItemDrop(PacketType.UPDATE_ITEM_DROP.getId(), buf.array());

		return packet;

	}

}
