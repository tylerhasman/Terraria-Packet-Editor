package me.tyler.terraria.packets;

import java.net.Socket;

import me.tyler.terraria.Buffs;
import me.tyler.terraria.Proxy;

public class TerrariaPacketAddBuff extends TerrariaPacket {

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public int getBuff(){
		return getPayloadBuffer(1).get() & 0xFF;
	}
	
	public short getTime(){
		return getPayloadBuffer(2).getShort();
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		if(getPlayerId() == proxy.getThePlayer().getId()){
			
			String buff = Buffs.getBuffName((short) getBuff());
			
			if(getBuff() == 0){
				return true;
			}
		
			
			return true;
			
			
		}
		
		return super.onReceive(proxy, client);
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		if(getPlayerId() == proxy.getThePlayer().getId()){
			
			String buff = Buffs.getBuffName((short) getBuff());
			
			if(getBuff() == 0){
				return true;
			}
			
			if(buff != null){
				System.out.println("Buff: "+buff);
			}
			
			return false;
			
			
		}
		
		
		return super.onSending(proxy, client);
	}
	
}
