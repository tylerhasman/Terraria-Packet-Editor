var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var Data = Java.type("me.tyler.terraria.TerrariaData");
var TeleportPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketPortalTeleport");
var Math = Java.type("java.lang.Math");

var command_name = "yoyorocket";
var command_description = "shoot around the map using a yoyo";

var yoyo_ids = [534, 541, 542, 543, 544, 545, 546, 547, 548, 549, 550, 551, 552, 553, 554, 555, 562, 563, 564];
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
	
	for(i = 0; i < yoyo_ids.length;i++){
		if(id == yoyo_ids[i]){
			
			var player = proxy.getThePlayer();
			
			var velx = packet.getX() - player.getX();
			var vely = packet.getY() - player.getY();
			
			velx /= 5;
			vely /= 5;
			
			var tp_packet = TeleportPacket.getPortalTeleportPacket(player.getId(), 0, player.getX(), player.getY(), velx, vely);
			
			player.setX(player.getX() + velx * 2);
			player.setY(player.getY() + vely * 2);
			
			proxy.sendPacketToClient(tp_packet);
			proxy.sendPacketToServer(tp_packet);
			
			break;
		}
	}
	
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("yoyorocket")){
		
		enabled = !enabled;
		
		if(enabled){
			proxy.sendPacketToClient(makeMessage(Color.GREEN, "YoYo rocket enabled!"));
		}else{
			proxy.sendPacketToClient(makeMessage(Color.RED, "YoYo rocket disabled!"));
		}
		
		return true;
	
	}

}