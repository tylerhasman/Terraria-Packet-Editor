package me.tyler.terraria;

import me.tyler.terraria.packets.TerrariaPacketAddBuff;
import me.tyler.terraria.packets.TerrariaPacketChatMessage;
import me.tyler.terraria.packets.TerrariaPacketDisconnect;
import me.tyler.terraria.packets.TerrariaPacketInventorySlot;
import me.tyler.terraria.packets.TerrariaPacketKillMe;
import me.tyler.terraria.packets.TerrariaPacketPlayerHp;
import me.tyler.terraria.packets.TerrariaPacketPlayerInfo;
import me.tyler.terraria.packets.TerrariaPacketPortalTeleport;
import me.tyler.terraria.packets.TerrariaPacketStatus;

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
	
	public void sendStatus(String message){
		
		StringBuffer buf = new StringBuffer();
		
		for(int i = 0; i < 11;i++){
			buf.append("\n");
		}
		
		buf.append(message);
		
		for(int i = 0; i < 30;i++){
			buf.append("\n");
		}
		
		proxy.sendPacketToClient(new TerrariaPacketStatus(0, buf.toString()));
	}
	
	@Override
	public void setHealth(int health){
		super.setHealth(health);
		proxy.sendPacketToClient(new TerrariaPacketPlayerHp(getId(), health, getMaxHp()));
	}
	
	@Override
	public void setMaxHealth(int maxHealth){
		super.setMaxHealth(maxHealth);
		proxy.sendPacketToClient(new TerrariaPacketPlayerHp(getId(), getHp(), maxHealth));
	}
	
	@Override
	public void setInventoryItem(int index, int id, boolean update) {
		super.setInventoryItem(index, id, update);
		
		if(update){
			TerrariaPacketInventorySlot slot = new TerrariaPacketInventorySlot(getId(), index, 1, 0, id);
			
			proxy.sendPacketToServer(slot);
		}
	}

	public void teleport(float x, float y) {
		setX(x);
		setY(y);
		
		TerrariaPacketPortalTeleport packet = TerrariaPacketPortalTeleport.getPortalTeleportPacket(getId(), 0, x, y, 0, 0);
		
		proxy.sendPacketToClient(packet);
	}

	public void kill(String reason) {
		TerrariaPacketKillMe packet = TerrariaPacketKillMe.getKillMePacket(getId(), 0, 0, false, reason);
		
		proxy.sendPacketToClient(packet);
	}

	public void kick(String string) {
		TerrariaPacketDisconnect packet = new TerrariaPacketDisconnect(string);
		
		proxy.sendPacketToClient(packet);
	}
	
	@Override
	public void updateInfo() {
		TerrariaPacketPlayerInfo packet = new TerrariaPacketPlayerInfo(getId(), getName(), getInfo());
		
		proxy.sendPacketToClient(packet);
		proxy.sendPacketToServer(packet);
		
	}

}
