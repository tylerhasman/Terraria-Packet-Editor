package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaPlayer;

public class TerrariaPacketUpdatePlayer extends TerrariaPacket {

	private static int nextId = 50;
	
	public TerrariaPacketUpdatePlayer(byte type, byte[] payload) {
		super(type, payload);
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public byte getControl(){
		return getPayloadBuffer(1).get();
	}
	
	public byte getPulley(){
		return getPayloadBuffer(2).get();
	}
	
	public byte getSelectedItem(){
		return getPayloadBuffer(3).get();
	}
	
	public float getPositionX(){
		return getPayloadBuffer(4).getFloat();
	}
	
	public float getPositionY(){
		return getPayloadBuffer(8).getFloat();
	}
	
	public float getVelocityX(){
		if(getPulley() >= 4){
			return getPayloadBuffer(12).getFloat();
		}
		return -1;
	}
	
	public float getVelocityY(){
		if(getPulley() >= 4){
			return getPayloadBuffer(16).getFloat();
		}
		return -1;
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		TerrariaPlayer player = proxy.getThePlayer();
		
		player.setX(getPositionX());
		player.setY(getPositionY());
		
		if((getPulley() & 4) > 0){
			player.setVelocityX(getVelocityX());
			player.setVelocityY(getVelocityY());
		}
		
		proxy.setConnectionIniatializationDone(true);
		
		/*if(player.isPvpEnabled() && Cheats.PVP_INSTAKILL){
			for(int i = 0; i < 5;i++){
				
				TerrariaPacket packet = TerrariaPacketProjectileUpdate.getProjectilePacket(nextId, player.getX(), player.getY(), player.getVelocityX() * 2, player.getVelocityY() * 2, 5.0F, 1000, getPlayerId()-1, 132, 0);
				
				proxy.sendPacketToServer(packet);
				proxy.sendPacketToClient(client, packet);
				nextId ++;
			}
			if(nextId > 65){
				nextId = 50;
			}
			
			if(System.currentTimeMillis() - Cheats.LAST_FAKE_KILL >= 10000){
				TerrariaPacket packet = TerrariaPacketKillMe.getKillMePacket(getPlayerId(), 0, 1000, true, "Ouch!");
				
				proxy.sendPacketToClient(client, packet);
				proxy.sendPacketToServer(packet);
				Cheats.LAST_FAKE_KILL = System.currentTimeMillis();
			}

			
		}*/
		
		return true;
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		TerrariaPlayer player = proxy.getPlayer(getPlayerId());
		
		player.setX(getPositionX());
		player.setY(getPositionY());
		
		if((getPulley() & 4) > 0){
			player.setVelocityX(getVelocityX());
			player.setVelocityY(getVelocityY());
		}
		
		if(player.isPvpEnabled() && Cheats.PVP_INSTAKILL){
			for(int i = 0; i < 5;i++){
				
				TerrariaPacket packet = TerrariaPacketProjectileUpdate.getProjectilePacket(nextId, player.getX(), player.getY(), player.getVelocityX() * 2, player.getVelocityY() * 2, 5.0F, 1000, proxy.getThePlayer().getId(), 409, 0);
				
				proxy.sendPacketToServer(packet);
				proxy.sendPacketToClient(client, packet);
				nextId ++;
			}
			if(nextId > 65){
				nextId = 50;
			}
		}
		
		if(Cheats.CONFETTI_FOLLOW == getPlayerId()){
			TerrariaPacket packet = TerrariaPacketProjectileUpdate.getProjectilePacket(50, getPositionX() + player.getVelocityX(), getPositionY() + player.getVelocityY(), player.getVelocityX(), player.getVelocityY(), 0, 0, 0, 178, 0);
			
			for(int i = 0; i < 8;i++){
				proxy.sendPacketToClient(client, packet);
				proxy.sendPacketToServer(packet);
			}

		}
		
		return true;
	}
	
}
