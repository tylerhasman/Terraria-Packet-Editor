package me.tyler.terraria.packets;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;

public class TerrariaPacketKillMe extends TerrariaPacket {

	public TerrariaPacketKillMe(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public byte getHitDirection(){
		return getPayloadBuffer(1).get();
	}
	
	public short getDamage(){
		return getPayloadBuffer(2).getShort();
	}
	
	public boolean isPvP(){
		return getPayloadBuffer(4).get() > 0;
	}
	
	public String getDeathText(){
		return PacketUtil.readString(getPayload(), 5);
	}
	
	public static TerrariaPacketKillMe getKillMePacket(byte playerId, int hitDirection, int damage, boolean pvp, String text){
		
		byte[] strBuf = null;
		try {
			strBuf = text.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();//This will never happen
		}
		
		ByteBuffer buf = ByteBuffer.allocate(6 + strBuf.length).order(ByteOrder.LITTLE_ENDIAN);
		buf.put(playerId);
		buf.put((byte) hitDirection);
		buf.putShort((short) damage);
		buf.put((byte) (pvp ? 1 : 0));
		buf.put((byte) strBuf.length);
		buf.put(strBuf);
		
		return new TerrariaPacketKillMe(PacketType.KILL_ME.getId(), buf.array());
		
	}

}
