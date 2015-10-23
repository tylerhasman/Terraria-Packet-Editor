package me.tyler.terraria;

import me.tyler.terraria.packets.TerrariaPacketAddBuff;
import me.tyler.terraria.packets.TerrariaPacketChatMessage;
import me.tyler.terraria.packets.TerrariaPacketPlayerHp;

public class TerrariaPlayerLocal extends TerrariaPlayer {
	
	public TerrariaPlayerLocal(byte id, Proxy proxy) {
		super(id, proxy);
	}
	
	public void sendMessage(String message){
		sendMessage(TerrariaColor.BLUE, message);
	}

	public void sendMessage(TerrariaColor color, String message) {
		sendMessage(0xFF, color, message);
	}

	private void sendMessage(int i, TerrariaColor color, String message) {
		proxy.sendPacketToClient(new TerrariaPacketChatMessage(i, color, message));
	}
	
	public void addBuff(int id){
		addBuff(id, Short.MAX_VALUE);
	}
	
	public void addBuff(int id, int time){
		proxy.sendPacketToClient(new TerrariaPacketAddBuff(getId(), id, time));
	}
	
	public void chat(String message){
		proxy.sendPacketToServer(new TerrariaPacketChatMessage(getId(), TerrariaColor.WHITE, message));
	}
	
	@Override
	public void setHealth(int health){
		setHealth(health);
		proxy.sendPacketToClient(new TerrariaPacketPlayerHp(getId(), health, getMaxHp()));
	}
	
	@Override
	public void setMaxHealth(int maxHealth){
		setMaxHealth(maxHealth);
		proxy.sendPacketToClient(new TerrariaPacketPlayerHp(getId(), getHp(), maxHealth));
	}

}
