package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.text.WordUtils;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaColor;
import me.tyler.terraria.TerrariaData;
import me.tyler.terraria.TerrariaPlayer;
import me.tyler.terraria.script.Script;
import me.tyler.terraria.script.Script.CommandDescription;

public class TerrariaPacketChatMessage extends TerrariaPacket {

	public TerrariaPacketChatMessage(byte t, byte[] p) {
		super(t, p);
	}
	
	public TerrariaPacketChatMessage(int playerId, TerrariaColor color, String message) {
		
		super(PacketType.CHAT_MESSAGE.getId(), getMessagePacket(playerId, color, message));
		
	}
	
	public TerrariaPacketChatMessage(TerrariaColor color, String message){
		this(0xff, color, message);
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
	public boolean onSending(Proxy proxy) {
		
		String msg = getMessage();

		String[] splits = msg.split(" ");
		
		if (msg.startsWith("-")) {
			
			try {
				String command = splits[0].substring(1);
				
				for(Script script : proxy.getScriptManager().getAllScripts()){
					try{
						Object obj = script.invoke("chat_command", proxy, command, Arrays.copyOfRange(splits, 1, splits.length));
					
						if(obj == null){
							continue;
						}
						
						boolean b = (boolean) obj;
						
						if(b){
							return false;
						}
					}catch(NoSuchMethodException e){
					}
				}

				if (command.equalsIgnoreCase("drop")) {

					if(splits.length >= 2){	
						float x = proxy.getThePlayer().getX();
						float y = proxy.getThePlayer().getY();
						int itemId = 0;
						int offset = 0;
						if(splits[1].startsWith("\"")){
							
							String full = splits[1].substring(1);
							
							if(splits[1].endsWith("\"")){
								full = full.substring(0, full.length()-1);
							}else{
								full += " ";
								for(int i = 2;i < splits.length;i++){
									String s = splits[i];
									if(s.endsWith("\"")){
										full += s.substring(0, s.length()-1);
										offset = i-1;
										break;
									}else{
										full += s+" ";
									}
								}
							}
							
							itemId = TerrariaData.ITEMS.getKey(full);
							
							if(itemId == Short.MIN_VALUE){
								proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.RED, "No item named "+full+" was found!"));
								return false;
							}
							
						}else{
							itemId = Short.parseShort(splits[1]);
						}

						int amount = 1;
						int stackAmount = 1;

						if (splits.length > 2+offset) {
							amount = Integer.parseInt(splits[2 + offset]);
						}

						if (splits.length > 3+offset) {
							stackAmount = Integer.parseInt(splits[3 + offset]);
						}

						Random random = new Random();

						for (int i = 0; i < amount; i++) {
							TerrariaPacket packet = TerrariaPacketUpdateItemDrop.getItemDropPacket(400, x, y, random.nextFloat() * 3, random.nextFloat() * 3, stackAmount, 0, 1, itemId);
							proxy.sendPacketToServer(packet);
						}

						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.YELLOW, "Dropped " + amount + " " + TerrariaData.ITEMS.getValue(itemId)));
					}

				} else if(command.equalsIgnoreCase("killme")){
					proxy.getThePlayer().kill("");
				} else if (command.equalsIgnoreCase("replace")) {

					if(splits.length >= 3){
						int from = 0;
						int to = 0;
						
						int offset = 0;
						
						if(splits[1].startsWith("\"")){
							
							String full = splits[1].substring(1);
							
							if(splits[1].endsWith("\"")){
								full = full.substring(0, full.length()-1);
							}else{
								full += " ";
								for(int i = 2;i < splits.length;i++){
									String s = splits[i];
									if(s.endsWith("\"")){
										full += s.substring(0, s.length()-1);
										offset = i-1;
										break;
									}else{
										full += s+" ";
									}
								}
							}
							
							from = TerrariaData.PROJECTILES.getKey(full);
							
							if(from == Short.MIN_VALUE){
								proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.RED, "No projectile named "+full+" was found!"));
								return false;
							}
							
						}else{
							from = Short.parseShort(splits[1]);
						}
						
