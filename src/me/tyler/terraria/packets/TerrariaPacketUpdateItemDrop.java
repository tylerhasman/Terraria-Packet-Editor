package me.tyler.terraria.packets;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacketUpdateItemDrop extends TerrariaPacket {
	
	public TerrariaPacketUpdateItemDrop(byte type, byte[] buffer) {
		super(type, buffer);
	}
	
	public short getItemId()
	{
		return getPayloadBuffer().getShort();
	}
	
	public float getPositionX(){
		return getPayloadBuffer(2).getFloat();
	}
	
	public float getPositionY(){
		return getPayloadBuffer(6).getFloat();
	}
	
	public float getVelocityX(){
		return getPayloadBuffer(10).getFloat();
	}
	
	public float getVelocityY(){
		return getPayloadBuffer(14).getFloat();
	}
	
	public short getStacks(){
		return getPayloadBuffer(18).getShort();
	}
	
	public byte getPrefix(){
		return getPayloadBuffer(20).get();
	}
	
	public byte getNoDelay(){
		return getPayloadBuffer(21).get();
	}
	
	public short getItemNetId(){
		return getPayloadBuffer(22).getShort();
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {

		if(getItemNetId() == 0){
			return true;
		}
		
		if(Cheats.VAC_ENABLED){
			
			float x3 = (float) Math.pow(proxy.getThePlayer().getX() - getPositionX(), 2);
			float y3 = (float) Math.pow(proxy.getThePlayer().getY() - getPositionY(), 2);
			
			float distance = (float) Math.sqrt(x3 + y3);
			
			if(distance > 200){
				TerrariaPacket packet = getItemDropPacket(getItemId(), proxy.getThePlayer().getX(), proxy.getThePlayer().getY() - 100, getVelocityX(), getVelocityY(), getStacks(), getPrefix(), getNoDelay(), getItemNetId());
				
				proxy.sendPacketToServer(packet);
				
			}
		}else if(Cheats.VAC_POS_ENABLED){
			
			float x3 = (float) Math.pow(Cheats.VAC_POS_X - getPositionX(), 2);
			float y3 = (float) Math.pow(Cheats.VAC_POS_Y - getPositionY(), 2);
			
			float distance = (float) Math.sqrt(x3 + y3);
			
			if(distance > 1000){
				
				Random random = new Random();
				
				TerrariaPacket packet = getItemDropPacket(getItemId(), Cheats.VAC_POS_X + random.nextInt(700) - 700, Cheats.VAC_POS_Y - 100, getVelocityX(), getVelocityY(), getStacks(), getPrefix(), getNoDelay(), getItemNetId());
				
				proxy.sendPacketToServer(packet);
				
			}
			
		}

	
		return true;
	}
	
	public static TerrariaPacket getItemDropPacket(int itemId, float x, float y, float velX, float velY, int stacks, int prefix, int nodelay, int netid){
		
		ByteBuffer buf = ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putShort((short) itemId);
		buf.putFloat(x);
		buf.putFloat(y);
		buf.putFloat(velX);
		buf.putFloat(velY);
		buf.putShort((short) stacks);
		buf.put((byte) prefix);
		buf.put((byte) nodelay);
		buf.putShort((short) netid);
		
		TerrariaPacket packet = new TerrariaPacket(PacketType.UPDATE_ITEM_DROP.getId(), buf.array());
		
		return packet;
		
		
	}
	
}