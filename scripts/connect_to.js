var Color = Java.type("me.tyler.terraria.TerrariaColor");

var command_name = "connect";
var command_description = "connect to another server";

function packet_type(){
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("connect")){

	
		try{
			
			var ip = args[0];
			var port = 7777;
			
			if(args.length > 1){
				port = args[1];
			}
			
			
			proxy.getThePlayer().sendMessage(Color.YELLOW, "Connecting you to "+ip+":"+port+", things might get weird");
			
			var result = proxy.reconnectTo(ip, port);
			
			if(result == 1){
				proxy.getThePlayer().sendMessage(Color.GREEN, "Success!");
			}else if(result == -1){
				proxy.getThePlayer().sendMessage(Color.RED, "Failed to connect, connection timed out!");
			}else if(result == -2){
				proxy.getThePlayer().sendMessage(Color.RED, "Failed to connect, server sent strange info.");
			}else if(result == -3){
				proxy.getThePlayer().sendMessage(Color.RED, "Failed to connect, no response from server.");
			}else if(result == -4){
				proxy.getThePlayer().sendMessage(Color.RED, "Failed to connect, io error.");
			}else if(result == -5){
				proxy.getThePlayer().sendMessage(Color.RED, "No server could be found with the ip: "+ip);
			}else{
				proxy.getThePlayer().sendMessage(Color.RED, "Failed to connect. Unknown error code "+result);
			}
			
		}catch(e){
			e.printStackTrace();
		}
		
		return true;
	
	}

}