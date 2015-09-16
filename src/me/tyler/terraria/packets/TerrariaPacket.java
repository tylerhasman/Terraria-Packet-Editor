package me.tyler.terraria.packets;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacket {

	private short length;
	private byte type;
	private byte[] payload;
	
	public TerrariaPacket(byte t, byte[] p) {
		type = t;
		payload = p;
		length = (short) (3 + p.length);
	}
	
	public TerrariaPacket() {
		
	}
	
	public byte getType() {
		return type;
	}
	
	public short getLength() {
		return length;
	}

	public byte[] getPayload() {
		return payload;
	}
	
	public ByteBuffer getPayloadBuffer(){
		return ByteBuffer.wrap(payload).order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public ByteBuffer getPayloadBuffer(int offset){
		return (ByteBuffer) ByteBuffer.wrap(payload, offset, payload.length - offset).order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public boolean onReceive(Proxy proxy, Socket client){
		return true;
	}
	
	public boolean onSending(Proxy proxy, Socket client){
		return true;
	}
	
	@Deprecated
	public static List<TerrariaPacket> getPacketsFromData(byte[] bytes){
		List<TerrariaPacket> packets = new ArrayList<TerrariaPacket>();
		
		ByteBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
		
		while(true){
			
			if(!buf.hasRemaining()){
				break;
			}
			
			TerrariaPacket packet = new TerrariaPacket();
			
			packet.length = buf.getShort();
			packet.type = buf.get();
			packet.payload = new byte[packet.length - 3];
			
			if(buf.remaining() < packet.payload.length){
				System.out.println("Length:"+packet.length);
				System.out.println("Packet ID "+packet.type);
				System.out.println("Buffer Under Flow! Expected "+packet.payload.length+" bytes in payload but got "+buf.remaining());
				break;
			}
			
			buf.get(packet.payload);
			
			packets.add(packet);
			
		}
		
		
		return packets;
	}
	
	public static TerrariaPacket getPacketFromData(byte[] bytes){
		
		ByteBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
		
		short length = buf.getShort();
		byte type = buf.get();
		byte[] payload = new byte[length - 3];
		
		buf.get(payload);
		
		TerrariaPacket packet = null;
		
		if(type == PacketType.CHAT_MESSAGE){
			packet = new TerrariaPacketChatMessage();
		}else if(type == PacketType.UPDATE_ITEM_DROP){
			packet = new TerrariaPacketItemDrop();
		}else if(type == PacketType.PLAYER_INFO){
			packet = new TerrariaPacketPlayerInfo();
		}else if(type == PacketType.UPDATE_PLAYER){
			packet = new TerrariaPacketUpdatePlayer();
		}else if(type == PacketType.CLIENT_UUID){
			packet = new TerrariaPacketUUID();
		}else if(type == PacketType.DISCONNECT){
			packet = new TerrariaPacketDisconnect();
		}else if(type == PacketType.SPAWN_BOSS){
			packet = new TerrariaPacketSpawnBoss();
		}else if(type == PacketType.PORTAL_TELEPORT){
			packet = new TerrariaPacketPortalTeleport();
		}else if(type == PacketType.NPC_UPDATE){
			packet = new TerrariaPacketUpdateNpc();
		}else if(type == PacketType.STRIKE_PACKET){
			packet = new TerrariaPacketStrikeNpc();
		}else if(type == PacketType.PLAYER_HP){
			packet = new TerrariaPacketPlayerHP();
		}else if(type == PacketType.ADD_BUFF){
			packet = new TerrariaPacketAddBuff();
		}else if(type == PacketType.TELEPORT){
			packet = new TerrariaPacketTeleport();
		}else if(type == PacketType.MANA){
			packet = new TerrariaPacketMana();
		}else if(type == PacketType.PROJECTILE_UPDATE){
			packet = new TerrariaPacketProjectileUpdate();
		}else{
			packet = new TerrariaPacket();
		}
		
		packet.length = length;
		packet.type = type;
		packet.payload = payload;
		
		return packet;
		
	}
	
}
