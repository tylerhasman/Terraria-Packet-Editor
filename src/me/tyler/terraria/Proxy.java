package me.tyler.terraria;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import me.tyler.terraria.hooks.PacketHook;
import me.tyler.terraria.net.ConnectionFactory;
import me.tyler.terraria.net.NetworkConnection;
import me.tyler.terraria.net.SocketConnectionFactory;
import me.tyler.terraria.net.SocketNetworkConnection;
import me.tyler.terraria.packets.TerrariaPacket;
import me.tyler.terraria.packets.TerrariaPacketConnectionRequest;
import me.tyler.terraria.packets.TerrariaPacketContinue;
import me.tyler.terraria.packets.TerrariaPacketGetSection;
import me.tyler.terraria.packets.TerrariaPacketInventorySlot;
import me.tyler.terraria.packets.TerrariaPacketMana;
import me.tyler.terraria.packets.TerrariaPacketPlayerHp;
import me.tyler.terraria.packets.TerrariaPacketPlayerInfo;
import me.tyler.terraria.packets.TerrariaPacketUUID;
import me.tyler.terraria.packets.TerrariaPacketUpdatePlayer;
import me.tyler.terraria.packets.TerrariaPacketUpdatePlayerBuff;
import me.tyler.terraria.packets.TerrariaPacketWorldInfo;
import me.tyler.terraria.script.Script;
import me.tyler.terraria.script.ScriptManager;

public class Proxy {

	private String targetIp;
	private int targetPort;
	private boolean isConnected;
	private TerrariaPlayerLocal thePlayer;
	private Map<Byte, TerrariaPlayer> players;
	private Map<Short, TerrariaItemDrop> itemsOnGround;
	private List<Npc> npcs;
	private boolean isConnectionIniatializationDone;
	private long lastScriptCycle;
	private List<PacketHook> hooks;
	private List<Short> projectileIdsInUse;
	private WorldInfo worldInfo;
	private NetworkConnection client, server;
	private ConnectionFactory connectionFactory;
	private ScriptManager scriptManager;
	
