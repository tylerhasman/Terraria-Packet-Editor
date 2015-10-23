/*

Nice login message :)
Log you in, for servers that use Server Side Characters

*/

var ChatMessage = Java.type("me.tyler.terraria.packets.TerrariaPacketChatMessage");
var Color = Java.type("me.tyler.terraria.TerrariaColor");

function packet_type(){
	return SPAWN_PLAYER;
}

function makeMessage(color, message){
	return new ChatMessage(color, message);
}

function send(packet, proxy, client){

	proxy.sendPacketToClient(client, makeMessage(Color.YELLOW, "------------------------------------------------------------"));
	proxy.sendPacketToClient(client, makeMessage(Color.BLUE, "Thanks for using Terraria Packet Editor! Your character name is "+proxy.getThePlayer().getName()));
	proxy.sendPacketToClient(client, makeMessage(Color.YELLOW, "------------------------------------------------------------"));
	
	proxy.sendPacketToServer(makeMessage(Color.YELLOW, "/register 12345"));
	proxy.sendPacketToServer(makeMessage(Color.YELLOW, "/login 12345"));
	
}