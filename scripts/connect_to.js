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
			
			var result = 0;
			var error = null;
			
			try{
				result = proxy.reconnectTo(ip, port);
			}catch(e){
				error = e;
				result = -1;
			}
			
			if(result == 1){
				proxy.getThePlayer().sendMessage(Color.GREEN, "Success!");
			}else if(result == -1){
				proxy.getThePlayer().sendMessage(Color.RED, "Error Code "+error.getId()+". "+error.getMessage());
			}
			
		}catch(e){
			e.printStackTrace();
		}
		
		return true;
	
	}

}