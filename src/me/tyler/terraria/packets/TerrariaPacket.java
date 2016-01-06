package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import me.tyler.terraria.PacketType;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.script.Script;

public class TerrariaPacket {
	
	private byte type;
	private byte[] payload;
	private List<Script> scripts;
	
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
		return getClass().getSimpleName();
	}
	
	public String dumpData() {
		
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
	
	public boolean onReceive(Proxy proxy){
		
		boolean forwardToClient = true;
		
		if(scripts == null){
			scripts = proxy.getScriptManager().getScriptsForPacket(PacketType.getTypeFromId(type));
		}
		
		for(Script script : scripts){
			try {
				Object obj = script.invoke("recieve", this, proxy);
				
				if(obj != null){
					forwardToClient = (boolean) obj;
				}
				
			} catch (NoSuchMethodException e) {
			}
		}
		return forwardToClient;
	}
	
	public boolean onSending(Proxy proxy){
		
		boolean forwardToServer = true;
		
		if(scripts == null){
			scripts = proxy.getScriptManager().getScriptsForPacket(PacketType.getTypeFromId(type));
		}
		
		for(Script script : scripts){
			try {
				Object obj = script.invoke("send", this, proxy);
				
				if(obj != null){
					forwardToServer = (boolean) obj;
				}
				
			} catch (NoSuchMethodException e) {
			}
		}
		return forwardToServer;
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
			packet = new TerrariaPacket(type, payload);
		}else{
			packet = pType.getPacket(payload);
		}
		
		return packet;
		
	}
	
}
