package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaPlayer;

public class TerrariaPacketMana extends TerrariaPacket {

	public TerrariaPacketMana(byte t, byte[] p) {
		super(t, p);
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public short getMana(){
		return getPayloadBuffer(1).getShort();
	}
	
	public short getMaxMana(){
		return getPayloadBuffer(3).getShort();
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		
		if(getPlayerId() != proxy.getThePlayer().getId()){
			TerrariaPlayer player = proxy.getPlayer(getPlayerId());
			
			player.setMana(getMana());
			player.setMaxMana(getMaxMana());	
		}
		
		return super.onReceive(proxy);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		TerrariaPlayer player = proxy.getThePlayer();
		
		player.setMana(getMana());
		player.setMaxMana(getMaxMana());
		
		return super.onSending(proxy);
	}
	
	public static TerrariaPacket getManaPacket(byte playerId, short mana, short maxMana){
		
		ByteBuffer buf = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put(playerId);
		buf.putShort(mana);
		buf.putShort(maxMana);
		
		return new TerrariaPacket(PacketType.MANA.getId(), buf.array());
		
	}
	
}
