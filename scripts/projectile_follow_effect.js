/*
This script lets you tag players with a projectile and it will follow them around
*/

var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var Data = Java.type("me.tyler.terraria.TerrariaData");
var HashMap = Java.type("java.util.HashMap");
var ProjectilePacket = Java.type("me.tyler.terraria.packets.TerrariaPacketProjectileUpdate");

var command_name = "effect";
var command_description = "Add or remove an projectile effect for a player";

var particles = new HashMap();

function packet_type(){
	return UPDATE_PLAYER;
}
/*
function do_cycle(proxy){
	for each (player in proxy.getPlayers()){
		if(particles.containsKey(player.getName())){
			spawn_particles(player, proxy);
		}
	}
	if(particles.containsKey(proxy.getThePlayer().getName())){
	
		for(i = 0; i < 4;i++){
			
		}
	
		spawn_particles(proxy.getThePlayer(), proxy);
	}
}*/

function distance(p1, p2){
	return p1.distance(p2.getX(), p2.getY());
}

function make_packet(proxy, x, y, vx, vy, knockback, damage, owner, id){
	var nextId = proxy.getFreeProjectileId();
	var packet = ProjectilePacket.getProjectilePacket(nextId, x, y, vx, vy, knockback, damage, owner, id, 0);
	
	return packet;
}

function spawn_particles(player, proxy){
	var id = particles.get(player.getName());
	var p_packet = make_packet(proxy, player.getX(), player.getY(), player.getVelocityX(), player.getVelocityY(), 0, 0, proxy.getThePlayer().getId(), id);
	
	if(distance(player, proxy.getThePlayer()) < 3000){
		proxy.sendPacketToClient(p_packet);
	}
	
	proxy.sendPacketToServer(p_packet);
	
}

function makeMessage(color, message){

	return new ChatMessage(color, message);

}

function recieve(packet, proxy){
	var player = proxy.getPlayer(packet.getPlayerId());
	if(particles.containsKey(player.getName())){
		spawn_particles(player, proxy);
	}
}

function send(packet, proxy){
	var player = proxy.getPlayer(packet.getPlayerId());
	if(particles.containsKey(player.getName())){
		spawn_particles(player, proxy);
	}
}


function add_particle(player_name, particle_id){
	particles.put(player_name, particle_id);
}

function remove_particle(player_name){
	particles.remove(player_name);
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("effect")){
	
		if(args.length < 2){
			proxy.sendPacketToClient(makeMessage(Color.RED, "-effect [effect id] [player name]"));
		}else{
			var id = args[0];
			var projectile_name = Data.PROJECTILES.getValue(id);
			var name = "";
			
			for(i = 1; i < args.length;i++){
				name += args[i] + " ";
			}
			
			name = name.substring(0, name.length()-1);
			
			var player = proxy.getPlayer(name);
			
			if(player != null){
				if(id < 0){
					remove_particle(player.getName());
					proxy.sendPacketToClient(makeMessage(Color.BLUE, "Particle effect cleared for "+player.getName()));
				}else{
					add_particle(player.getName(), id);
					proxy.sendPacketToClient(makeMessage(Color.GREEN, projectile_name+" tagged to "+player.getName()));
				}
			}else{
				proxy.sendPacketToClient(makeMessage(Color.RED, "No player found named "+name));
			}
			
		}
		
		return true;
	
	}

}