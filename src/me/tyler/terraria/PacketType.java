package me.tyler.terraria;

import me.tyler.terraria.packets.*;

public enum PacketType {

	CONNECTION_REQUEST(1, TerrariaPacketConnectionRequest.class),
	DISCONNECT(2, TerrariaPacketDisconnect.class),
	PLAYER_INFO(4, TerrariaPacketPlayerInfo.class),
	STATUS(9, TerrariaPacketStatus.class),
	UPDATE_PLAYER(13, TerrariaPacketUpdatePlayer.class),
	PLAYER_HP(16, TerrariaPacketPlayerHp.class),
	UPDATE_ITEM_DROP(21, TerrariaPacketUpdateItemDrop.class),
	NPC_UPDATE(23, TerrariaPacketUpdateNpc.class),
	CHAT_MESSAGE(25, TerrariaPacketChatMessage.class),
	PROJECTILE_UPDATE(27, TerrariaPacketProjectileUpdate.class),
	STRIKE_NPC(28, TerrariaPacketStrikeNpc.class),
	MANA(42, TerrariaPacketMana.class),
	MANA_EFFECT(43, TerrariaPacketManaEffect.class),
	PLAYER_TEAM(45, TerrariaPacketPlayerTeam.class),
	ADD_BUFF(55, TerrariaPacketAddBuff.class),
	SPAWN_BOSS(61, TerrariaPacketSpawnBoss.class),
	TELEPORT(65, TerrariaPacketTeleport.class),
	HEAL_OTHER(66, TerrariaPacketHealOther.class),
	CLIENT_UUID(68, TerrariaPacketUUID.class),
	RELEASE_NPC(71, TerrariaPacketReleaseNpc.class),
	COMBAT_TEXT(81, TerrariaPacketCombatText.class),
	PLACE_FRAME(89, TerrariaPacketPlaceFrame.class),
	PORTAL_TELEPORT(96, TerrariaPacketPortalTeleport.class),
	NPC_PORTAL_TELEPORT(100, TerrariaPacketNpcPortalTeleport.class),
	OTHER(-1, TerrariaPacket.class)//Used to handle packets that the packet editor doesnt specifically care about
	;
	
	private final byte id;
	private final Class<? extends TerrariaPacket> packetClass;
	
	private <T extends TerrariaPacket> PacketType(int id, Class<? extends T> packetClass) {
		this.id = (byte) id;
		this.packetClass = packetClass;
	}
	
	public byte getId() {
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getPacket(byte type, byte[] payload){
		try {
			return (T) packetClass.getConstructor(byte.class, byte[].class).newInstance(type, payload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PacketType getTypeFromId(byte id){
		for(PacketType type : values()){
			if(type.id == id){
				return type;
			}
		}
		
		return OTHER;
	}
	
}
