package me.tyler.terraria.packets;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.Items;
import me.tyler.terraria.Npc;
import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaColor;
import me.tyler.terraria.TerrariaPlayer;

public class TerrariaPacketChatMessage extends TerrariaPacket {
	
	public TerrariaPacketChatMessage(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public TerrariaPacketChatMessage() {
		
	}

	public byte getPlayerId(){
		return getPayloadBuffer().get();
	}
	
	public boolean isPlayerNamed(){
		return getPlayerId() != 0xff;
	}
	
	public TerrariaColor getColor(){
		
		byte[] buffer = new byte[3];
		
		getPayloadBuffer(1).get(buffer);
		
		return TerrariaColor.getColor(buffer);
		
	}

	
	public String getMessage(){
		
		return PacketUtil.readString(getPayload(), 4);
		
	}
	
	@Override
	public boolean onSending(Proxy proxy, Socket client) {
		
		try{
			String msg = getMessage();
			
			String[] splits = msg.split(" ");
			
			if(msg.startsWith("-")){
				if(splits[0].endsWith("drop")){

					float x = proxy.getThePlayer().getX();
					float y = proxy.getThePlayer().getY();
					short itemId = Short.parseShort(splits[1]);
					
					int amount = Integer.parseInt(splits[2]);
					
					Random random = new Random();
					
					for(int i = 0; i < amount;i++){
						TerrariaPacket packet = TerrariaPacketItemDrop.getItemDropPacket(400, x + random.nextInt(1000) - 500, y - random.nextInt(100) - 100, random.nextFloat() * 3, random.nextFloat() * 3, 1, 0, 0, itemId);
						proxy.sendPacketToServer(packet);
					}
					
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 200, 50), "Dropped "+amount+" "+Items.getItemName(itemId)));
					
				}else if(splits[0].endsWith("vac")){
					Cheats.VAC_ENABLED = !Cheats.VAC_ENABLED;
					
					if(Cheats.VAC_ENABLED){
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(25, 150, 50), "Vac Enabled"));
					}else{
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(150, 25, 25), "Vac Disabled"));
					}
					
				}else if(splits[0].endsWith("boss")){
					
					int amount = 1;
					
					if(splits.length == 3){
						amount = Integer.parseInt(splits[2]);
					}
					
					TerrariaPacketSpawnBoss packet = TerrariaPacketSpawnBoss.getSpawnBossPacket(proxy.getThePlayer().getId(), (short) Integer.parseInt(splits[1]));
					
					for(int i = 0; i < amount;i++){
						proxy.sendPacketToServer(packet);
					}
					
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 50, 200), "Boss spawned!"));
				}else if(splits[0].endsWith("teleport")){
					
					String player = splits[1];
					
					TerrariaPlayer pl = proxy.getPlayer(player);
					
					if(pl == null){
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 50, 50), "No player was found named "+player));
					}else{
						proxy.sendPacketToClient(client, TerrariaPacketPortalTeleport.getPortalTeleportPacket(proxy.getThePlayer().getId(), (short) 0, pl.getX(), pl.getY(), 0, 0));
					}
					
				}else if(splits[0].endsWith("damageall")){
					int damage = 1500;
					
					if(splits.length == 2){
						damage = Integer.parseInt(splits[1]);
					}
					
					for(Npc npc : proxy.getNpcs()){
						proxy.sendPacketToServer(TerrariaPacketStrikeNpc.getStrikePacket(npc.getId(), damage, 0, (byte) 0, false));
					}
					
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(50, 25, 220), "Killed "+proxy.getNpcs().size()+" npcs."));
				}else if(splits[0].endsWith("damage")){
					
					Cheats.BLOCK_DAMAGE = !Cheats.BLOCK_DAMAGE;
					
					if(Cheats.BLOCK_DAMAGE){
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(25, 150, 50), "Damage Blocking Enabled"));
					}else{
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(150, 25, 25), "Damage Blocking Disabled"));
					}
				}else if(splits[0].endsWith("pos")){
					
					float x = proxy.getThePlayer().getX();
					float y = proxy.getThePlayer().getY();
					
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 200, 25), x+"/"+y));
				}else if(splits[0].endsWith("vachere")){
					
					float x = proxy.getThePlayer().getX();
					float y = proxy.getThePlayer().getY();
					
					Cheats.VAC_POS_ENABLED = !Cheats.VAC_POS_ENABLED;
					Cheats.VAC_POS_X = x;
					Cheats.VAC_POS_Y = y;
					
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 200, 25), "Vac set to "+x+"/"+y));
					
				}else{
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 50, 50), "Unknown command '"+splits[0]+"'"));
				}
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(255, 0, 0), "ERROR >> "+ e.getMessage()));
		}
		
		
		
		return true;
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		return true;
	}
	
	public static TerrariaPacketChatMessage getMessagePacket(byte player, TerrariaColor color, String msg){
		
		ByteBuffer buf = ByteBuffer.allocate(1 + 3 + msg.length() + 1).order(ByteOrder.LITTLE_ENDIAN);//player + color(3) + msg length + msg length byte
		
		buf.put(player);
		buf.put(color.getBytes());//Client cant choose color
		buf.put((byte) msg.length());
		byte[] msgBytes = null;
		
		try {
			msgBytes = msg.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		buf.put(msgBytes);
		
		TerrariaPacketChatMessage packet = new TerrariaPacketChatMessage(PacketType.CHAT_MESSAGE, buf.array());
		
		return packet;
	}
	
}
