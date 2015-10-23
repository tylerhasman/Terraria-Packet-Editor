/*

This script allows use to edit the players HP and MP, as well as inventory
This is because server side characters must be enabled to perform these actions

*/

var enabled = false;

function packet_type(){
	return WORLD_INFO;
}

function recieve(packet, proxy){
	if(enabled){
		return true;
	}
	if(!packet.isServerSideCharacters()){
		packet.getPayload()[packet.getWorldNameOffset() + 75] += 64;
		print("Server side characters force enabled!");
		enabled = true;
	}
}