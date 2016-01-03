package me.tyler.terraria;

import me.tyler.terraria.packets.*;

public enum PacketType {

	CONNECTION_REQUEST(1, buf -> new TerrariaPacketConnectionRequest((byte) 1, buf)),
	DISCONNECT(2, buf -> new TerrariaPacketDisconnect((byte) 2, buf)),
	CONTINUE(3, buf -> new TerrariaPacketContinue((byte) 3, buf)),
	PLAYER_INFO(4, buf -> new TerrariaPacketPlayerInfo((byte) 4, buf)),
	INVENTORY_SLOT(5, buf -> new TerrariaPacketInventorySlot((byte) 5, buf)),
	CONTINUE2(6, buf -> new TerrariaPacketContinue((byte) 6, buf)),
	WORLD_INFO(7, buf -> new TerrariaPacketWorldInfo((byte) 7, buf)),
	GET_SECTION(8, buf -> new TerrariaPacketGetSection((byte) 8, buf)),
	STATUS(9, buf -> new TerrariaPacketStatus((byte) 9, buf)),
	/*SEND_SECTION(10, TerrariaPacketSendSection.class),*/
	SPAWN_PLAYER(12, buf -> new TerrariaPacketSpawnPlayer((byte) 12, buf)),
	UPDATE_PLAYER(13, buf -> new TerrariaPacketUpdatePlayer((byte) 13, buf)),
	PLAYER_HP(16, buf -> new TerrariaPacketPlayerHp((byte) 16, buf)),
	MODIFY_TILE(17, buf -> new TerrariaPacketModifyTile((byte) 17, buf)),
	SEND_TILE_SQUARE(20, buf -> new TerrariaPacketSendTileSquare((byte) 20, buf)),
	UPDATE_ITEM_DROP(21, buf -> new TerrariaPacketUpdateItemDrop((byte) 21, buf)),
	UPDATE_ITEM_OWNER(22, buf -> new TerrariaPacketUpdateItemOwner((byte) 22, buf)),
	NPC_UPDATE(23, buf -> new TerrariaPacketUpdateNpc((byte) 23, buf)),
	CHAT_MESSAGE(25, buf -> new TerrariaPacketChatMessage((byte) 25, buf)),
	PROJECTILE_UPDATE(27, buf -> new TerrariaPacketProjectileUpdate((byte) 27, buf)),
	STRIKE_NPC(28, buf -> new TerrariaPacketStrikeNpc((byte) 28, buf)),
	DESTROY_PROJECTILE(29, buf -> new TerrariaPacketDestroyProjectile((byte) 29, buf)),
	TOGGLE_PVP(30, buf -> new TerrariaPacketTogglePvp((byte) 30, buf)),
	ANIMATION(41, buf -> new TerrariaPacketItemAnimation((byte) 41, buf)),
	MANA(42, buf -> new TerrariaPacketMana((byte) 42, buf)),
	MANA_EFFECT(43, buf -> new TerrariaPacketManaEffect((byte) 43, buf)),
	KILL_ME(44, buf -> new TerrariaPacketKillMe((byte) 44, buf)),
	PLAYER_TEAM(45, buf -> new TerrariaPacketPlayerTeam((byte) 45, buf)),
	SET_LIQUID(48, buf -> new TerrariaPacketSetLiquid((byte) 48, buf)),
	UPDATE_BUFF(50, buf -> new TerrariaPacketUpdatePlayerBuff((byte) 50, buf)),
	/*SPECIAL_NPC_EFFECT(51, TerrariaPacketSpecialNpcEffect.class),*/
	ADD_BUFF(55, buf -> new TerrariaPacketAddBuff((byte) 55, buf)),
	SPAWN_BOSS(61, buf -> new TerrariaPacketSpawnBoss((byte) 61, buf)),
	TELEPORT(65, buf -> new TerrariaPacketTeleport((byte) 65, buf)),
	HEAL_OTHER(66, buf -> new TerrariaPacketHealOther((byte) 66, buf)),
	CLIENT_UUID(68, buf -> new TerrariaPacketUUID((byte) 68, buf)),
	RELEASE_NPC(71, buf -> new TerrariaPacketReleaseNpc((byte) 71, buf)),
	COMBAT_TEXT(81, buf -> new TerrariaPacketCombatText((byte) 81, buf)),
	PLACE_FRAME(89, buf -> new TerrariaPacketPlaceFrame((byte) 89, buf)),
	EMOTE_BUBBLE(91, buf -> new TerrariaPacketEmoteBubble((byte) 91, buf)),
	PORTAL_TELEPORT(96, buf -> new TerrariaPacketPortalTeleport((byte) 96, buf)),
	NPC_PORTAL_TELEPORT(100, buf -> new TerrariaPacketNpcPortalTeleport((byte) 100, buf)),
	OTHER(-1, null)//Used to handle packets that the packet editor doesnt specifically care about
	;
	
	private final byte id;
	private final PacketGenerator<? extends TerrariaPacket> generator;
	
	private <T extends TerrariaPacket> PacketType(int id, PacketGenerator<T> generator) {
		this.id = (byte) id;
		this.generator = generator;
	}
	
	public byte getId() {
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends TerrariaPacket> T getPacket(byte[] payload){
		return (T) generator.generate(payload);
	}
	
	public static PacketType getTypeFromId(byte id){
		for(PacketType type : values()){
			if(type.id == id){
				return type;
			}
		}
		
		return OTHER;
	}
	
	static interface PacketGenerator<T extends TerrariaPacket> {
		public T generate(byte[] payload);
	}
	
}
