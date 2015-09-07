package me.tyler.terraria;

public class TerrariaPlayer {

	private byte id;
	private String name;
	private float x, y;
	
	public TerrariaPlayer(byte id) {
		this.id = id;
		name = "<not set>";
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public byte getId() {
		return id;
	}
	
}
