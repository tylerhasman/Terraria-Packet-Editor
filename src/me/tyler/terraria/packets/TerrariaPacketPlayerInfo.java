package me.tyler.terraria.packets;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import org.apache.commons.lang3.RandomStringUtils;

import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaPlayer;

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
	public boolean onReceive(Proxy proxy, Socket client) {
		TerrariaPlayer tp = new TerrariaPlayer(getPlayerId());
		tp.setName(getName());
		proxy.addPlayer(tp);
		return super.onReceive(proxy, client);
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		boolean b = super.onSending(proxy, client);
		
		proxy.setThePlayer(new TerrariaPlayer(getPlayerId()));
		proxy.getThePlayer().setName(getName());
		
		return b;
	}
	
}
