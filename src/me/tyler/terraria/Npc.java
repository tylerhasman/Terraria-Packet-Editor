package me.tyler.terraria;

public class Npc {

	private float x, y;
	private short netId;
	private short id;
	
	public Npc(int id) {
		this.id = (short) id;
	}
	
	public short getNetId() {
		return netId;
	}
	
	public short getId() {
		return id;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
}