						if(splits[2+offset].startsWith("\"")){
							String full = splits[2+offset].substring(1);
							
							if(splits[2+offset].endsWith("\"")){
								full = full.substring(0, full.length()-1);
							}else{
								full += " ";
								for(int i = 3+offset;i < splits.length;i++){
									String s = splits[i];
									if(s.endsWith("\"")){
										full += s.substring(0, s.length()-1);
										break;
									}else{
										full += s+" ";
									}
								}
							}
							
							to = TerrariaData.PROJECTILES.getKey(full);
							
							if(to == Short.MIN_VALUE){
								proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.RED, "No projectile named "+full+" was found!"));
								return false;
							}
						}else{
							to = Short.parseShort(splits[2+offset]);
						}
						
						if(to < 0){
							Cheats.replacer.remove((short) from);
						}else{
							Cheats.replacer.put((short) from, (short) to);
						}

						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.BLUE, "Converting " + TerrariaData.PROJECTILES.getValue(from) + " to " + TerrariaData.PROJECTILES.getValue(to)));
					}

				} else if (command.equalsIgnoreCase("replaceother")) {

					if(splits.length >= 2){
						short to = 0;
						if(splits[1].startsWith("\"")){
							
							String full = splits[1].substring(1);
							
							if(splits[1].endsWith("\"")){
								full = full.substring(0, full.length()-1);
							}else{
								full += " ";
								for(int i = 2;i < splits.length;i++){
									String s = splits[i];
									if(s.endsWith("\"")){
										full += s.substring(0, s.length()-1);
										break;
									}else{
										full += s+" ";
									}
								}
							}
							
							to = (short) TerrariaData.PROJECTILES.getKey(full);
							
							if(to == Short.MIN_VALUE){
								proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.RED, "No projectile named "+full+" was found!"));
								return false;
							}
							
						}else{
							to = Short.parseShort(splits[1]);
						}

						Cheats.PROJECTILE_REPLACER_OTHER_TO = to;

						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.BLUE, "Converting other projectiles to " + TerrariaData.PROJECTILES.getValue(to)));
					}else{
						Cheats.PROJECTILE_REPLACER_OTHER_TO = -1;
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.RED, "Replacing other, DISABLED!"));
					}
					
				} else if (command.equalsIgnoreCase("track")){
					
					Cheats.TRACK_PROJECTILES = !Cheats.TRACK_PROJECTILES;
					
					if (Cheats.TRACK_PROJECTILES) {
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.GREEN, "Projectile tracking enabled!"));
					} else {
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.getColor(150, 25, 25), "Projectile tracking disabled"));
					}
					
				} else if (command.equalsIgnoreCase("maxhp")) {

					if(splits.length >= 2){
						short max = Short.parseShort(splits[1]);
						proxy.sendPacketToClient(new TerrariaPacketPlayerHp(proxy.getThePlayer().getId(), max, max));
						
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.RED, "Max HP set to "+max));
					}

				} else if (command.equalsIgnoreCase("maxmana")) {

					if(splits.length >= 2){
						short max = Short.parseShort(splits[1]);
						proxy.sendPacketToClient(TerrariaPacketMana.getManaPacket(proxy.getThePlayer().getId(), max, max));
						
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.BLUE, "Max Mana set to "+max));
					}
					
				} else if (command.equalsIgnoreCase("confetti")) {

					Random random = new Random();

					for (int i = 0; i < 100; i++) {
						int id = proxy.getFreeProjectileId();

						float offsetX = random.nextFloat() * random.nextInt(1000);
						float offsetY = random.nextFloat() * random.nextInt(500);

						if (random.nextBoolean()) {
							offsetX = -offsetX;
						}
						
						TerrariaPacket projectile = TerrariaPacketProjectileUpdate.getProjectilePacket(id, proxy.getThePlayer().getX() + offsetX, proxy.getThePlayer().getY() - offsetY, random.nextFloat(), random.nextFloat(), 0, 0, proxy.getThePlayer().getId(), 178, 0);

						proxy.sendPacketToClient(projectile);
						proxy.sendPacketToServer(projectile);

					}

					proxy.sendPacketToClient(new TerrariaPacketCombatText(proxy.getThePlayer().getX(), proxy.getThePlayer().getY(), TerrariaColor.getColor(0, 255, 0), "Party!!!!"));

				} else if (command.equalsIgnoreCase("kickme")) {

					proxy.getThePlayer().kick("Bye bye!");

				} else if (command.equalsIgnoreCase("vac")) {
					Cheats.VAC_ENABLED = !Cheats.VAC_ENABLED;

					if (Cheats.VAC_ENABLED) {
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.getColor(25, 150, 50), "Vac Enabled"));
					} else {
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.getColor(150, 25, 25), "Vac Disabled"));
					}

				} else if (command.equalsIgnoreCase("boss")) {

					if (splits.length == 2) {
						String name = splits[1];

						int boss = TerrariaData.BOSSES.getKey(name);

						if (boss == Short.MIN_VALUE) {
							proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.getColor(200, 50, 50), "No boss with name " + name+", do -boss for a list of bosses"));
						} else {

							TerrariaPacket packet = TerrariaPacketSpawnBoss.getSpawnBossPacket(proxy.getThePlayer().getId(), boss);

							proxy.sendPacketToServer(packet);

							proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.getColor(200, 50, 200), "Attempted to spawn " +name));
						}

					} else {
						
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.PURPLE, "Bosses:"));
						
						String bosses = "";
						
						for (String boss : TerrariaData.BOSSES.values()) {
							bosses += boss+" ";
						}
						
						String wrapped = WordUtils.wrap(bosses, 127, "\r\n", true);
						
						String[] msgs = wrapped.split("\r\n");
						
						for(String str : msgs){
							proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.PURPLE, str));
						}
						

					}

				} else if (command.equalsIgnoreCase("teleport")) {
					if (splits.length >= 2) {

						String player = stitchArray(splits, 1);

						TerrariaPlayer pl = proxy.getPlayer(player);

						if (pl == null) {
							proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.RED, "No player was found named " + player));
						} else {
							proxy.sendPacketToClient(TerrariaPacketPortalTeleport.getPortalTeleportPacket(proxy.getThePlayer().getId(), (short) 0, pl.getX(), pl.getY(), 0, 0));
						}
						
					}

				/*} else if (command.equalsIgnoreCase("god")) {

					Cheats.BLOCK_DAMAGE = !Cheats.BLOCK_DAMAGE;

					if (Cheats.BLOCK_DAMAGE) {
						proxy.sendPacketToClient(client, new TerrariaPacketChatMessage(TerrariaColor.GREEN, "God mode enabled"));
					} else {
						proxy.sendPacketToClient(client, new TerrariaPacketChatMessage(TerrariaColor.RED, "God mode disabled"));
					}*/
				} else if (command.equalsIgnoreCase("pos")) {

					float x = proxy.getThePlayer().getX();
					float y = proxy.getThePlayer().getY();

					proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.YELLOW, x + "/" + y));
				} else if (command.equalsIgnoreCase("vachere")) {

					float x = proxy.getThePlayer().getX();
					float y = proxy.getThePlayer().getY();

					Cheats.VAC_POS_ENABLED = !Cheats.VAC_POS_ENABLED;
					Cheats.VAC_POS_X = x;
					Cheats.VAC_POS_Y = y;

					if (Cheats.VAC_POS_ENABLED) {
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.GREEN, "Vac set to " + x + "/" + y));
					} else {
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.RED, "Vac disabled"));
					}
				}else if(command.equalsIgnoreCase("vacto")){
					
					if(splits.length == 1){
						Cheats.VAC_TO = null;
					}else{
						
						String player = splits[1];
						
						if(proxy.getPlayer(player) == null){
							proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.RED, "No player named "+player));
						}
						
						Cheats.VAC_TO = player;
						
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.GREEN, "Sending all items to "+player));
						
					}
				} else if(command.equalsIgnoreCase("hideme")){
					Cheats.HIDE_POSITION = !Cheats.HIDE_POSITION;
					
					if (Cheats.HIDE_POSITION) {
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.GREEN, "Players will no longer be able to find you by teleporting"));
					} else {
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.RED, "You can now be found by teleportation"));
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
						
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.GREEN, "Search Results:"));
						
						if(buffMatches.size() > 0){
							proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.GREEN, "Buffs:"));
							
							int i = 0;
							
							for(int id : buffMatches.keySet()){
								i++;
								
								proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.YELLOW, buffMatches.get(id)+" - "+id));
								
								if(i > 5){
									break;
								}
							}
						}
						
						if(projMatches.size() > 0){
							proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.GREEN, "Projectiles:"));
							
							int i = 0;
							
							for(int id : projMatches.keySet()){
								i++;
								
								proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.YELLOW, projMatches.get(id)+" - "+id));
								
								if(i > 5){
									break;
								}
							}
						}
						
						if(itemMatches.size() > 0){
							proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.GREEN, "Items:"));
							
							int i = 0;
							
							for(int id : itemMatches.keySet()){
								i++;
								
								proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.YELLOW, itemMatches.get(id)+" - "+id+" [i/s1:"+id+"]"));
								
								if(i > 5){
									break;
								}
							}
						}
						
					}
				} else if (command.equalsIgnoreCase("help")){
					
					List<CommandDescription> commands = proxy.getScriptManager().getCommands();
					
					if(commands.size() == 0){
						proxy.getThePlayer().sendMessage("No commands!");
						return false;
					}
					
					int index = 0;
					int max = commands.size() / 5;
					
					if(splits.length >= 2){
						
						int pageNum = Integer.parseInt(splits[1]) - 1;
						
						if(pageNum >= max){
							pageNum = max-1;
						}else if(pageNum <= 0){
							pageNum = 0;
						}
						
						index = pageNum * 5;
					}
					
					proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.GREEN, "TPE Commands Page: "+((index / 5)+1)+"/"+max+" (/help [page #]): "));
					

					
					for(int i = index; i < index+5;i++){
						proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.YELLOW, commands.get(i).toString()));
					}
					
				} else {
					proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.getColor(200, 50, 50), "Unknown command '" + command + "'"));
					proxy.sendPacketToClient(new TerrariaPacketCombatText(proxy.getThePlayer().getX(), proxy.getThePlayer().getY(), TerrariaColor.getColor(255, 0, 0), "Unknown command '" + command + "'"));
				}
			} catch(NumberFormatException e){
				proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.getColor(255, 0, 0), "The command expected you to enter a number but instead you put in letters!"));
				proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.getColor(255, 0, 0), "Some commands support names however they require the name to be put around quotations."));
				proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.getColor(255, 0, 0), "For example: -drop \"Iron Axe\""));
				proxy.sendPacketToClient(new TerrariaPacketCombatText(proxy.getThePlayer().getX(), proxy.getThePlayer().getY(), TerrariaColor.getColor(255, 0, 0), "Error executing command!"));
			} catch (Exception e) {
				e.printStackTrace();
				proxy.sendPacketToClient(new TerrariaPacketChatMessage(TerrariaColor.getColor(255, 0, 0), "ERROR >> " + e.getLocalizedMessage()));
				proxy.sendPacketToClient(new TerrariaPacketCombatText(proxy.getThePlayer().getX(), proxy.getThePlayer().getY(), TerrariaColor.getColor(255, 0, 0), "Error executing command!"));
			}
			return false;
		}

		return super.onSending(proxy);
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

	private static byte[] getMessagePacket(int player, TerrariaColor color, String msg) {

		ByteBuffer buf = ByteBuffer.allocate(1 + 3 + PacketUtil.calculateLength(msg) + 1).order(ByteOrder.LITTLE_ENDIAN);//player + color(3) + msg length + msg length byte

		buf.put((byte) player);
		buf.put(color.getBytes());//Client cant choose color
		
		PacketUtil.writeString(buf, msg);
		
		return buf.array();
	}

}
