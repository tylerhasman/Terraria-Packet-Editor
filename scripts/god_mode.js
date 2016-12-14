var enabled = false;
var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var HpPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketHealOther");

var command_name = "god";
var command_description = "toggle on/off invincibility";

function packet_type(){
}

function do_cycle(proxy){

	if(!enabled){
		return;
	}

	player = proxy.getThePlayer();

	if(player.getHp() < player.getMaxHp()){
		player.setHealth(player.getMaxHp());
		player.addBuff(175);
	}
	
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("god")){
	
		enabled = !enabled;
		if(enabled){
			proxy.getThePlayer().sendMessage(Color.GREEN, "God mode enabled!");
		}else{
			proxy.getThePlayer().sendMessage(Color.RED, "God mode disabled!");
		}
		
		proxy.getThePlayer().sendStatus("God Mode is "+(enabled ? "enabled" : "disabled"));
		
		return true;
	
	}

}