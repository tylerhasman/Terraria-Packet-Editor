var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var Data = Java.type("me.tyler.terraria.TerrariaData");
var BuffPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketAddBuff");

var command_name = "buff";
var command_description = "buff yourself";

function packet_type(){

}

function makeMessage(color, message){

	return new ChatMessage(color, message);

}

function chat_command(proxy, client, command, args){

	if(command.equalsIgnoreCase("buff")){
	
		if(args.length > 0){
			
			var id = args[0];
			var name = Data.BUFFS.getValue(id);
			buff_packet = new BuffPacket(proxy.getThePlayer().getId(), id, 32767);
			proxy.sendPacketToClient(client, buff_packet);
			proxy.sendPacketToClient(client, makeMessage(Color.GREEN, "Added buff "+name));
			
		}else{
			proxy.sendPacketToClient(client, makeMessage(Color.RED, "-buff [buff id] (Do -lookup to find out ids)"));
		}
		
		return true;
	}
}