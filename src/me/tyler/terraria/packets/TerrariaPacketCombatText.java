package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.TerrariaColor;

public class TerrariaPacketCombatText extends TerrariaPacket {

	public TerrariaPacketCombatText(byte t, byte[] p) {
		super(t, p);
	}

	public TerrariaPacketCombatText(float x, float y, TerrariaColor color, String string) {
		super(PacketType.COMBAT_TEXT.getId(), getCombatTextPacket(x, y, color, string));
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
	
	private static byte[] getCombatTextPacket(float x, float y, TerrariaColor color, String text){
		
		ByteBuffer buf = ByteBuffer.allocate(12+PacketUtil.calculateLength(text)).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putFloat(x);
		buf.putFloat(y);
		buf.put(color.getBytes());
		PacketUtil.writeString(buf, text);
		
		return buf.array();
		
	}
	
}
