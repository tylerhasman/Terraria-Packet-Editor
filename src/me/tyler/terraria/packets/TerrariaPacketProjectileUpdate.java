package me.tyler.terraria.packets;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacketProjectileUpdate extends TerrariaPacket {

	public short getProjectileId(){
		return getPayloadBuffer().getShort();
	}
	
	public float getX(){
		return getPayloadBuffer(2).getFloat();
	}
	
	public float getVelocityX(){
		return getPayloadBuffer(6).getFloat();
	}
	
	public float getY(){
		return getPayloadBuffer(10).getFloat();
	}
	
	public float getVelocityY(){
		return getPayloadBuffer(14).getFloat();
	}
	
	public float getKnockback(){
		return getPayloadBuffer(18).getFloat();
	}
	
	public short getDamage(){
		return getPayloadBuffer(22).getShort();
	}
	
	public byte getOwner(){
		return getPayloadBuffer(24).get();
	}
	
	public short getProjectileType(){
		return getPayloadBuffer(25).getShort();
	}
	
	public byte getAiFlags(){
		return getPayloadBuffer(27).get();
	}
	
	public float getAiOne(){
		if(getPayload().length < 32){
			return -1;
		}
		return getPayloadBuffer(28).getFloat();
	}
	
	public float getAiTwo(){
		if(getPayload().length < 36){
			return -1;
		}
		return getPayloadBuffer(32).getFloat();
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		TerrariaPacket packet = getProjectilePacket(getProjectileId(), getX(), getY(), getVelocityX(), getVelocityY(), getKnockback(), (short) 1500, getOwner(), getType(), getAiFlags(), getAiOne(), getAiTwo());
		
		proxy.sendPacketToServer(packet);
		
		return false;
	}
	
	public static TerrariaPacket getProjectilePacket(short id, float x, float y, float velx, float vely, float knockback, short damage, byte owner, short type, byte aiflags, float... flags){
		
		ByteBuffer buf = ByteBuffer.allocate(28+(flags.length * 4)).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putShort(id);
		buf.putFloat(x);
		buf.putFloat(velx);
		buf.putFloat(y);
		buf.putFloat(vely);
		buf.putFloat(knockback);
		buf.putShort(damage);
		buf.put(owner);
		buf.putShort(type);
		buf.put(aiflags);
		for(float f : flags){
			if(f == -1)
				continue;
			buf.putFloat(f);
		}
		
		return new TerrariaPacket(PacketType.PROJECTILE_UPDATE, buf.array());
		
		
	}
	
}
