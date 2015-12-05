package me.tyler.terraria.script;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.script.Script.CommandDescription;

public class ScriptManager {

	private List<Script> loaded;
	private File scriptFolder;
	
	public ScriptManager(File folder) {
		loaded = new ArrayList<>();
		scriptFolder = folder;
	}
	
	public int loadScripts(){
		return loadScripts(scriptFolder);
	}
	
	public int loadScripts(File file){
		
		for(File child : scriptFolder.listFiles()){
			if(child.isDirectory()){
				loadScripts(child);
			}else{
				if(child.getName().endsWith(".js")){
					Script script = new Script(child);
					script.execute(null);
					
					loaded.add(script);	
				}
			}
		}
		
		return loaded.size();
	}
	
	public List<CommandDescription> getCommands(){
		List<CommandDescription> commands = new ArrayList<>();
		
		for(Script script : getAllScripts()){
			String name = (String) script.getValue("command_name");
			String desc = (String) script.getValue("command_description");
			
			if(name == null){
				continue;
			}
			
			CommandDescription command = new CommandDescription(name, desc);
			
			commands.add(command);
		}
		
		return commands;
	}
	
	public List<Script> getScriptsForPacket(PacketType type){
		List<Script> scripts = new ArrayList<>(loaded);
		
		scripts.removeIf(script -> script.getPacketType() != type.getId());
		
		return scripts;
	}
	
	public int reload() {
		loaded.clear();
		
		return loadScripts(scriptFolder);
	}
	
	public List<Script> getAllScripts(){
		return loaded;
	}

}
