package me.tyler.terraria.packets;

import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaPlayer;

public class TerrariaPacketItemAnimation extends TerrariaPacket {

	public TerrariaPacketItemAnimation(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public byte getPlayerId(){
		return getPayload()[0];
	}
	
	public float getRotation(){
		return getPayloadBuffer(1).getFloat();
	}
	
	public short getItemAnimation(){
		return getPayloadBuffer(5).getShort();
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		
		updatePlayer(proxy.getPlayer(getPlayerId()));
		
		return super.onReceive(proxy);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		updatePlayer(proxy.getThePlayer());
		
		return super.onSending(proxy);
	}
	
	private void updatePlayer(TerrariaPlayer player){
		player.setRotation((float) Math.toDegrees(getRotation()));
	}

}
