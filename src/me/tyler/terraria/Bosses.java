package me.tyler.terraria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Bosses {

	private static Map<Short, String> bossNames = new HashMap<>();

	public static void loadBosses() {
		File file = new File("bosses.txt");

		if (!file.exists()) {
			System.out.println("No bosses.txt found!");
			return;
		}
		int lineNum = 1;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = reader.readLine()) != null) {

				String[] splits = line.split(",");
				short id = Short.parseShort(splits[1]);
				String name = splits[0];

				bossNames.put(id, name);
				lineNum++;
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(lineNum);
		}

		System.out.println("Loaded " + bossNames.size() + " boss names!");

	}

	public static String getBossName(short id) {
		return bossNames.get(id);
	}
	
	public static short getBossByName(String name){
		for(short boss : bossNames.keySet()){
			if(bossNames.get(boss).equals(name)){
				return boss;
			}
		}
		
		return -1;
	}

	public static Collection<String> getBossNames() {
		return bossNames.values();
	}
	
}
