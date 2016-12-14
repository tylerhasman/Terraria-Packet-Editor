package me.tyler.terraria.packets;

import me.tyler.terraria.Npc;
import me.tyler.terraria.Proxy;

public class TerrariaPacketUpdateNpc extends TerrariaPacket {

	public TerrariaPacketUpdateNpc(byte type, byte[] payload) {
		super(type, payload);
	}

	public short getId(){
		return getPayloadBuffer().getShort();
	}
	
	public float getPositionX(){
		return getPayloadBuffer(2).getFloat();
	}
	
	public float getPositionY(){
		return getPayloadBuffer(6).getFloat();
	}
	
	public float getVelocityX(){
		return getPayloadBuffer(10).getFloat();
	}
	
	public float getVelocityY(){
		return getPayloadBuffer(14).getFloat();
	}
	
	public byte getTarget(){
		return getPayloadBuffer(18).get();
	}
	
	public byte getFlag(){
		return getPayloadBuffer(19).get();
	}
	
	private int getAiFlags(){
		int amount = 0;
		byte flag = getFlag();
		if((flag & 4) == 4){
			amount++;
		}
		if((flag & 8) == 8){
			amount++;
		}
		if((flag & 16) == 16){
			amount++;
		}
		if((flag & 32) == 32){
			amount++;
		}
		return amount;
	}
	
	public byte[] getAI(){
		byte[] ai = new byte[getAiFlags()];
		
		getPayloadBuffer(20).get(ai);
		
		return ai;
	}
	
	public short getNpcType(){
		return getPayloadBuffer(22).getShort();
	}
	
	private int getLifeBytes(){
		int bytes = getPayload().length - 22 - getAiFlags() - 1;
		
		return bytes;
	}
	
	public boolean isMaxHealth(){
		return (getFlag() & 128) == 128;
	}
	
	public int getHealth(){
		/*if(!isMaxHealth()){
			int bytes = getLifeBytes();
			if(bytes == 1){
				return getPayloadBuffer(24).get();
			}else if(bytes == 2){
				return getPayloadBuffer(24).getShort();
			}else if(bytes == 4){
				return getPayloadBuffer(24).getInt();
			}
		}*/
		return Short.MAX_VALUE;
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		
/*		if(proxy.getNpc(getId()) == null){
			Npc npc = new Npc(getId());
			npc.setX(getPositionX());
			npc.setY(getPositionY());
			npc.setNetId(getNpcType());
			proxy.addNpc(npc);
		}else{
			proxy.getNpc(getId()).setX(getPositionX());
			proxy.getNpc(getId()).setY(getPositionY());
			proxy.getNpc(getId()).setLife(getHealth());
			proxy.getNpc(getId()).setNetId(getNpcType());
		}*/
		
		return super.onReceive(proxy);
	}
	
	
}
