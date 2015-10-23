package me.tyler.terraria;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.tyler.terraria.packets.TerrariaPacket;
import me.tyler.terraria.script.Script;

public class Proxy {

	private String targetIp;
	private int targetPort;
	private Socket socket;
	private boolean isConnected;
	private TerrariaPlayer thePlayer;
	private Map<Byte, TerrariaPlayer> players;
	private Map<Short, TerrariaItemDrop> itemsOnGround;
	private List<Npc> npcs;
	private boolean isConnectionIniatializationDone;
	private TerrariaTile[][] tiles;
	private long lastScriptCycle;
	
	public Proxy(String ip, int port) {
		targetIp = ip;
		targetPort = port;
		players = new HashMap<>();
		npcs = new ArrayList<>();
		itemsOnGround = new HashMap<>();
		isConnected = false;
		isConnectionIniatializationDone = false;
		lastScriptCycle = System.currentTimeMillis();
	}
	
	public void connect() throws IOException {
		socket = new Socket(InetAddress.getByName(targetIp), targetPort);
		isConnected = true;
	}
	
	public void cycle(Socket client){
		
		try{
			
			TerrariaPacket sending = readDataFromSource(client);
			TerrariaPacket recv = readDataFromSource(socket);
			
			if(recv != null){
				if(recv.onReceive(this, client)){
					sendPacketToClient(client, recv);
				}
			}
			
			if(sending != null){
				if(sending.onSending(this, client)){
					sendPacketToServer(sending);
				}
			}
			
			if(isConnectionIniatializationDone){
				if(System.currentTimeMillis() - lastScriptCycle >= 500){
					for(Script script : Script.getAll()){
						if(script.doesCycle()){
							script.invoke("do_cycle", this, client);
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
	
	private TerrariaPacket readDataFromSource(Socket source) throws IOException{
		try{
			InputStream is = source.getInputStream();
			
			if(is.available() <= 2){
				return null;
			}
			
			ByteBuffer buffer = ByteBuffer.allocate(3).order(ByteOrder.LITTLE_ENDIAN);
			
			is.read(buffer.array());
			
			short length = buffer.getShort();
			
			if(length <= 0){
				return null;
			}
			
			byte type = buffer.get();
			
			while(is.available() < length - 3){
				if(!isConnected){
					return null;
				}
			}
			
			buffer = ByteBuffer.allocate(length).order(ByteOrder.LITTLE_ENDIAN);
			
			if(length < 3){
				System.out.println("length is "+length+"! Type "+ type);
				return null;
			}
			
			buffer.putShort(length);
			buffer.put(type);
			
			is.read(buffer.array(), buffer.position(), length - 3);
			
			TerrariaPacket packet = TerrariaPacket.getPacketFromData(buffer.array());
			
			return packet;	
		}catch(SocketTimeoutException e){
			System.out.println(e.getMessage());
		}
		
		return null;

	}
	
	public boolean isConnectionIniatializationDone() {
		return isConnectionIniatializationDone;
	}
	
	public void setConnectionIniatializationDone(boolean flag) {
		this.isConnectionIniatializationDone = flag;
	}
	
	public boolean isConnected() {
		return isConnected;
	}

	
	public void close() throws IOException{
		socket.close();
		isConnected = false;
	}
	
	private void sendPacketToOutputStream(OutputStream os, TerrariaPacket packet) throws IOException{
		ByteBuffer buf = ByteBuffer.allocate(packet.getLength()).order(ByteOrder.LITTLE_ENDIAN);		
		
		buf.putShort(packet.getLength());
		buf.put(packet.getType());
		buf.put(packet.getPayload());
		
		try{
			os.write(buf.array());
		}catch(Exception e){
			System.out.println(e.getMessage());
			close();
		}
	}
	
	public void sendPacketToClient(Socket client, TerrariaPacket packet){
		try {
			OutputStream os = client.getOutputStream();
			sendPacketToOutputStream(os, packet);
		} catch (IOException e) {
			e.printStackTrace();
			isConnected = false;
		}
	}
	
	public void sendPacketToServer(TerrariaPacket packet) {
		
		try {
			sendPacketToOutputStream(socket.getOutputStream(), packet);
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
	
	public TerrariaPlayer getThePlayer() {
		return thePlayer;
	}
	
	public void setThePlayer(TerrariaPlayer thePlayer) {
		this.thePlayer = thePlayer;
	}
	
	public TerrariaPlayer getPlayer(byte id){
		
		if(id == getThePlayer().getId()){
			return getThePlayer();
		}
		
		if(!players.containsKey(id)){
			players.put(id, new TerrariaPlayer(id));
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
	
	public TerrariaTile getTile(int x, int y, boolean makeIfNotExist){
		
		if(tiles[x][y] == null && makeIfNotExist){
			tiles[x][y] = new TerrariaTile();
		}else if(!makeIfNotExist && tiles[x][y] == null){
			return null;
		}
		
		return tiles[x][y];
	}

	public TerrariaTile getTileOrMake(int x, int y) {
		return getTile(x, y, true);
	}
	
	public void setWorldDimensions(int width, int height){
		tiles = new TerrariaTile[width][height];
	}
	
	public boolean areDimensionsSet(){
		return tiles != null;
	}

	public void reset() {
		
	}
	
}
