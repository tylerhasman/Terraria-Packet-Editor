package me.tyler.terraria;

import me.tyler.terraria.packets.TerrariaPacketUpdateItemDrop;

public class TerrariaItemDrop {

	private short itemId;
	private short itemType;
	private float x, y;
	private float velX, velY;
	private short amount;
	private byte prefix;
	private byte delay;
	private Proxy proxy;
	
	public TerrariaItemDrop(Proxy proxy, short itemId, short itemType, float x, float y, float velX, float velY, short amount, byte prefix, byte delay) {
		this.itemId = itemId;
		this.itemType = itemType;
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.amount = amount;
		this.prefix = prefix;
		this.delay = delay;
		this.proxy = proxy;
	}
	
	public void teleport(int x, int y){
		TerrariaPacketUpdateItemDrop packet = TerrariaPacketUpdateItemDrop.getItemDropPacket(itemId, x, y, 0, 0, amount, prefix, delay, itemType);
	
		proxy.sendPacketToServer(packet);
		this.x = x;
		this.y = y;
	}
	
	//Servers block us setting item types
/*	public void setItemType(short type){
		TerrariaPacketUpdateItemDrop packet = TerrariaPacketUpdateItemDrop.getItemDropPacket(itemId, x, y, 0, 0, amount, prefix, delay, type);
		
		proxy.sendPacketToServer(packet);
		itemType = type;
	}*/
	
	@Override
	public String toString() {
		return "["+Prefix.getPrefix(prefix)+"] "+TerrariaData.ITEMS.getValue(itemType)+" x "+amount;
	}

	public short getItemId() {
		return itemId;
	}
	
	public short getItemType() {
		return itemType;
	}
	
	public short getAmount() {
		return amount;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
	}
	
	public byte getPrefix() {
		return prefix;
	}
	
	public byte getDelay() {
		return delay;
	}
	
}
