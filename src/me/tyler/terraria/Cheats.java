package me.tyler.terraria;

import java.util.HashMap;
import java.util.Map;

public class Cheats {

	public static boolean VAC_ENABLED = false;
	public static boolean VAC_POS_ENABLED = false;
	public static float VAC_POS_X = 0, VAC_POS_Y = 0;
	public static boolean BLOCK_DAMAGE = false;
	
	public static Map<Short, Short> replacer = new HashMap<>();

	public static short PROJECTILE_REPLACER_OTHER_TO = -1;
	public static short PROJECTILE_REPLACER_TO_DAMAGE = 1000;
	
}
