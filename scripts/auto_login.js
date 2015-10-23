/*

Nice login message :)
Log you in, for servers that use Server Side Characters

*/

var Packet = Java.type("me.tyler.terraria.packets.TerrariaPacket");
var Color = Java.type("me.tyler.terraria.TerrariaColor");

function packet_type(){

}

function game_state_ready(proxy){

	proxy.getThePlayer().sendMessage(Color.YELLOW, "------------------------------------------------------------");
	proxy.getThePlayer().sendMessage(Color.BLUE, "Thanks for using Terraria Packet Editor!");
	proxy.getThePlayer().sendMessage(Color.BLUE, "Created by [c/1BF095:Tyler]");
	proxy.getThePlayer().sendMessage(Color.YELLOW, "------------------------------------------------------------");
	
	proxy.getThePlayer().chat("/register 12345");
	proxy.getThePlayer().chat("/login 12345");
	
	print("Logging in with password 12345");

}