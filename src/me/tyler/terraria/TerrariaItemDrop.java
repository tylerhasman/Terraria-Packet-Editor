package me.tyler.terraria;

public class TerrariaItemDrop {

	private short itemId;
	private short itemType;
	private float x, y;
	private float velX, velY;
	private short amount;
	private byte prefix;
	private byte delay;
	
	
	public TerrariaItemDrop(short itemId, short itemType, float x, float y, float velX, float velY, short amount, byte prefix, byte delay) {
		this.itemId = itemId;
		this.itemType = itemType;
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.amount = amount;
		this.prefix = prefix;
		this.delay = delay;
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
