var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var BuffPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketAddBuff");
var featherFallPotionID = 8;

var command_name = "fall";
var command_description = "toggle on/off fall protection";

function packet_type(){
	return UPDATE_PLAYER;
}

function makeMessage(color, message){
	return new ChatMessage(color, message);
}

function recieve(packet, proxy, client){

}

function send(packet, proxy, client){
	if(enabled){
		if(packet.getVelocityY() > 5.0){
			proxy.sendPacketToClient(client, new BuffPacket(proxy.getThePlayer().getId(), featherFallPotionID, 500));
		}
	}
}

function chat_command(proxy, client, command, args){

	if(command.equalsIgnoreCase("fall")){
	
		enabled = !enabled;
		if(enabled){
			proxy.sendPacketToClient(client, makeMessage(Color.GREEN, "Fall protection enabled!"));
		}else{
			proxy.sendPacketToClient(client, makeMessage(Color.RED, "Full protection disabled!"));
		}
		
		return true;
	
	}

}