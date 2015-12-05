var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var ItemPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketInventorySlot");

var command_name = "item";
var command_description = "add a item into your inventory";

function packet_type(){
}

function makeMessage(color, message){
	return new ChatMessage(color, message);
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("item")){
	
		if(args.length > 0){
		
			var id = args[0];
			var amount = 1;
			
			if(args.length > 1){
				amount = args[1];
			}
			
			
			
			var packet = new ItemPacket(proxy.getThePlayer().getId(), 0, amount, 0, id);
			
			proxy.sendPacketToClient(packet);
		
			proxy.sendPacketToClient(makeMessage(Color.GREEN, "Done!]"));
		
		}else{
			proxy.sendPacketToClient(makeMessage(Color.RED, "-item [item id] [amount]"));
		}
		
		return true;
	
	}

}