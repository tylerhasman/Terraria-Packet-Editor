package me.tyler.terraria;

public enum InventorySlot {

	HELD_ITEM(58),
	HELMET(59),
	CHESTPLATE(60),
	LEGGINGS(61),
	VANITY_HELMET(69),
	VANITY_CHESTPLATE(70),
	VANITY_LEGGINGS(71)
	;
	
	private final int slot;
	
	private InventorySlot(int slot) {
		this.slot = slot;
	}
	
	 public int getSlot() {
		return slot;
	}
	
}
