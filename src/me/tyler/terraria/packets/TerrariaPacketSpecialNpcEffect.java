package me.tyler.terraria.packets;

public class TerrariaPacketSpecialNpcEffect extends TerrariaPacket {

	public TerrariaPacketSpecialNpcEffect(byte type, byte[] payload) {
		super(type, payload);
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public byte getType(){
		return getPayloadBuffer(1).get();
	}
/*	
	public static TerrariaPacket getEffectPacket(byte playerId, byte type){
		
		ByteBuffer buf = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.put(playerId);
		buf.put(type);
		
		return new TerrariaPacket(PacketType.SPECIAL_NPC_EFFECT.getId(), buf.array());		
	}*/
	
	public class Type{
		public static final byte SPAWN_SKELETRON = 1;
		public static final byte SOUND_AT_PLAYER = 2;
		public static final byte START_SUNDIALING = 3;
		public static final byte BIG_SPAWN_SMOKE = 4;
	}
	
}
