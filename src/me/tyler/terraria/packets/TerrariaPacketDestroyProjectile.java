package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacketDestroyProjectile extends TerrariaPacket {

	public TerrariaPacketDestroyProjectile(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketDestroyProjectile(int projectileId, int owner) {
		super(PacketType.DESTROY_PROJECTILE.getId(), getPacket(projectileId, owner).array());
	}
	
	public short getProjectileId(){
		return getPayloadBuffer().getShort();
	}
	
	public byte getOwner(){
		return getPayloadBuffer(2).get();
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		
		proxy.freeProjectileId(getProjectileId());
		
		return super.onReceive(proxy);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		proxy.freeProjectileId(getProjectileId());
		
		return super.onSending(proxy);
	}
	
	private static ByteBuffer getPacket(int pid, int owner){
		ByteBuffer buf = ByteBuffer.allocate(3).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putShort((short) pid);
		buf.put((byte) owner);
		
		return buf;
	}

}
