package me.tyler.terraria;

public enum EventInfo{
	
	SHADOW_ORB_SMASHED(1, 1),
	DOWNED_BOSS(1, 2),
	DOWNED_BOSS_2(1, 4),
	DOWNED_BOSS_3(1, 8),
	HARD_MODE(1, 16),
	DOWNED_CLOWN(1, 32),
	SERVER_SIDE_CHARACTER(1, 64),
	DOWNED_PLANT_BOSS(1, 128),
	MECH_BOSS_DOWNED(2, 1),
	MECH_BOSS_DOWNED_2(2, 2),
	MECH_BOSS_DOWNED_3(2, 4),
	MECH_BOSS_ANY_DOWNED(2, 8),
	CLOUD_BG(2, 16),
	CRIMSON(2, 32),
	PUMPKIN_MOON(2, 64),
	SNOW_MOON(2, 128),
	EXPERT_MODE(3, 1),
	FAST_FORWARD_TIME(3, 2),
	SLIME_RAIN(3, 4),
	DOWNED_SLIME_KING(3, 8),
	DOWNED_QUEEN_BEE(3, 16),
	DOWNED_FISHRON(3, 32),
	DOWNED_MARTIANS(3, 64),
	DOWNED_ANCIENT_CULTIST(3, 128),
	MOON_LORD_DOWNED(4, 1),
	HALLOWEEN_KING_DOWNED(4, 2),
	HALLOWEEN_TREE_DOWNED(4, 4),
	CHRISTMAS_ICE_QUEEN(4, 8),
	CHRISTMAS_SANTANK_DOWNED(4, 16),
	CHRISTMAS_TREE_DOWNED(4, 32);
	
	private final int id;
	private final int bit;
	
	private EventInfo(int i, int b) {
		id = i;
		bit = b;
	}
	
	public int getId() {
		return id;
	}
	
	public int getBit() {
		return bit;
	}
}