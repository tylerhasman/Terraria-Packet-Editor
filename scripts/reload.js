var Script = Java.type("me.tyler.terraria.script.Script");
var Color = Java.type("me.tyler.terraria.TerrariaColor");

var command_name = "reload";
var command_description = "reload all scripts";

function packet_type(){
	return ADD_BUFF;
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("reload")){
		
		var i = Script.reload();
		
		proxy.getThePlayer().sendMessage(Color.GREEN, "Reloaded "+i+" scripts");
		
		return true;
	
	}

}