	public Proxy(String ip, int port, NetworkConnection client) {
		players = new HashMap<>();
		npcs = new ArrayList<>();
		itemsOnGround = new HashMap<>();
		isConnected = false;
		isConnectionIniatializationDone = false;
		lastScriptCycle = System.currentTimeMillis();
		hooks = new ArrayList<>();
		projectileIdsInUse = new ArrayList<Short>();
		targetIp = ip;
		targetPort = port;
		thePlayer = new TerrariaPlayerLocal((byte) -1, this);
		this.client = client;
		connectionFactory = new SocketConnectionFactory();
		scriptManager = new ScriptManager(new File("scripts/"));
		scriptManager.loadScripts();
	}
	
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}	
	
	public boolean isProjectileIdFree(short id){
		return projectileIdsInUse.indexOf(id) < 0;
	}
	
	public void freeProjectileId(short id){
		
		int index = projectileIdsInUse.indexOf(id);
		
		if(index >= 0 && index < projectileIdsInUse.size()){
			projectileIdsInUse.remove(index);
		}
		
	}
	
	public void addProjectileId(short projectileId) {
		projectileIdsInUse.add(projectileId);
	}
	
	public short getFreeProjectileId(){
		
		for(short s = 0; s < Short.MAX_VALUE;s++){
			if(!projectileIdsInUse.contains(s)){
				
				projectileIdsInUse.add(s);
				
				return s;
			}
		}
		
		//This should realistically never happen
		throw new RuntimeException("No new projectile ids available!");
		
	}
	
	public void addHook(PacketHook hook){
		hooks.add(hook);
	}
	
	public List<PacketHook> getHooksOfType(PacketType type){
		return hooks.stream().filter(hook -> hook.getPacketType() == type).collect(Collectors.toList());
	}
	
	public void connect() throws IOException {
		server = connectionFactory.connect(InetAddress.getByName(targetIp), targetPort);
		isConnected = true;
	}
	
	public NetworkConnection getServer() {
		return server;
	}
	
	public void cycle(){
		
		try{
			
			TerrariaPacket sending = client.readPacket();
			TerrariaPacket recv = server.readPacket();
			
			if(recv != null){
				if(recv.onReceive(this)){
					boolean result = true;
					for(PacketHook hook : getHooksOfType(PacketType.getTypeFromId(recv.getType()))){
						if(!hook.onRecieve(recv)){
							result = false;
						}
					}
					if(result){
						sendPacketToClient(recv);
					}
				}
			}
			
			if(sending != null){
				if(sending.onSending(this)){
					boolean result = true;
					for(PacketHook hook : getHooksOfType(PacketType.getTypeFromId(sending.getType()))){
						if(!hook.onSend(sending)){
							result = false;
						}
					}
					if(result){
						sendPacketToServer(sending);
					}
				}
			}
			
			if(isConnectionIniatializationDone){
				if(System.currentTimeMillis() - lastScriptCycle >= 500){
					for(Script script : scriptManager.getAllScripts()){
						if(script.doesCycle()){
							script.invoke("do_cycle", this);
						}
					}	
					lastScriptCycle = System.currentTimeMillis();
					
					for(TerrariaPlayer player : getPlayers()){
						player.cycle();
					}
					
					thePlayer.cycle();
				}
			}
			
		}catch(Exception e){
			isConnected = false;
			e.printStackTrace();
		}
		
	}
	
	public boolean isConnectionIniatializationDone() {
		return isConnectionIniatializationDone;
	}
	
	public void setConnectionIniatializationDone(boolean flag) {
		
		if(!isConnectionIniatializationDone){
			if(flag){
				for(Script script : scriptManager.getAllScripts()){
					try {
						script.invoke("game_state_ready", this);
					} catch (NoSuchMethodException e) {
					}
				}
				getThePlayer().sendStatus(getDefaultStatus());
			}
		}
		
		this.isConnectionIniatializationDone = flag;
	}
	
	private static String getDefaultStatus(){
		
		String msg = new String(Base64.getDecoder().decode("VGVycmFyaWEgUGFja2V0IEVkaXRvciBlbmFibGVkIVxyXG5DcmVhdGVkIGJ5IFR5bGVyIEhcclxuL3UvZmlyc3Rib3d0aWU="));
		
		msg = msg.replace("\\r\\n", "\r\n");
		
		return msg;
		
	}
	
	
	public boolean isConnected() {
		return isConnected;
	}

	
	public void close() throws IOException{
		server.close();
		isConnected = false;
	}
	
	public void sendPacketToClient(TerrariaPacket packet){
		try {
			client.sendData(packet);
		} catch (IOException e) {
			e.printStackTrace();
			isConnected = false;
		}
	}
	
	public void sendPacketToServer(TerrariaPacket packet) {
		
		try {
			server.sendData(packet);
		} catch (IOException e) {
			e.printStackTrace();
			isConnected = false;
		}
	}
	
	public TerrariaItemDrop getDroppedItem(int id){
		return itemsOnGround.get(id);
	}

	public void setDroppedItem(TerrariaItemDrop item){
		itemsOnGround.put(item.getItemId(), item);
	}
	
	public TerrariaPlayerLocal getThePlayer() {
		return thePlayer;
	}
	
	public void setThePlayer(TerrariaPlayerLocal thePlayer) {
		this.thePlayer = thePlayer;
	}
	
	public NetworkConnection getClient() {
		return client;
	}
	
	public TerrariaPlayer getPlayer(byte id){
		
		if(id == getThePlayer().getId()){
			return getThePlayer();
		}
		
		if(!players.containsKey(id)){
			players.put(id, new TerrariaPlayer(id, this));
		}
		
		return players.get(id);
	}
	
	public void addNpc(Npc npc){
		npcs.add(npc);
	}
	
	public Npc getNpc(int id){
		for(Npc npc : npcs){
			if(npc.getId() == id){
				return npc;
			}
		}
		return null;
	}
	
	public void addPlayer(TerrariaPlayer player){
		players.put(player.getId(), player);
	}
	
	public Collection<TerrariaPlayer> getPlayers(){
		return players.values();
	}

	public TerrariaPlayer getPlayer(String player) {
		
		if(player.equalsIgnoreCase(getThePlayer().getName())){
			return getThePlayer();
		}
		
		for(TerrariaPlayer pl : players.values()){
			if(pl.getName().equalsIgnoreCase(player)){
				return pl;
			}
		}
		return null;
	}

	public List<Npc> getNpcs() {
		return npcs;
	}

	public int reconnectTo(String ip, int port) {
		//Forward the player to a new server!
		NetworkConnection con = null;
		try {
			
			con = connectionFactory.connect(InetAddress.getByName(ip), port);
			con.setTimeout(server.getTimeout());
			
			TerrariaPacket cc = null;
			
			long time = System.currentTimeMillis();
			
			con.sendData(new TerrariaPacketConnectionRequest());
			
			List<TerrariaPacket> packetsToSend = new ArrayList<TerrariaPacket>();
			
			while((cc = con.readPacket()) == null && (System.currentTimeMillis() - time) < 5000);
			
			if(cc == null){
				con.close();
				return -1;
			}
			
			if(cc.getType() != 3){
				con.close();
				return -2;
			}

			setConnectionIniatializationDone(false);
			
			TerrariaPlayerLocal player = getThePlayer();
			
			player.addBuff(156, 10);
			player.addBuff(149, 10);
			player.addBuff(47, 10);
			
			player.setHealth(0);
			
			cc.onReceive(this);
			sendPacketToClient(cc);
			
			con.sendData(new TerrariaPacketConnectionRequest());
			con.sendData(new TerrariaPacketPlayerInfo(player.getId(), player.getName(), player.getInfo()));
			con.sendData(TerrariaPacketUUID.getFakeUUIDPacket());
			con.sendData(new TerrariaPacketPlayerHp(player.getId(), player.getHp(), player.getMaxHp()));
			con.sendData(TerrariaPacketMana.getManaPacket(player.getId(), (short) player.getHp(), (short) player.getMaxHp()));
			con.sendData(new TerrariaPacketUpdatePlayerBuff(PacketType.UPDATE_BUFF.getId(), new byte[] {}));
			for(int i = 0; i < player.getInventorySize();i++){
				con.sendData(new TerrariaPacketInventorySlot(player.getId(), i, 1, 0, player.getInventoryItem(i)));
			}
			con.sendData(new TerrariaPacketContinue(PacketType.CONTINUE2.getId(), new byte[] {}));
			
			time = System.currentTimeMillis();
			
			while((System.currentTimeMillis() - time) < 5000){
				cc = con.readPacket();
				if(cc == null){
					continue;
				}
				cc.onReceive(this);
				packetsToSend.add(cc);
				time = System.currentTimeMillis();
				if(cc.getType() == 7){
					break;
				}
			}
			
			TerrariaPacketWorldInfo info = (TerrariaPacketWorldInfo) cc;
			
			TerrariaPacketGetSection get = new TerrariaPacketGetSection(-1, -1);
			
			con.sendData(get);
			
			time = System.currentTimeMillis();
			while((System.currentTimeMillis() - time) < 5000){
				cc = con.readPacket();
				if(cc == null){
					continue;
				}
				cc.onReceive(this);
				packetsToSend.add(cc);
				time = System.currentTimeMillis();
				if(cc.getType() == 7){
					break;
				}
			}	
			
			TerrariaPacketUpdatePlayer update = new TerrariaPacketUpdatePlayer(player.getId(), 0, 0, 0, info.getSpawnX(), info.getSpawnY(), 0, 0);
			
			con.sendData(update);
			sendPacketToClient(update);
			
			con.sendData(get);
			
			player.setHealth(0);
			
			packetsToSend.stream().forEach(p -> sendPacketToClient(p));
			
			server.close();
			server = con;
			
		} catch (UnknownHostException e) {
			return -5;
		} catch(ConnectException e){
			return -3;
		} catch (IOException e) {
			return -4;
		}catch(Exception e){
			return -10;
		}finally{
			if(server != con && con != null){
				try {
					con.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return 1;
		
	}

	public void setWorldInfo(WorldInfo worldInfo) {
		this.worldInfo = worldInfo;
	}
	
	public WorldInfo getWorldInfo() {
		return worldInfo;
	}
	
	public void updateWorldInfo(){
		sendPacketToClient(worldInfo.getPacket());
	}

	public ScriptManager getScriptManager() {
		return scriptManager;
	}

}
