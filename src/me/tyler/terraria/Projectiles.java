package me.tyler.terraria;

public enum Projectiles {

	ARCANE_MISSLE(16);	
	
	private final short type;
	
	
	private Projectiles(int type) {
		this.type = (short) type;
	}
	
	public short getType() {
		return type;
	}
	
}
