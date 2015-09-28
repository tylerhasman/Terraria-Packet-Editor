package me.tyler.terraria.packets;

public class TerrariaPacketUpdatePlayerBuff extends TerrariaPacket{

	private static final int MAX_BUFFS = 22;
	
	public TerrariaPacketUpdatePlayerBuff(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public int[] getBuffs(){
		int[] buffs = new int[MAX_BUFFS];
		for(int i = 0; i < buffs.length;i++){
			buffs[i] = getPayloadBuffer().get(i + 1) & 0xFF;
		}
		
		return buffs;
	}
	
	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}

}
