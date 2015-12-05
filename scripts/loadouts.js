var command_name = "loadout";
var command_description = "Load or Save a loadout";
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var File = Java.type("java.io.File");
var BWriter = Java.type("java.io.BufferedWriter");
var FileWriter = Java.type("java.io.FileWriter");
var Scanner = Java.type("java.util.Scanner");

function packet_type(){
}

function get_file(name){
	var file = new File(new File("loadouts/"), name);

	return file;
}

function get_writer(file){
	return new BWriter(new FileWriter(file));
}

function get_loadout(name, player){

	var file = get_file(name);
	
	if(!file.exists()){
		player.sendMessage(Color.RED, "No loadout was found named "+name);
		return null;
	}

	var loadout = Array(180);
	
	var scanner = new Scanner(file);
	
	var line;
	
	while(scanner.hasNextLine()){
		line = scanner.nextLine();
		var parts = line.split(":");
		var slot = parts[0];
		var id = parts[1];
		
		loadout[slot] = id;
	}
	
	return loadout;
}

function save_loadout(player, name) {

    player.sendMessage(Color.GREEN, "Saving your loadout to " + name + ", your game may experience a small amount of lag.");

    try {
	
	    var file = get_file(name);
	
		if(!file.exists()){
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
	
		var writer = get_writer(file);

        for (i = 0; i < player.getInventorySize(); i++) {

            var id = player.getInventoryItem(i);
			
			if(id == 0){
				continue;
			}
			
            writer.write(i + ":" + id);
            writer.newLine();

        }

        writer.flush();

        player.sendMessage(Color.GREEN, "Loadout successfully saved!");

    } catch (err) {

        player.sendMessage(Color.RED, "An error occured while saving your loadout!");

        err.printStackTrace();

    } finally {
        writer.close();
    }
}

function string_together(args, index){
	var s = "";
	for(i = index; i < args.length;i++){
		s += args[i] + " ";
	}
	
	return s.substring(0, s.length()-1);
}

function apply_loadout(loadout, player){
	for(i = 0; i < loadout.length;i++){
		var id = loadout[i];	
		player.setInventoryItem(i, id);
	}
				
	player.sendMessage(Color.GREEN, "Loadout applied to your character successfully!");
}


function chat_command(proxy, command, args) {

	if(command.equalsIgnoreCase("loadout")){

		var player = proxy.getThePlayer();
		
		if(args.length < 2){
			player.sendMessage(Color.RED, "Incorrect usage! Example: -loadout [save | load | copy] [loadout name | player name]");
			return true;
		}
		
		var name = string_together(args, 1) + ".loadout";
		
		if(args[0].equalsIgnoreCase("save")){
		
			save_loadout(player, name);
			
		}else if(args[0].equalsIgnoreCase("load")){
			
			var loadout = get_loadout(name);
			
			if(loadout != null){
			
				apply_loadout(loadout, player);
			
			}
			
		}else if(args[0].equalsIgnoreCase("copy")){
			var target_name = string_together(args, 1);
			
			var target = proxy.getPlayer(target_name);
			
			
			if(target == null){
				player.sendMessage(Color.RED, "No player named "+target_name+" found!");
			}else{
				var loadout = target.getInventory();
				
				player.getInfo().clone(target.getInfo());
				try{
				player.updateInfo();
				}catch(e) { e.printStackTrace(); }
				apply_loadout(loadout, player);
			}
			
		}
		
		return true;
	}

}