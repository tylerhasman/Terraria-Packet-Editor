package me.tyler.terraria.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class KeyValueFile {

	private File file;
	private Map<Integer, String> map;
	
	public KeyValueFile(File file){
		this.file = file;
		map = new HashMap<>();
		try {
			load(",");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getRandomKey(){
		List<Integer> keys = new ArrayList<>(map.keySet());
		
		Collections.shuffle(keys);
		
		return keys.get(0);
	}
	
	private void load(String seperator) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line;
		
		while((line = reader.readLine()) != null){
			
			String[] parts = line.split(seperator);

			int key = Integer.parseInt(parts[0]);
			String value = parts[1];
			map.put(key, value);	
		}
		
		Set<Integer> sortedKeys = map.keySet().stream().sorted().collect(Collectors.toSet());
		
		HashMap<Integer, String> map2 = new HashMap<>();
		
		for(int key : sortedKeys){
			map2.put(key, map.get(key));
		}
		
		map = map2;
		
		reader.close();
	}
	
	/*public static void main(String[] args) throws IOException {
		
		File file = new File("moreitems.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line;
		
		Map<String, Integer> map = new HashMap<>();
		
		while((line = reader.readLine()) != null){
			
			if(!line.contains(",")){
				continue;
			}
			
			String[] parts = line.split(",");

			int key = Integer.parseInt(parts[1]);
			String value = parts[0];
			
			if(map.containsKey(value)){
				System.out.println("Duplicate value "+value);
				value = value+"@@@@@@";
			}
			
			map.put(value, key);
			
			
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
		
		Map<Integer, String> properMap = new HashMap<>();
		
		for(String str : map.keySet()){
			int id = map.get(str);
			
			properMap.put(id, str);
		}
		
		for(int id : properMap.keySet().stream().sorted().collect(Collectors.toList())){
			String str = properMap.get(id);
			
			writer.write(id+","+str);
			writer.newLine();
		}
		
		writer.flush();
		
		writer.close();
		
	}*/
	
	public String getValue(int key){
		return map.get(key);
	}
	
	public int getKey(String value){
		for(int i : map.keySet()){
			if(map.get(i).equalsIgnoreCase(value)){
				return i;
			}
		}
		return Short.MIN_VALUE;//We use a short because almost all terraria ids are shorts
	}
	
	public int size(){
		return map.size();
	}
	
	public Collection<Integer> keys(){
		return map.keySet();
	}

	public Collection<String> values() {
		return map.values();
	}

	public Map<Integer, String> getValuesLike(String term) {
		
		Map<Integer, String> found = new HashMap<>();
		
		for(int i : map.keySet()){
			
			String value = getValue(i);
			
			if(value.contains(term)){
				found.put(i, value);
			}
			
		}
		
		return found;
	}
	
}
