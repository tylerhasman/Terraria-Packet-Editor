var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var HpPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketHealOther");

var command_name = "blockbuff";
var command_description = "toggle on/off blocking debuffs (and buffs)";

function packet_type(){
	return ADD_BUFF;
}

function makeMessage(color, message){

	return new ChatMessage(color, message);

}

function recieve(packet, proxy, client){
	if(enabled){
		return false;//Cancel the packet
	}
}

function send(packet, proxy, client){

}

function chat_command(proxy, client, command, args){

	if(command.equalsIgnoreCase("blockbuff")){
	
		enabled = !enabled;
		
		if(enabled){
			proxy.sendPacketToClient(client, makeMessage(Color.GREEN, "Buff blocking enabled!"));
		}else{
			proxy.sendPacketToClient(client, makeMessage(Color.RED, "Buff blocking disabled!"));
		}
		
		return true;
	
	}

}