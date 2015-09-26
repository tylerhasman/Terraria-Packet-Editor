package me.tyler.terraria.packets;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacketPlayerHp extends TerrariaPacket {

	public TerrariaPacketPlayerHp(byte type, byte[] payload) {
		super(type, payload);
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
	public boolean onSending(Proxy proxy, Socket client) {
		if(getPlayerId() == proxy.getThePlayer().getId()){
			if(Cheats.BLOCK_DAMAGE){
				proxy.sendPacketToClient(client, TerrariaPacketHealOther.getHealthOtherPacket(getPlayerId(), (short) (getMaxLife()-getLife())));
				return false;
			}
		}
		return true;
	}
	
	public static TerrariaPacketPlayerHp getPlayerHpPacket(int playerId, int hp, int maxHp){
		ByteBuffer buf = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
		buf.put((byte) playerId);
		buf.putShort((short) hp);
		buf.putShort((short) maxHp);
		
		return new TerrariaPacketPlayerHp(PacketType.PLAYER_HP.getId(), buf.array());
	}
	
	
	
}
