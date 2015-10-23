var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var ProjectilePacket = Java.type("me.tyler.terraria.packets.TerrariaPacketProjectileUpdate");
var TogglePvp = Java.type("me.tyler.terraria.packets.TerrariaPacketTogglePvp");
var Data = Java.type("me.tyler.terraria.TerrariaData");
var projectileType = 409;

var command_name = "pvpinstakill";
var command_description = "toggle on/off instantly killing other players in pvp";

function packet_type(){
	return UPDATE_PLAYER;
}

function makeMessage(color, message){
	return new ChatMessage(color, message);
}

function make_packet(x, y, vx, vy, knockback, damage, owner, id){

	var nextId = Data.getFreeProjectileId();
	var packet = ProjectilePacket.getProjectilePacket(nextId, x, y, vx, vy, knockback, damage, owner, id, 0);
	
	return packet;

}

function recieve(packet, proxy, client){
	if(!enabled){
		return;
	}
	
	var player = proxy.getPlayer(packet.getPlayerId());
	
	if(player.isPvpEnabled()){	
		print(player.getName());
		
		var p_packet = make_packet(player.getX(), player.getY(), player.getVelocityX() * 2.5, player.getVelocityY() * 2.5, 0.0, 100, proxy.getThePlayer().getId(), projectileType);
		
		proxy.sendPacketToServer(p_packet);
		proxy.sendPacketToClient(client, p_packet);
	}
}

function send(packet, proxy, client){

}

function chat_command(proxy, client, command, args){

	if(command.equalsIgnoreCase("pvpinstakill")){
	
		enabled = !enabled;
		if(enabled){
			proxy.sendPacketToClient(client, makeMessage(Color.GREEN, "PvP instakill enabled!"));
			var tpvp_packet = new TogglePvp(proxy.getThePlayer().getId(), true);
			
			proxy.sendPacketToServer(tpvp_packet);
			proxy.sendPacketToClient(client, tpvp_packet);
		}else{
			proxy.sendPacketToClient(client, makeMessage(Color.RED, "PvP instakill disabled!"));
			
			var tpvp_packet = new TogglePvp(proxy.getThePlayer().getId(), false);
			
			proxy.sendPacketToServer(tpvp_packet);
			proxy.sendPacketToClient(client, tpvp_packet);
		}
		
		return true;
	
	}

}