package me.tyler.terraria;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TerrariaData {
	
	public static final KeyValueFile BUFFS = new KeyValueFile(new File("data/buffs.txt"));
	public static final KeyValueFile PROJECTILES = new KeyValueFile(new File("data/projectiles.txt"));
	public static final KeyValueFile BOSSES = new KeyValueFile(new File("data/bosses.txt"));
	public static final KeyValueFile ITEMS = new KeyValueFile(new File("data/items.txt"));
	
	/**
	 * A list of all current projectile ids currently in use
	 */
	public static List<Short> proj_ids_in_use = new ArrayList<>();
	
	public static short getFreeProjectileId(){
		
		for(short s = 0; s < Short.MAX_VALUE;s++){
			
			if(!proj_ids_in_use.contains(s)){
				
				proj_ids_in_use.add(s);
				
				return s;
			}
			
		}
		
		//This should realistically never happen
		throw new RuntimeException("No new projectile ids available!");
		
	}
	
	
	
	//public static int next_projectile_id = 0;
	
	
}
