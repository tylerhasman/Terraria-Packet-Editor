package me.tyler.terraria.packets;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaColor;
import me.tyler.terraria.TerrariaData;

public class TerrariaPacketProjectileUpdate extends TerrariaPacket {

	public TerrariaPacketProjectileUpdate(byte type, byte[] payload) {
		super(type, payload);
	}

	public short getProjectileId(){
		return getPayloadBuffer().getShort();
	}
	
	public float getX(){
		return getPayloadBuffer(2).getFloat();
	}
	
	public float getY(){
		return getPayloadBuffer(6).getFloat();
	}
	
	public float getVelocityX(){
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
		if(getAiFlags() < 1){
			return -1;
		}
		return getPayloadBuffer(28).getFloat();
	}
	
	public float getAiTwo(){
		if(getAiFlags() < 2){
			return -1;
		}
		return getPayloadBuffer(32).getFloat();
	}
	
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		if(!TerrariaData.proj_ids_in_use.contains(getProjectileId())){
			TerrariaData.proj_ids_in_use.add(getProjectileId());
		}
		
		if(Cheats.replacer.containsKey(getProjectileType())){
			
			TerrariaPacket packet = getProjectilePacket(getProjectileId(), getX(), getY(), getVelocityX(), getVelocityY(), getKnockback(), getDamage() * 2, getOwner(), Cheats.replacer.get(getProjectileType()), 0);
			
			proxy.sendPacketToServer(packet);
			proxy.sendPacketToClient(client, packet);
			
			return false;
		}
		
		if(Cheats.TRACK_PROJECTILES){
			
			String name = TerrariaData.PROJECTILES.getValue(getProjectileType());
			
			proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.YELLOW, name+" - "+getProjectileType()));
			
		}
		
		
		return true;
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		if(!TerrariaData.proj_ids_in_use.contains(getProjectileId())){
			TerrariaData.proj_ids_in_use.add(getProjectileId());
		}
		
		if(Cheats.PROJECTILE_REPLACER_OTHER_TO >= 0){
			TerrariaPacket packet = getProjectilePacket(TerrariaData.getFreeProjectileId(), getX(), getY(), getVelocityX(), getVelocityY(), getKnockback(), getDamage(), proxy.getThePlayer().getId(), Cheats.PROJECTILE_REPLACER_OTHER_TO, 0);
			
			proxy.sendPacketToServer(packet);
			proxy.sendPacketToClient(client, packet);
		}
		
		return true;
	}
	
	public static TerrariaPacket getProjectilePacket(int id, float x, float y, float velx, float vely, float knockback, int damage, int owner, int type, int aiflags, float... flags){
		
		ByteBuffer buf = ByteBuffer.allocate(28+(flags.length * 4)).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putShort((short) id);
		buf.putFloat(x);
		buf.putFloat(y);
		buf.putFloat(velx);
		buf.putFloat(vely);
		buf.putFloat(knockback);
		buf.putShort((short) damage);
		buf.put((byte) owner);
		buf.putShort((short) type);
		buf.put((byte) aiflags);
		
		if(flags != null){
			for(float f : flags){
				if(f == -1)
					continue;
				buf.putFloat(f);
			}
		}

		
		return new TerrariaPacket(PacketType.PROJECTILE_UPDATE.getId(), buf.array());
	}
	
}
