package me.tyler.terraria.packets;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;

public class TerrariaPacket {
	
	private byte type;
	private byte[] payload;
	
	public TerrariaPacket(byte type, byte[] payload) {
		this.type = type;
		this.payload = payload;
	}
	
	public byte getType() {
		return type;
	}
	
	public short getLength() {
		return (short) (3 + payload.length);
	}

	public byte[] getPayload() {
		return payload;
	}
	
	@Override
	public String toString() {
		
		String packetInfo = "";
		
		packetInfo += "Length: "+getLength()+" ";
		packetInfo += "Type: "+type+" Payload: ";
		
		for(byte b : payload){
			packetInfo += b+" ";
		}
		
		return packetInfo;
	}
	
	public ByteBuffer getPayloadBuffer(){
		return getPayloadBuffer(0);
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
	
	protected void setPayload(byte[] b){
		this.payload = b;
	}
	
	public static TerrariaPacket getPacketFromData(byte[] bytes){
		
		ByteBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
		
		short length = buf.getShort();
		byte type = buf.get();
		byte[] payload = new byte[length - 3];
		
		buf.get(payload);
		
		PacketType pType = PacketType.getTypeFromId(type);

		TerrariaPacket packet;
		
		if(pType == PacketType.OTHER){
			packet = pType.getPacket(type, payload);

		}else{
			packet = pType.getPacket(payload);
		}
		
		return packet;
		
	}
	
}
