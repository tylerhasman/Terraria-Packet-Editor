var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var TeleportPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketPortalTeleport");
var ManaPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketManaEffect");
var DestroyPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketDestroyProjectile");

var command_name = "hydratp";
var command_description = "teleport to wherever you place a frost hydra";

var hydra_id = 308;
var enabled = false;

function packet_type(){
	return PROJECTILE_UPDATE;
}

function makeMessage(color, message){
	return new ChatMessage(color, message);
}

function send(packet, proxy){

	if(!enabled){
		return;
	}

	var id = packet.getProjectileType();
	
	if(id == hydra_id){
	
		var player = proxy.getThePlayer();
		
		var tp_packet = TeleportPacket.getPortalTeleportPacket(player.getId(), 0, packet.getX(), packet.getY(), player.getVelocityX(), player.getVelocityY());
		var destroy_packet = new DestroyPacket(packet.getProjectileId(), player.getId());
		
		proxy.sendPacketToClient(tp_packet);
		proxy.sendPacketToClient(destroy_packet);
		
		player.addBuff(178, 1000);
		
		return false;
	}
	
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("hydratp")){
		
		enabled = !enabled;
		
		if(enabled){
			proxy.sendPacketToClient(makeMessage(Color.GREEN, "Hydra Teleport enabled!"));
		}else{
			proxy.sendPacketToClient(makeMessage(Color.RED, "Hydra Teleport disabled!"));
		}
		
		return true;
	
	}

}