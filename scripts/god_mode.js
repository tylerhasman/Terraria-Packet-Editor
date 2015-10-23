var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var HpPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketHealOther");

var command_name = "god";
var command_description = "toggle on/off invincibility";

function packet_type(){
	return PLAYER_HP;
}

function makeMessage(color, message){

	return new ChatMessage(color, message);

}

function recieve(packet, proxy, client){

}

function send(packet, proxy, client){
	if(enabled){
		var id = proxy.getThePlayer().getId();
		var p2 = HpPacket.getHealthOtherPacket(id, packet.getMaxLife()-packet.getLife());
		
		proxy.sendPacketToClient(client, p2);
	}
}

function chat_command(proxy, client, command, args){

	if(command.equalsIgnoreCase("god")){
	
		enabled = !enabled;
		if(enabled){
			proxy.sendPacketToClient(client, makeMessage(Color.GREEN, "God mode enabled!"));
		}else{
			proxy.sendPacketToClient(client, makeMessage(Color.RED, "God mode disabled!"));
		}
		
		return true;
	
	}

}