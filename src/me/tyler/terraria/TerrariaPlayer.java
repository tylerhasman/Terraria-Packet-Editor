package me.tyler.terraria;

import me.tyler.terraria.packets.TerrariaPacketUpdatePlayer.Control;

public class TerrariaPlayer {

	private byte id;
	private String name;
	private float x, y;
	private float velocityX, velocityY;
	private boolean pvpEnabled;
	private Control[] controls;
	private boolean facingLeft;
	private int hp, maxHp;
	protected Proxy proxy;
	
	public TerrariaPlayer(byte id, Proxy proxy) {
		this.id = id;
		name = "<not set>";
		controls = new Control[0];
		facingLeft = false;
		this.proxy = proxy;
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
	
	public int getHp() {
		return hp;
	}
	
	public int getMaxHp() {
		return maxHp;
	}
	
	public void setHealth(int hp) {
		this.hp = hp;
	}
	
	public void setMaxHealth(int maxHp) {
		this.maxHp = maxHp;
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
	
	public boolean isFacingLeft() {
		return facingLeft;
	}
	
	public float distance(float x, float y){
		float x3 = (float) Math.pow(getX() - x, 2);
		float y3 = (float) Math.pow(getY() - y, 2);

		float distance = (float) Math.sqrt(x3 + y3);
		
		return distance;
	}

	public void setControls(Control[] controls) {
		this.controls = controls;
	}
	
	public void cycle(){
		for(Control control : controls){
			if(control == Control.LEFT){
				x -= 90;
				facingLeft = true;
			}
			if(control == Control.RIGHT){
				x += 90;
				facingLeft = false;
			}
		}
	}
	
}
