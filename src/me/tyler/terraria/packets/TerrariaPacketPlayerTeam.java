package me.tyler.terraria.packets;

public class TerrariaPacketPlayerTeam extends TerrariaPacket {

	public TerrariaPacketPlayerTeam(byte type, byte[] payload) {
		super(type, payload);
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public byte getTeam(){
		return getPayloadBuffer(1).get();
	}
	
	public static String getTeamName(byte id){
		
		if(id == 0){
			return "White";
		}else if(id == 1){
			return "Red";
		}else if(id == 2){
			return "Green";
		}else if(id == 3){
			return "Blue";
		}else if(id == 4){
			return "Orange";
		}else if(id == 5){
			return "Purple";
		}
		
		return "Unknown "+id;
	}
	
}
