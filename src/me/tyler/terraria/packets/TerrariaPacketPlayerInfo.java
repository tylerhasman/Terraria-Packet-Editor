package me.tyler.terraria.packets;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.RandomStringUtils;

import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaPlayer;
import me.tyler.terraria.TerrariaPlayerLocal;

public class TerrariaPacketPlayerInfo extends TerrariaPacket {
	
	public TerrariaPacketPlayerInfo(byte type, byte[] payload) {
		super(type, payload);
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
	
	public void randomizeName(){
		byte length = getPayload()[3];
		
		byte[] bytes = null;
		
		String str = RandomStringUtils.randomAlphabetic(length).toLowerCase();
		
		try {
			bytes = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		getPayloadBuffer(4).put(bytes);
	}
	
	@Override
	public boolean onReceive(Proxy proxy) {
		TerrariaPlayer tp = new TerrariaPlayer(getPlayerId(), proxy);
		tp.setName(getName());
		proxy.addPlayer(tp);
		return super.onReceive(proxy);
	}
	
	@Override
	public boolean onSending(Proxy proxy) {
		
		boolean b = super.onSending(proxy);
		
		proxy.setThePlayer(new TerrariaPlayerLocal(getPlayerId(), proxy));
		proxy.getThePlayer().setName(getName());
		
		return b;
	}
	
}
