var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var EmotePacket = Java.type("me.tyler.terraria.packets.TerrariaPacketEmoteBubble");
var Emotes = Java.type("me.tyler.terraria.Emote");

var Random = Java.type("java.util.Random");

var command_name = "emote";
var command_description = "make everyone emote a random emote! (Only you can see it)";

function packet_type(){
	return EMOTE_BUBBLE;
}

function makeMessage(color, message){
	return new ChatMessage(color, message);
}


function receive(packet, proxy){

	var id = packet.getEmote();
	var emote = Emotes.getbyId(id);
	var sender = proxy.getMetadata();
	
	var player = proxy.getPlayer(sender);
	
	proxy.getThePlayer().sendMessage(sender.getName()+" emotes "+emote.name());
	
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("emote")){
	
		var rng = new Random();
	
		for each(player in proxy.getPlayers()){
			var packet = new EmotePacket(1, player.getId(), 255, rng.nextInt(126));
			proxy.sendPacketToClient(packet);
		}
		
		proxy.sendPacketToClient(makeMessage(Color.GREEN, "Done!"));
		
		return true;
	
	}

}