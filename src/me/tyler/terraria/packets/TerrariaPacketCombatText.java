package me.tyler.terraria.packets;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.TerrariaColor;

public class TerrariaPacketCombatText extends TerrariaPacket {

	public TerrariaPacketCombatText(byte t, byte[] p) {
		super(t, p);
		// TODO Auto-generated constructor stub
	}

	public float getX(){
		return getPayloadBuffer().getFloat();
	}
	
	public float getY(){
		return getPayloadBuffer(4).getFloat();
	}
	
	public TerrariaColor getColor(){
		return TerrariaColor.getColor(getPayload(), 9);
	}
	
	public String getText(){
		return PacketUtil.readString(getPayload(), 13);
	}
	
	public static TerrariaPacket getCombatTextPacket(float x, float y, TerrariaColor color, String text){
		
		byte[] strBytes = null;
		try {
			strBytes = text.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();//this will never happen
		}
		
		ByteBuffer buf = ByteBuffer.allocate(12+strBytes.length).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putFloat(x);
		buf.putFloat(y);
		buf.put(color.getBytes());
		buf.put((byte) strBytes.length);
		buf.put(strBytes);
		
		return new TerrariaPacket(PacketType.COMBAT_TEXT.getId(), buf.array());
		
	}
	
}
