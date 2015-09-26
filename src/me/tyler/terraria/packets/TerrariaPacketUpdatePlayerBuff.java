package me.tyler.terraria.packets;

public class TerrariaPacketUpdatePlayerBuff extends TerrariaPacket{

	public TerrariaPacketUpdatePlayerBuff(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public int[] getBuffs(){
		int[] buffs = new int[11];
		for(int i = 0; i < 11;i++){
			buffs[i] = getPayloadBuffer().get(i + 1) & 0xFF;
		}
		
		return buffs;
	}
	
	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}

}
