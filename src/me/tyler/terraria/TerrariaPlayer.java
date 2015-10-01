package me.tyler.terraria;

public class TerrariaPlayer {

	private byte id;
	private String name;
	private float x, y;
	private float velocityX, velocityY;
	private boolean pvpEnabled;
	
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
	
	public boolean isPvpEnabled() {
		return pvpEnabled;
	}
	
	public void setPvpEnabled(boolean pvpEnabled) {
		this.pvpEnabled = pvpEnabled;
	}
	
	public String getName() {
		return name;
	}
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public byte getId() {
		return id;
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	public float distance(float x, float y){
		float x3 = (float) Math.pow(getX() - x, 2);
		float y3 = (float) Math.pow(getY() - y, 2);

		float distance = (float) Math.sqrt(x3 + y3);
		
		return distance;
	}
	
}
