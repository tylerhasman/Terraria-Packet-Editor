var enabled = false;
var Color = Java.type("me.tyler.terraria.TerrariaColor");

var command_name = "vac";
var command_description = "Pull dropped items towards you";

function packet_type(){
}

function do_cycle(proxy){
	
	if(!enabled){
		return;	
	}
	
	items = proxy.getDroppedItems();
	iterator = items.iterator();
	
	while(iterator.hasNext()){
		
		item = iterator.next();
		
		item.teleport(proxy.getThePlayer().getX(), proxy.getThePlayer().getY());
		
	}
	
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("vac")){
	
		enabled = !enabled;
		
		if(enabled){
			proxy.getThePlayer().sendMessage(Color.GREEN, "VAC enabled!");
		}else{
			proxy.getThePlayer().sendMessage(Color.RED, "VAC disabled!");
		}
		
		return true;
	
	}

}
