package me.tyler.terraria.hooks;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.packets.TerrariaPacket;

public interface PacketHook {

	public PacketType getPacketType();
	
	public default boolean onRecieve(TerrariaPacket packet){
		return true;
	}
	
	public default boolean onSend(TerrariaPacket packet){
		return true;
	}
	
}
