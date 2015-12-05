
var command_name = "clearinventory";
var command_description = "Clear your inventory";
var Color = Java.type("me.tyler.terraria.TerrariaColor");

function packet_type(){
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("clearinventory")){

		var player = proxy.getThePlayer();
		
		var amount = 0;
		
		for(i = 0; i < 180;i++){
			if(player.getInventoryItem(i) != 0){
				player.setInventoryItem(i, 0);
				amount++;
			}
		}
		
		player.sendMessage(Color.YELLOW, "Removed "+amount+" items from your inventory");
		
		return true;
	}

}