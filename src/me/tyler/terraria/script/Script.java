package me.tyler.terraria.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import me.tyler.terraria.PacketType;

public class Script {
	
	private File file;
	private ScriptEngine engine;
	private byte type;
	private boolean cycle;
	
	public Script(String filePath) {
		this(new File("scripts/"+filePath));
	}
	
	public Script(File file) {
		this.file = file;
		cycle = false;
	}
	
	public byte getPacketType(){
		return type;
	}
	
	private BufferedReader getReader(File file) throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		return reader;
	}
	
	public boolean doesCycle() {
		return cycle;
	}
	
	public Object getValue(String name){
		return engine.get(name);
	}
	
	public void execute(SimpleBindings bindings) {
		ScriptEngineManager sem = new ScriptEngineManager();
		engine = sem.getEngineByName("JavaScript");
		
		if(bindings != null){
			engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		}
		
		for(PacketType type : PacketType.values()){
			engine.put(type.name(), type);
		}
		
		BufferedReader reader = null;
		try {
			reader = getReader(file);
			
			engine.eval(reader);
			
			Object obj = engine.get("cycle");
			
			if(obj != null){
				this.cycle = true;
			}
			
			PacketType type = null;
			try {
				type = (PacketType) invoke("packet_type");
				if(type != null){
					this.type = type.getId();	
				}else{
					this.type = -1;
				}
			} catch (NoSuchMethodException e) {
				System.out.println("missing function 'packet_type' for script "+file.getName());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			System.out.println("SCRIPT ERROR ("+file.getName()+"): "+e.getClass().getName()+" "+e.getMessage());
		}finally{
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T invoke(String name, Object... args) throws NoSuchMethodException {
		Invocable inv = (Invocable) engine;
		
		try {
			
			long l = System.currentTimeMillis();
			
			T t = (T) inv.invokeFunction(name, args);
			
			l = System.currentTimeMillis() - l;
			
			if(l > 200){
				System.out.println(file.getName()+" is taking "+l+" milliseconds to execute "+name);
			}
			
			
			return t;
		} catch (NoSuchMethodException e) {
			throw e;
		} catch (Exception e){
			System.out.println("SCRIPT ERROR ("+file.getName()+"): "+e.getClass().getName()+" "+e.getMessage());
		}
		
		return null;
	}
	

	
	public static class CommandDescription {
		
		private String name;
		private String description;
		
		public CommandDescription(String n, String d) {
			name = n;
			description = d;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return description;
		}
		
		@Override
		public String toString() {
			return name+" -> "+description;
		}
	}
	
}
