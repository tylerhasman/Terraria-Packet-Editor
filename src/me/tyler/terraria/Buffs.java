package me.tyler.terraria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Buffs {

	private static Map<Short, String> buffNames = new HashMap<>();

	public static void loadBuffs() {
		File file = new File("buffs.txt");

		if (!file.exists()) {
			System.out.println("No buffs.txt found!");
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

				buffNames.put(id, name);
				lineNum++;
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(lineNum);
		}

		System.out.println("Loaded " + buffNames.size() + " buff names!");

	}

	public static String getBuffName(short id) {
		return buffNames.get(id);
	}
}
