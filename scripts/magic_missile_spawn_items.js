var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var Data = Java.type("me.tyler.terraria.TerrariaData");
var ProjectilePacket = Java.type("me.tyler.terraria.packets.TerrariaPacketProjectileUpdate");

var command_name = "itemwand";
var command_description = "Make items chase your magic missile!";

var magic_missile_id = 16;
var enabled = false;

var missile_chat_text = "[i/s113:113]";

var x = 0;
var y = 0;

function packet_type(){
	return PROJECTILE_UPDATE;
}

function makeMessage(color, message){
	return new ChatMessage(color, message);
}

function send(packet, proxy){
	var id = packet.getProjectileType();
	
	if(id == magic_missile_id){
		if(enabled){
			x = packet.getX();
			y = packet.getY();
			
			for(i=0; i < 1;i++){
				proxy.dropItem(x, y, Data.ITEMS.getRandomKey());
			}
			
			return false;
		}
	}
	
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("itemwand")){
	
		enabled = !enabled;
			
		if(!enabled){
			proxy.sendPacketToClient(makeMessage(Color.RED, "Item wand disabled!"));
		}else{
			proxy.sendPacketToClient(makeMessage(Color.GREEN, missile_chat_text+" Item wand enabled!"));
		}
		
		return true;
	}

}