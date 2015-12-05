package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaPlayer;

public class TerrariaPacketPlayerHp extends TerrariaPacket {

	public static int id = 100;
	
	public TerrariaPacketPlayerHp(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketPlayerHp(int pid, int hp, int maxHp){
		super(PacketType.PLAYER_HP.getId(), getPlayerHpPacket(pid, hp, maxHp).array());
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public short getLife(){
		return getPayloadBuffer(1).getShort();
	}
	
	public short getMaxLife(){
		return getPayloadBuffer(3).getShort();
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		
		if(getPlayerId() != proxy.getThePlayer().getId()){	
			TerrariaPlayer player = proxy.getPlayer(getPlayerId());
			
			player.setHealth(getLife());
			player.setMaxHealth(getMaxLife());
			
		}
		
		return super.onReceive(proxy);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		TerrariaPlayer player = proxy.getThePlayer();
		
		player.setHealth(getLife());
		player.setMaxHealth(getMaxLife());
		
		return super.onSending(proxy);
	}
	
	public static ByteBuffer getPlayerHpPacket(int playerId, int hp, int maxHp){
		ByteBuffer buf = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
		buf.put((byte) playerId);
		buf.putShort((short) hp);
		buf.putShort((short) maxHp);
		
		return buf;
	}
	
	
	
}
