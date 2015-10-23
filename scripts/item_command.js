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

function chat_command(proxy, client, command, args){

	if(command.equalsIgnoreCase("item")){
	
		if(args.length == 1){
		
			var id = args[0];
			
			var packet = new ItemPacket(proxy.getThePlayer().getId(), 0, 1, 0, id);
			
			proxy.sendPacketToClient(client, packet);
		
			proxy.sendPacketToClient(client, makeMessage(Color.GREEN, "Done!]"));
		
		}else{
			proxy.sendPacketToClient(client, makeMessage(Color.RED, "-item [item id]"));
		}
		
		return true;
	
	}

}