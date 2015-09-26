package me.tyler.terraria.packets;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

import me.tyler.terraria.Bosses;
import me.tyler.terraria.Buffs;
import me.tyler.terraria.Cheats;
import me.tyler.terraria.Items;
import me.tyler.terraria.Npc;
import me.tyler.terraria.PacketType;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaColor;
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

					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 200, 50), "Dropped " + amount + " " + Items.getItemName(itemId)));
				} else if(command.equalsIgnoreCase("party")){
					
					String victim = stitchArray(splits, 1);
					
					TerrariaPlayer player = proxy.getPlayer(victim);
					
					if(player != null){
						
						Cheats.CONFETTI_FOLLOW = player.getId();
						
					}else{
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 50, 50), "No player was found named " + victim));
					}
				} else if(command.equalsIgnoreCase("pvpinstakill")){
					
					Cheats.PVP_INSTAKILL = !Cheats.PVP_INSTAKILL;
					
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(100, 150, 190), "PvP Instakiller "+ (Cheats.PVP_INSTAKILL ? "enabled" : "disabled")));
				} else if(command.equalsIgnoreCase("blockbuffs")){
					
					Cheats.BLOCK_BUFFS = !Cheats.BLOCK_BUFFS;
					
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(100, 150, 190), "Buff Blocker "+ (Cheats.BLOCK_BUFFS ? "enabled" : "disabled")));
				} else if(command.equalsIgnoreCase("killme")){
					
					TerrariaPacket packet = TerrariaPacketKillMe.getKillMePacket(getPlayerId(), 0, 1000, true, "Ouch!");

					proxy.sendPacketToClient(client, packet);
					proxy.sendPacketToServer(packet);
					
				} else if(command.equalsIgnoreCase("buff")){
					
					short buffId = Short.parseShort(splits[1]);
					
					TerrariaPacket packet = TerrariaPacketAddBuff.getBuffPacket(proxy.getThePlayer().getId(), buffId, Short.MAX_VALUE);
					
					proxy.sendPacketToClient(client, packet);
					
					proxy.sendPacketToClient(client, TerrariaPacketCombatText.getCombatTextPacket(proxy.getThePlayer().getX(), proxy.getThePlayer().getY(), TerrariaColor.getColor(0, 255, 0), "Added buff "+Buffs.getBuffName(buffId)));
					
				} else if (command.equalsIgnoreCase("replacer")) {

					short from = Short.parseShort(splits[1]);
					short to = Short.parseShort(splits[2]);

					Cheats.replacer.put(from, to);

					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(0, 255, 0), "Converting " + from + " to " + to));
				} else if (command.equalsIgnoreCase("replaceother")) {

					short to = Short.parseShort(splits[1]);

					Cheats.PROJECTILE_REPLACER_OTHER_TO = to;

					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(0, 255, 0), "Converting others to " + to));
				} else if (command.equalsIgnoreCase("maxhp")) {

					short max = Short.parseShort(splits[1]);
					proxy.sendPacketToClient(client, TerrariaPacketPlayerHp.getPlayerHpPacket(proxy.getThePlayer().getId(), max, max));

				} else if (command.equalsIgnoreCase("maxmana")) {

					short max = Short.parseShort(splits[1]);
					proxy.sendPacketToClient(client, TerrariaPacketMana.getManaPacket(proxy.getThePlayer().getId(), max, max));

				} else if (command.equalsIgnoreCase("confetti")) {

					Random random = new Random();

					for (int i = 0; i < 100; i++) {
						int id = i + 50;

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
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(25, 150, 50), "Vac Enabled"));
					} else {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(150, 25, 25), "Vac Disabled"));
					}

				} else if (command.equalsIgnoreCase("boss")) {

					if (splits.length == 2) {
						String name = splits[1];

						short boss = Bosses.getBossByName(name);

						if (boss == -1) {
							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 50, 50), "No boss with name " + name));
						} else {

							TerrariaPacket packet = TerrariaPacketSpawnBoss.getSpawnBossPacket(proxy.getThePlayer().getId(), boss);

							proxy.sendPacketToServer(packet);

							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 50, 200), "Attempted to spawn " + Bosses.getBossName(boss)));
						}

					} else {
						StringBuffer buf = new StringBuffer();

						for (String boss : Bosses.getBossNames()) {
							buf.append(boss + ", ");
						}
						buf.setLength(buf.length() - 1);

						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 50, 200), "Boss List: " + buf.toString()));

					}

				} else if (command.equalsIgnoreCase("teleport")) {
					if (splits.length >= 2) {

						String player = stitchArray(splits, 1);

						TerrariaPlayer pl = proxy.getPlayer(player);

						if (pl == null) {
							proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 50, 50), "No player was found named " + player));
						} else {
							proxy.sendPacketToClient(client, TerrariaPacketPortalTeleport.getPortalTeleportPacket(proxy.getThePlayer().getId(), (short) 0, pl.getX(), pl.getY(), 0, 0));
						}
					}

				} else if (command.equalsIgnoreCase("god")) {

					Cheats.BLOCK_DAMAGE = !Cheats.BLOCK_DAMAGE;

					if (Cheats.BLOCK_DAMAGE) {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(25, 150, 50), "Damage Blocking Enabled"));
					} else {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(150, 25, 25), "Damage Blocking Disabled"));
					}
				} else if (command.equalsIgnoreCase("pos")) {

					float x = proxy.getThePlayer().getX();
					float y = proxy.getThePlayer().getY();

					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 200, 25), x + "/" + y));
				} else if (command.equalsIgnoreCase("vachere")) {

					float x = proxy.getThePlayer().getX();
					float y = proxy.getThePlayer().getY();

					Cheats.VAC_POS_ENABLED = !Cheats.VAC_POS_ENABLED;
					Cheats.VAC_POS_X = x;
					Cheats.VAC_POS_Y = y;

					if (Cheats.VAC_POS_ENABLED) {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(25, 200, 25), "Vac set to " + x + "/" + y));
					} else {
						proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 25, 25), "Vac disabled"));
					}
				} else if (command.equalsIgnoreCase("critter")) {

					short id = Short.parseShort(splits[1]);

					TerrariaPacket packet = TerrariaPacketReleaseNpc.getReleaseNpcPacket((int) proxy.getThePlayer().getX(), (int) proxy.getThePlayer().getY(), id, (byte) 0);

					proxy.sendPacketToServer(packet);

				} else {
					proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(200, 50, 50), "Unknown command '" + command + "'"));
					proxy.sendPacketToClient(client, TerrariaPacketCombatText.getCombatTextPacket(proxy.getThePlayer().getX(), proxy.getThePlayer().getY(), TerrariaColor.getColor(255, 0, 0), "Unknown command '" + command + "'"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				proxy.sendPacketToClient(client, TerrariaPacketChatMessage.getMessagePacket((byte) 0xff, TerrariaColor.getColor(255, 0, 0), "ERROR >> " + e.getLocalizedMessage()));
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

	public static TerrariaPacket getMessagePacket(byte player, TerrariaColor color, String msg) {

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

		TerrariaPacket packet = new TerrariaPacket(PacketType.CHAT_MESSAGE.getId(), buf.array());

		return packet;
	}

}
