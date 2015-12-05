package me.tyler.terraria.packets;

import java.nio.ByteBuffer;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.PlayerInfo;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaColor;
import me.tyler.terraria.TerrariaPlayer;
import me.tyler.terraria.TerrariaPlayerLocal;

import org.apache.commons.lang3.RandomStringUtils;

public class TerrariaPacketPlayerInfo extends TerrariaPacket {
	
	public TerrariaPacketPlayerInfo(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketPlayerInfo(int id, String name, PlayerInfo info) {
		super(PacketType.PLAYER_INFO.getId(), create(id, name, info));
	}

	private static byte[] create(int id, String name, PlayerInfo info) {
		ByteBuffer buf = ByteBuffer.allocate(30 + PacketUtil.calculateLength(name));
		
		buf.put((byte) id);
		buf.put(info.getSkinVariant());
		buf.put(info.getHair());
		PacketUtil.writeString(buf, name);
		buf.put(info.getHairDye());
		buf.put(info.getHideVisuals());
		buf.put(info.getHideVisuals2());
		buf.put(info.getHideMisc());
		buf.put(info.getHairColor().getBytes());
		buf.put(info.getSkinColor().getBytes());
		buf.put(info.getEyeColor().getBytes());
		buf.put(info.getShirtColor().getBytes());
		buf.put(info.getUnderShirtColor().getBytes());
		buf.put(info.getPantsColor().getBytes());
		buf.put(info.getShoeColor().getBytes());
		buf.put((byte) info.getDifficulty().getId());
		
		return buf.array();
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public byte getSkinVariant(){
		return getPayloadBuffer(1).get();
	}
	
	public byte getHair(){
		return getPayloadBuffer(2).get();
	}
	
	public String getName(){
		return PacketUtil.readString(getPayload(), 3);
	}
	
	private int getOffsetAfterName(int offset){
		return 3 + PacketUtil.calculateLength(getName()) + offset;
	}
	
	public byte getHairDye(){
		return getPayloadBuffer(getOffsetAfterName(1)).get();
	}
	
	public byte getHideVisuals(){
		return getPayloadBuffer(getOffsetAfterName(2)).get();
	}
	
	public byte getHideVisuals2(){
		return getPayloadBuffer(getOffsetAfterName(3)).get();
	}
	
	public byte getHideMisc(){
		return getPayloadBuffer(getOffsetAfterName(4)).get();
	}
	
	public TerrariaColor getHairColor(){
		return TerrariaColor.getColor(getPayload(), getOffsetAfterName(5));
	}
	
	public TerrariaColor getSkinColor(){
		return TerrariaColor.getColor(getPayload(), getOffsetAfterName(8));
	}
	
	public TerrariaColor getEyeColor(){
		return TerrariaColor.getColor(getPayload(), getOffsetAfterName(11));
	}
	
	public TerrariaColor getShirtColor(){
		return TerrariaColor.getColor(getPayload(), getOffsetAfterName(14));
	}
	
	public TerrariaColor getUnderShirtColor(){
		return TerrariaColor.getColor(getPayload(), getOffsetAfterName(17));
	}
	
	public TerrariaColor getPantsColor(){
		return TerrariaColor.getColor(getPayload(), getOffsetAfterName(20));
	}
	
	public TerrariaColor getShoeColor(){
		return TerrariaColor.getColor(getPayload(), getOffsetAfterName(23));
	}
	
	public byte getDifficulty(){
		return getPayload()[getOffsetAfterName(26)];
	}
	
	public void randomizeName(){
		byte length = getPayload()[3];
		
		String str = RandomStringUtils.randomAlphabetic(length).toLowerCase();
		
		PacketUtil.writeString(getPayloadBuffer(4), str);
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		TerrariaPlayer tp = new TerrariaPlayer(getPlayerId(), proxy);
		tp.setName(getName());
		tp.getInfo().load(this);
		
		proxy.addPlayer(tp);
		
		return super.onReceive(proxy);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		boolean b = super.onSending(proxy);
		
		proxy.getThePlayer().setName(getName());
		proxy.getThePlayer().getInfo().load(this);
		
		return b;
	}
	
}
