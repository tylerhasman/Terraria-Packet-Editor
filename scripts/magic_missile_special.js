var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var Data = Java.type("me.tyler.terraria.TerrariaData");
var ProjectilePacket = Java.type("me.tyler.terraria.packets.TerrariaPacketProjectileUpdate");

var command_name = "magicmissile";
var command_description = "add a special effect to your magic missile!";

var projectile_id = 0;
var magic_missile_id = 16;
var projectile_type = -1;
var enabled = false;

var missile_chat_text = "[i/s113:113]";

function packet_type(){
	return PROJECTILE_UPDATE;
}

function make_packet(pid, x, y, vx, vy, knockback, damage, owner, id, proxy){

	var nextId = proxy.getFreeProjectileId();
	var packet = ProjectilePacket.getProjectilePacket(pid, x, y, vx, vy, knockback, damage, owner, id, 0);
	
	return packet;

}

function makeMessage(color, message){

	return new ChatMessage(color, message);

}

function send(packet, proxy){
	var id = packet.getProjectileType();
	
	if(id == magic_missile_id){
		if(enabled){
			projectile_id = packet.getProjectileId();
			var proj_packet = make_packet(projectile_id+1, packet.getX(), packet.getY(), packet.getVelocityX(), packet.getVelocityY(), packet.getKnockback(), 32767, packet.getOwner(), projectile_type, proxy);
			proxy.sendPacketToServer(proj_packet);
			proxy.sendPacketToClient(proj_packet);

			return false;
		}
	}
	
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("magicmissile")){
	
		if(args.length > 0){
			var id = args[0];
			
			if(id < 0){
				proxy.sendPacketToClient(makeMessage(Color.RED, "Magic Missile disabled!"));
				enabled = false;
			}else{
				var name = Data.PROJECTILES.getValue(id);
				projectile_type = id;
				enabled = true;
				proxy.sendPacketToClient(makeMessage(Color.GREEN, missile_chat_text+" Magic Missile set to "+name+" "+missile_chat_text));
			}
			
		}else{
			proxy.sendPacketToClient(makeMessage(Color.RED, "-magicmissile [projectile id]"));
		}
		
		return true;
	
	}

}