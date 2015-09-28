package me.tyler.terraria.packets;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.text.WordUtils;

import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm.WordListener;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaColor;
import me.tyler.terraria.TerrariaData;
import me.tyler.terraria.TerrariaPlayer;

public class TerrariaPacketChatMessage extends TerrariaPacket {

	public TerrariaPacketChatMessage(byte t, byte[] p) {
		super(t, p);
	}

	public byte getPlayerId() {
		return getPayloadBuffer().get();
	}

	public boolean isPlayerNamed() {
		return getPlayerId() != 0xff;
	}

	public TerrariaColor getColor() {
		return TerrariaColor.getColor(getPayload(), 1);
	}

	public String getMessage() {
		return PacketUtil.readString(getPayload(), 4);
	}

	@Override
	public boolean onSending(Proxy proxy, Socket client) {

		String msg = getMessage();

		String[] splits = msg.split(" ");
		
		if (msg.startsWith("-")) {
			try {
				String command = splits[0].substring(1);

				if (command.equalsIgnoreCase("drop")) {

					if(splits.length >= 2){	
						float x = proxy.getThePlayer().getX();
						float y = proxy.getThePlayer().getY();
						short itemId = Short.parseShort(splits[1]);

						int amount = 1;
						int stackAmount = 1;

						if (splits.length > 2) {
							amount = Integer.parseInt(splits[2]);
						}

						if (splits.length > 3) {
							stackAmount = Integer.parseInt(splits[3]);
						}

						Random random = new Random();

						for (int i = 0; i < amount; i++) {
							TerrariaPacket packet = TerrariaPacketUpdateItemDrop.getItemDropPacket(400, x, y, random.nextFloat() * 3, random.nextFloat() * 3, stackAmount, 0, 0, itemId);
							proxy.sendPacketToServer(packet);
						}

						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.YELLOW, "Dropped " + amount + " " + TerrariaData.ITEMS.getValue(itemId)));
					}

				} else if(command.equalsIgnoreCase("particle")){
					
					if(splits.length >= 3){
						String victim = stitchArray(splits, 2);
						short effect = Short.parseShort(splits[1]);
						
						TerrariaPlayer player = proxy.getPlayer(victim);
						
						if(player != null){
							
							Cheats.particleEffect.put(player.getId(), effect);
							
							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.YELLOW, "Particle added to "+player.getName()));
							
						}else{
							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.RED, "No player was found named " + victim));
						}
					}

				} else if(command.equalsIgnoreCase("pvpinstakill")){
					
					Cheats.PVP_INSTAKILL = !Cheats.PVP_INSTAKILL;
					
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.BLUE, "PvP Instakiller "+ (Cheats.PVP_INSTAKILL ? "enabled" : "disabled")));
					
				} else if(command.equalsIgnoreCase("blockbuffs")){
					
					Cheats.BLOCK_BUFFS = !Cheats.BLOCK_BUFFS;
					
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.BLUE, "Buff Blocker "+ (Cheats.BLOCK_BUFFS ? "enabled" : "disabled")));
				} else if(command.equalsIgnoreCase("killme")){
					
					TerrariaPacket packet = TerrariaPacketKillMe.getKillMePacket(getPlayerId(), 0, 1000, true, " killed themselves!");

					proxy.sendPacketToClient(client, packet);
					proxy.sendPacketToServer(packet);
					
				} else if(command.equalsIgnoreCase("buff")){
					
					short buffId = Short.parseShort(splits[1]);
					
					TerrariaPacket packet = TerrariaPacketAddBuff.getBuffPacket(proxy.getThePlayer().getId(), buffId, Short.MAX_VALUE);
					
					proxy.sendPacketToClient(client, packet);
					
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.GREEN, "Added buff "+TerrariaData.BUFFS.getValue(buffId)));
					
				} else if (command.equalsIgnoreCase("replacer")) {

					if(splits.length >= 3){
						short from = Short.parseShort(splits[1]);
						short to = Short.parseShort(splits[2]);

						Cheats.replacer.put(from, to);

						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.BLUE, "Converting " + TerrariaData.PROJECTILES.getValue(from) + " to " + TerrariaData.PROJECTILES.getValue(to)));
					}

				} else if (command.equalsIgnoreCase("replaceother")) {

					if(splits.length >= 2){
						short to = Short.parseShort(splits[1]);

						Cheats.PROJECTILE_REPLACER_OTHER_TO = to;

						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.BLUE, "Converting other projectiles to " + TerrariaData.PROJECTILES.getValue(to)));
					}
					
				} else if (command.equalsIgnoreCase("track")){
					
					Cheats.TRACK_PROJECTILES = !Cheats.TRACK_PROJECTILES;
					
					if (Cheats.TRACK_PROJECTILES) {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.GREEN, "Projectile tracking enabled!"));
					} else {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.getColor(150, 25, 25), "Projectile tracking disabled"));
					}
					
				} else if (command.equalsIgnoreCase("maxhp")) {

					if(splits.length >= 2){
						short max = Short.parseShort(splits[1]);
						proxy.sendPacketToClient(client, TerrariaPacketPlayerHp.getPlayerHpPacket(proxy.getThePlayer().getId(), max, max));
						
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.RED, "Max HP set to "+max));
					}

				} else if (command.equalsIgnoreCase("maxmana")) {

					if(splits.length >= 2){
						short max = Short.parseShort(splits[1]);
						proxy.sendPacketToClient(client, TerrariaPacketMana.getManaPacket(proxy.getThePlayer().getId(), max, max));
						
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.BLUE, "Max Mana set to "+max));
					}
					
				} else if (command.equalsIgnoreCase("confetti")) {

					Random random = new Random();

					for (int i = 0; i < 100; i++) {
						int id = TerrariaData.getFreeProjectileId();

						float offsetX = random.nextFloat() * random.nextInt(1000);
						float offsetY = random.nextFloat() * random.nextInt(500);

						if (random.nextBoolean()) {
							offsetX = -offsetX;
						}
						
						TerrariaPacket projectile = TerrariaPacketProjectileUpdate.getProjectilePacket(id, proxy.getThePlayer().getX() + offsetX, proxy.getThePlayer().getY() - offsetY, random.nextFloat(), random.nextFloat(), 0, 0, proxy.getThePlayer().getId(), 178, 0);

						proxy.sendPacketToClient(client, projectile);
						proxy.sendPacketToServer(projectile);

					}

					proxy.sendPacketToClient(client, TerrariaPacketCombatText.getCombatTextPacket(proxy.getThePlayer().getX(), proxy.getThePlayer().getY(), TerrariaColor.getColor(0, 255, 0), "Party!!!!"));

				} else if (command.equalsIgnoreCase("kickme")) {

					proxy.sendPacketToClient(client, TerrariaPacketDisconnect.getKickPacket("Okee dokee!"));
					proxy.close();

				} else if (command.equalsIgnoreCase("vac")) {
					Cheats.VAC_ENABLED = !Cheats.VAC_ENABLED;

					if (Cheats.VAC_ENABLED) {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.getColor(25, 150, 50), "Vac Enabled"));
					} else {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.getColor(150, 25, 25), "Vac Disabled"));
					}

				} else if (command.equalsIgnoreCase("boss")) {

					if (splits.length == 2) {
						String name = splits[1];

						int boss = TerrariaData.BOSSES.getKey(name);

						if (boss == Short.MIN_VALUE) {
							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.getColor(200, 50, 50), "No boss with name " + name+", do -boss for a list of bosses"));
						} else {

							TerrariaPacket packet = TerrariaPacketSpawnBoss.getSpawnBossPacket(proxy.getThePlayer().getId(), boss);

							proxy.sendPacketToServer(packet);

							proxy.sendPacketToClient(client, getMessagePacket(0xff, TerrariaColor.getColor(200, 50, 200), "Attempted to spawn " +name));
						}

					} else {
						
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.PURPLE, "Bosses:"));
						
						String bosses = "";
						
						for (String boss : TerrariaData.BOSSES.values()) {
							bosses += boss+" ";
						}
						
						String wrapped = WordUtils.wrap(bosses, 127, "\r\n", true);
						
						String[] msgs = wrapped.split("\r\n");
						
						for(String str : msgs){
							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.PURPLE, str));
						}
						

					}

				} else if (command.equalsIgnoreCase("teleport")) {
					if (splits.length >= 2) {

						String player = stitchArray(splits, 1);

						TerrariaPlayer pl = proxy.getPlayer(player);

						if (pl == null) {
							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.RED, "No player was found named " + player));
						} else {
							proxy.sendPacketToClient(client, TerrariaPacketPortalTeleport.getPortalTeleportPacket(proxy.getThePlayer().getId(), (short) 0, pl.getX(), pl.getY(), 0, 0));
						}
					}

				} else if (command.equalsIgnoreCase("god")) {

					Cheats.BLOCK_DAMAGE = !Cheats.BLOCK_DAMAGE;

					if (Cheats.BLOCK_DAMAGE) {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.GREEN, "God mode enabled"));
					} else {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.RED, "God mode disabled"));
					}
				} else if (command.equalsIgnoreCase("pos")) {

					float x = proxy.getThePlayer().getX();
					float y = proxy.getThePlayer().getY();

					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.YELLOW, x + "/" + y));
				} else if (command.equalsIgnoreCase("vachere")) {

					float x = proxy.getThePlayer().getX();
					float y = proxy.getThePlayer().getY();

					Cheats.VAC_POS_ENABLED = !Cheats.VAC_POS_ENABLED;
					Cheats.VAC_POS_X = x;
					Cheats.VAC_POS_Y = y;

					if (Cheats.VAC_POS_ENABLED) {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.GREEN, "Vac set to " + x + "/" + y));
					} else {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.RED, "Vac disabled"));
					}
					
				} else if (command.equalsIgnoreCase("critter")) {

					if(splits.length >= 2){
						short id = Short.parseShort(splits[1]);

						TerrariaPacket packet = TerrariaPacketReleaseNpc.getReleaseNpcPacket((int) proxy.getThePlayer().getX(), (int) proxy.getThePlayer().getY(), id, (byte) 0);

						proxy.sendPacketToServer(packet);	
					}else{
						
					}
				} else if (command.equalsIgnoreCase("lookup")){
					
					if(splits.length > 1){
						
						String term = stitchArray(splits, 1);
						
						Map<Integer, String> buffMatches = TerrariaData.BUFFS.getValuesLike(term);
						Map<Integer, String> projMatches = TerrariaData.PROJECTILES.getValuesLike(term);
						Map<Integer, String> itemMatches = TerrariaData.ITEMS.getValuesLike(term);
						
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.GREEN, "Search Results:"));
						
						if(buffMatches.size() > 0){
							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.GREEN, "Buffs:"));
							
							int i = 0;
							
							for(int id : buffMatches.keySet()){
								i++;
								
								proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.YELLOW, buffMatches.get(id)+" - "+id));
								
								if(i > 5){
									break;
								}
							}
						}
						
						if(projMatches.size() > 0){
							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.GREEN, "Projectiles:"));
							
							int i = 0;
							
							for(int id : projMatches.keySet()){
								i++;
								
								proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.YELLOW, projMatches.get(id)+" - "+id));
								
								if(i > 5){
									break;
								}
							}
						}
						
						if(itemMatches.size() > 0){
							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.GREEN, "Items:"));
							
							int i = 0;
							
							for(int id : itemMatches.keySet()){
								i++;
								
								proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.YELLOW, itemMatches.get(id)+" - "+id));
								
								if(i > 5){
									break;
								}
							}
						}
						
					}
					
				} else if (command.equalsIgnoreCase("help")){
					
					String[] help = new String[] {
							"-drop [item id] <amount of stacks to drop> <amount in each stack>",
							"-particle [particle id] [player name]",
							"-pvpinstakill",
							"-blockbuffs",
							"-killme",
							"-buff [buff id]",
							"-replacer [id to replace] [new projectile id]",
							"-replaceother [new projectile id]",
							"-track",
							"-maxhp [new max hp]",
							"-maxmana [new max mana]",
							"-confetti",
							"-kickme",
							"-vac",
							"-vachere",
							"-boss [boss id]",
							"-teleport [player name]",
							"-pos",
							"-critter [critter id]",
							"-god"
						};
					
					int index = 0;
					int max = help.length / 5;
					
					if(splits.length >= 2){
						
						int pageNum = Integer.parseInt(splits[1]) - 1;
						
						if(pageNum >= max){
							pageNum = max-1;
						}else if(pageNum <= 0){
							pageNum = 0;
						}
						
						index = pageNum * 5;
					}
					

					
					
					proxy.sendPacketToClient(client, getMessagePacket(0xff, TerrariaColor.GREEN, "TPE Commands Page: "+((index / 5)+1)+"/"+max+" (/help [page #]): "));
					
					for(int i = index; i < index+5;i++){
						proxy.sendPacketToClient(client, getMessagePacket(0xff, TerrariaColor.YELLOW, help[i]));
					}
					
				} else {
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.getColor(200, 50, 50), "Unknown command '" + command + "'"));
					proxy.sendPacketToClient(client, TerrariaPacketCombatText.getCombatTextPacket(proxy.getThePlayer().getX(), proxy.getThePlayer().getY(), TerrariaColor.getColor(255, 0, 0), "Unknown command '" + command + "'"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket(0xff, TerrariaColor.getColor(255, 0, 0), "ERROR >> " + e.getLocalizedMessage()));
				proxy.sendPacketToClient(client, TerrariaPacketCombatText.getCombatTextPacket(proxy.getThePlayer().getX(), proxy.getThePlayer().getY(), TerrariaColor.getColor(255, 0, 0), "Error executing command!"));
			}
			return false;
		}

		return true;
	}
	
	private String stitchArray(String[] array, int offset){
		String str = "";
		
		for(int i = offset;i < array.length;i++){
			str += array[i];
			
			if(i < array.length-1){
				str += " ";
			}
		}
		
		return str;
	}

	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		return true;
	}

	public static TerrariaPacket getMessagePacket(int player, TerrariaColor color, String msg) {

		ByteBuffer buf = ByteBuffer.allocate(1 + 3 + msg.length() + 1).order(ByteOrder.LITTLE_ENDIAN);//player + color(3) + msg length + msg length byte

		buf.put((byte) player);
		buf.put(color.getBytes());//Client cant choose color
		
		byte[] msgBytes = null;

		try {
			msgBytes = msg.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		buf.put((byte) msgBytes.length);

		buf.put(msgBytes);

		TerrariaPacket packet = new TerrariaPacket(PacketType.CHAT_MESSAGE.getId(), buf.array());

		return packet;
	}

}
