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
				
				ByteBuffer buf = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
				buf.put(proxy.getThePlayer().getId());
				buf.putShort((short) 600);
				buf.putShort((short) 600);
				proxy.sendPacketToClient(client, new TerrariaPacket(PacketType.PLAYER_HP.getId(), buf.array()));
				
				proxy.sendPacketToClient(client, TerrariaPacketHealOther.getHealthOtherPacket(getPlayerId(), (short) (getMaxLife()-getLife())));
				
				return false;
			}
		}
		return true;
	}
	
	
	
}
