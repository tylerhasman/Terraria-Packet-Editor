/*

This script allows use to edit the players HP and MP, as well as inventory
This is because server side characters must be enabled to perform these actions

*/

var EventInfo = Java.type("me.tyler.terraria.EventInfo");

var enabled = true;

function packet_type(){
	return WORLD_INFO;
}

function recieve(packet, proxy){
	if(!enabled){
		return true;
	}
	proxy.getWorldInfo().setEventInfoState(EventInfo.SERVER_SIDE_CHARACTER, true);
}