package me.tyler.terraria;

import javax.annotation.Generated;

public class Npc {

	private float x, y;
	private short netId;
	private short id;
	private int life;
	
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
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	
	public void setNetId(short netId) {
		this.netId = netId;
	}
	
}
