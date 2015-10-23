var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var Data = Java.type("me.tyler.terraria.TerrariaData");

var command_name = "track";
var command_description = "toggle on/off tracking projectiles";

function packet_type(){
	return PROJECTILE_UPDATE;
}

function makeMessage(color, message){

	return new ChatMessage(color, message);

}

function send(packet, proxy){
	if(enabled){
		var id = packet.getProjectileType();
		var name = Data.PROJECTILES.getValue(id);
	
		proxy.sendPacketToClient(makeMessage(Color.YELLOW, name+" - "+id));
	}
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("track")){
	
		enabled = !enabled;
		
		if(enabled){
			proxy.sendPacketToClient(makeMessage(Color.GREEN, "Projectile tracking enabled!"));
		}else{
			proxy.sendPacketToClient(makeMessage(Color.RED, "Projectile tracking disabled!"));
		}
		
		return true;
	
	}

}