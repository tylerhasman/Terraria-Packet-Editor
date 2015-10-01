package me.tyler.terraria;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.tyler.terraria.tools.KeyValueFile;

public class TerrariaData {
	
	public static final KeyValueFile BUFFS = new KeyValueFile(new File("data/buffs.txt"));
	public static final KeyValueFile PROJECTILES = new KeyValueFile(new File("data/projectiles.txt"));
	public static final KeyValueFile BOSSES = new KeyValueFile(new File("data/bosses.txt"));
	public static final KeyValueFile ITEMS = new KeyValueFile(new File("data/items.txt"));
	
	public static boolean frameImportantTiles[] = new boolean[419];
	
	static{
		frameImportantTiles[377] = true;
		frameImportantTiles[373] = true;
		frameImportantTiles[375] = true;
		frameImportantTiles[374] = true;
		frameImportantTiles[372] = true;
		frameImportantTiles[358] = true;
		frameImportantTiles[359] = true;
		frameImportantTiles[360] = true;
		frameImportantTiles[361] = true;
		frameImportantTiles[362] = true;
		frameImportantTiles[363] = true;
		frameImportantTiles[364] = true;
		frameImportantTiles[391] = true;
		frameImportantTiles[392] = true;
		frameImportantTiles[393] = true;
		frameImportantTiles[394] = true;
		frameImportantTiles[356] = true;
		frameImportantTiles[334] = true;
		frameImportantTiles[300] = true;
		frameImportantTiles[301] = true;
		frameImportantTiles[302] = true;
		frameImportantTiles[303] = true;
		frameImportantTiles[304] = true;
		frameImportantTiles[305] = true;
		frameImportantTiles[306] = true;
		frameImportantTiles[307] = true;
		frameImportantTiles[308] = true;
		frameImportantTiles[354] = true;
		frameImportantTiles[355] = true;
		frameImportantTiles[324] = true;
		frameImportantTiles[283] = true;
		frameImportantTiles[288] = true;
		frameImportantTiles[289] = true;
		frameImportantTiles[290] = true;
		frameImportantTiles[291] = true;
		frameImportantTiles[292] = true;
		frameImportantTiles[293] = true;
		frameImportantTiles[294] = true;
		frameImportantTiles[295] = true;
		frameImportantTiles[296] = true;
		frameImportantTiles[297] = true;
		frameImportantTiles[316] = true;
		frameImportantTiles[317] = true;
		frameImportantTiles[318] = true;
		frameImportantTiles[410] = true;
		frameImportantTiles[36] = true;
		frameImportantTiles[275] = true;
		frameImportantTiles[276] = true;
		frameImportantTiles[277] = true;
		frameImportantTiles[278] = true;
		frameImportantTiles[279] = true;
		frameImportantTiles[280] = true;
		frameImportantTiles[281] = true;
		frameImportantTiles[282] = true;
		frameImportantTiles[285] = true;
		frameImportantTiles[286] = true;
		frameImportantTiles[414] = true;
		frameImportantTiles[413] = true;
		frameImportantTiles[309] = true;
		frameImportantTiles[310] = true;
		frameImportantTiles[339] = true;
		frameImportantTiles[298] = true;
		frameImportantTiles[299] = true;
		frameImportantTiles[171] = true;
		frameImportantTiles[247] = true;
		frameImportantTiles[245] = true;
		frameImportantTiles[246] = true;
		frameImportantTiles[239] = true;
		frameImportantTiles[240] = true;
		frameImportantTiles[241] = true;
		frameImportantTiles[242] = true;
		frameImportantTiles[243] = true;
		frameImportantTiles[244] = true;
		frameImportantTiles[254] = true;
		frameImportantTiles[237] = true;
		frameImportantTiles[238] = true;
		frameImportantTiles[235] = true;
		frameImportantTiles[236] = true;
		frameImportantTiles[269] = true;
		frameImportantTiles[334] = true;
		frameImportantTiles[390] = true;
		frameImportantTiles[233] = true;
		frameImportantTiles[227] = true;
		frameImportantTiles[228] = true;
		frameImportantTiles[231] = true;
		frameImportantTiles[216] = true;
		frameImportantTiles[217] = true;
		frameImportantTiles[218] = true;
		frameImportantTiles[219] = true;
		frameImportantTiles[220] = true;
		frameImportantTiles[338] = true;
		frameImportantTiles[165] = true;
		frameImportantTiles[209] = true;
		frameImportantTiles[215] = true;
		frameImportantTiles[210] = true;
		frameImportantTiles[212] = true;
		frameImportantTiles[207] = true;
		frameImportantTiles[178] = true;
		frameImportantTiles[184] = true;
		frameImportantTiles[185] = true;
		frameImportantTiles[186] = true;
		frameImportantTiles[187] = true;
		frameImportantTiles[173] = true;
		frameImportantTiles[174] = true;
		frameImportantTiles[139] = true;
		frameImportantTiles[149] = true;
		frameImportantTiles[142] = true;
		frameImportantTiles[143] = true;
		frameImportantTiles[144] = true;
		frameImportantTiles[136] = true;
		frameImportantTiles[137] = true;
		frameImportantTiles[138] = true;
		frameImportantTiles[320] = true;
		frameImportantTiles[380] = true;
		frameImportantTiles[201] = true;
		frameImportantTiles[3] = true;
		frameImportantTiles[4] = true;
		frameImportantTiles[5] = true;
		frameImportantTiles[10] = true;
		frameImportantTiles[11] = true;
		frameImportantTiles[12] = true;
		frameImportantTiles[13] = true;
		frameImportantTiles[14] = true;
		frameImportantTiles[15] = true;
		frameImportantTiles[16] = true;
		frameImportantTiles[17] = true;
		frameImportantTiles[18] = true;
		frameImportantTiles[19] = true;
		frameImportantTiles[20] = true;
		frameImportantTiles[21] = true;
		frameImportantTiles[24] = true;
		frameImportantTiles[26] = true;
		frameImportantTiles[27] = true;
		frameImportantTiles[28] = true;
		frameImportantTiles[29] = true;
		frameImportantTiles[31] = true;
		frameImportantTiles[33] = true;
		frameImportantTiles[34] = true;
		frameImportantTiles[35] = true;
		frameImportantTiles[42] = true;
		frameImportantTiles[50] = true;
		frameImportantTiles[55] = true;
		frameImportantTiles[61] = true;
		frameImportantTiles[71] = true;
		frameImportantTiles[72] = true;
		frameImportantTiles[73] = true;
		frameImportantTiles[74] = true;
		frameImportantTiles[77] = true;
		frameImportantTiles[78] = true;
		frameImportantTiles[79] = true;
		frameImportantTiles[81] = true;
		frameImportantTiles[82] = true;
		frameImportantTiles[83] = true;
		frameImportantTiles[84] = true;
		frameImportantTiles[85] = true;
		frameImportantTiles[86] = true;
		frameImportantTiles[87] = true;
		frameImportantTiles[88] = true;
		frameImportantTiles[89] = true;
		frameImportantTiles[90] = true;
		frameImportantTiles[91] = true;
		frameImportantTiles[92] = true;
		frameImportantTiles[93] = true;
		frameImportantTiles[94] = true;
		frameImportantTiles[95] = true;
		frameImportantTiles[96] = true;
		frameImportantTiles[97] = true;
		frameImportantTiles[98] = true;
		frameImportantTiles[99] = true;
		frameImportantTiles[101] = true;
		frameImportantTiles[102] = true;
		frameImportantTiles[103] = true;
		frameImportantTiles[104] = true;
		frameImportantTiles[105] = true;
		frameImportantTiles[100] = true;
		frameImportantTiles[106] = true;
		frameImportantTiles[110] = true;
		frameImportantTiles[113] = true;
		frameImportantTiles[114] = true;
		frameImportantTiles[125] = true;
		frameImportantTiles[287] = true;
		frameImportantTiles[126] = true;
		frameImportantTiles[128] = true;
		frameImportantTiles[129] = true;
		frameImportantTiles[132] = true;
		frameImportantTiles[133] = true;
		frameImportantTiles[134] = true;
		frameImportantTiles[135] = true;
		frameImportantTiles[172] = true;
		frameImportantTiles[319] = true;
		frameImportantTiles[323] = true;
		frameImportantTiles[335] = true;
		frameImportantTiles[337] = true;
		frameImportantTiles[349] = true;
		frameImportantTiles[376] = true;
		frameImportantTiles[378] = true;
		frameImportantTiles[141] = true;
		frameImportantTiles[270] = true;
		frameImportantTiles[271] = true;
		frameImportantTiles[314] = true;
		frameImportantTiles[395] = true;
		frameImportantTiles[405] = true;
		frameImportantTiles[406] = true;
		frameImportantTiles[411] = true;
		frameImportantTiles[412] = true;
		frameImportantTiles[387] = true;
		frameImportantTiles[386] = true;
		frameImportantTiles[388] = true;
		frameImportantTiles[389] = true;

	}
	
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
