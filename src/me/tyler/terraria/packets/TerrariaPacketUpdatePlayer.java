package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaPlayer;

public class TerrariaPacketUpdatePlayer extends TerrariaPacket {
	
	public TerrariaPacketUpdatePlayer(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketUpdatePlayer(int id, int control, int pulley, int item, float x, float y, float velx, float vely) {
		super(PacketType.UPDATE_PLAYER.getId(), create((byte) id, (byte) control, (byte) pulley, (byte) item, x, y, velx, vely));
	}
	
	private static byte[] create(byte id, byte control, byte pulley, byte item, float x, float y, float velx, float vely){
		ByteBuffer buf = ByteBuffer.allocate(12 + (pulley >= 4 ? 8 : 0)).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put(id);
		buf.put(control);
		buf.put(pulley);
		buf.put(item);
		buf.putFloat(x);
		buf.putFloat(y);
		
		if(pulley >= 4){
			buf.putFloat(velx);
			buf.putFloat(vely);
		}
		
		return buf.array();
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
		if((getPulley() & 4) == 4){
			return getPayloadBuffer(12).getFloat();
		}
		return -1;
	}
	
	public float getVelocityY(){
		if((getPulley() & 4) == 4){
			return getPayloadBuffer(16).getFloat();
		}
		return -1;
	}
	
	public Control[] getControls(){
		int control = getControl() & 0xFF;
		Control[] controls = new Control[Control.values().length];
		
		int found = 0;
		
		for(Control c : Control.values()){
			if((control & c.bitPosition) == c.bitPosition){
				controls[found] = c;
				found++;
			}
		}
		
		return Arrays.copyOf(controls, found);
	}
	
	public Pulley[] getPulleys(){
		int pulley = getPulley() & 0xFF;
		Pulley[] pulleys = new Pulley[Pulley.values().length];
		
		int found = 0;
		
		for(Pulley c : Pulley.values()){
			if((pulley & c.bitPosition) == c.bitPosition){
				pulleys[found] = c;
				found++;
			}
		}
		
		return Arrays.copyOf(pulleys, found);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		TerrariaPlayer player = proxy.getThePlayer();
		
		player.setControls(getControls());
		
		player.setX(getPositionX());
		player.setY(getPositionY());
		
		if((getPulley() & 4) > 0){
			player.setVelocityX(getVelocityX());
			player.setVelocityY(getVelocityY());
		}
		
		proxy.setConnectionIniatializationDone(true);
		
		return super.onSending(proxy);
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		
		TerrariaPlayer player = proxy.getPlayer(getPlayerId());
		
		player.setX(getPositionX());
		player.setY(getPositionY());
		
		if((getPulley() & 4) > 0){
			player.setVelocityX(getVelocityX());
			player.setVelocityY(getVelocityY());
		}
		
		if(Cheats.VAC_TO != null){
			if(Cheats.VAC_TO.equalsIgnoreCase(player.getName())){
				Cheats.VAC_POS_X = player.getX();
				Cheats.VAC_POS_Y = player.getY();
				Cheats.VAC_POS_ENABLED = true;
			}
		}
		
		return super.onReceive(proxy);
	}
	
	public static enum Pulley{
		
		DIRECTION_1(1),
		DIRECTION_2(2),
		UPDATE_VELOCITY(4),
		STEALTH_ACTIVE(8),
		GRAVITY_DIRECTION(16)
		;
		
		private final int bitPosition;
		
		Pulley(int bit) {
			bitPosition = bit;
		}
		
		public int getBitPosition() {
			return bitPosition;
		}
	
	}
	
	public static enum Control{
		UP(1),
		DOWN(2),
		LEFT(4),
		RIGHT(8),
		JUMP(16),
		USE_ITEM(32),
		DIRECTION(64);
		
		private final int bitPosition;
		
		Control(int bit) {
			bitPosition = bit;
		}
		
		public int getBitPosition() {
			return bitPosition;
		}
		
	}
	
}
