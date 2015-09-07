package me.tyler.terraria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Items {

	private static Map<Short, String> itemNames = new HashMap<>();
	
	public static void loadItems() {
		File file = new File("items.txt");
		
		if(!file.exists()){
			System.out.println("No items.txt found!");
			return;
		}
		int lineNum = 1;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line;
			
			
			
			while((line = reader.readLine()) != null){
				
				String[] splits = line.split(",");
				short id = Short.parseShort(splits[0]);
				String name = splits[1];
				
				itemNames.put(id, name);
				lineNum++;
			}
			
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(lineNum);
		}
		
		
		System.out.println("Loaded "+itemNames.size()+" item names!");
	
	}
	
	public static String getItemName(short id){
		return itemNames.get(id);
	}
	
}
