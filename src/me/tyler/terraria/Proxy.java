package me.tyler.terraria;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.tyler.terraria.packets.TerrariaPacket;

public class Proxy {

	private String targetIp;
	private int targetPort;
	private Socket socket;
	private boolean isConnected;
	private TerrariaPlayer thePlayer;
	private Map<Byte, TerrariaPlayer> players;
	private List<Npc> npcs;
	
	public Proxy(String ip, int port) {
		targetIp = ip;
		targetPort = port;
		players = new HashMap<>();
		npcs = new ArrayList<>();
		isConnected = false;
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
			
		}catch(Exception e){
			isConnected = false;
			e.printStackTrace();
		}
		
	}
	
	private TerrariaPacket readDataFromSource(Socket source) throws IOException{
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

	}
	
	/*private void logToFile(String str){
		File f = new File("Log.txt");
		
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			System.out.println(str);
			BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
			
			writer.write(str);
			writer.newLine();
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}*/
	
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
		
		os.write(buf.array());
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
	
	public TerrariaPlayer getThePlayer() {
		return thePlayer;
	}
	
	public void setThePlayer(TerrariaPlayer thePlayer) {
		this.thePlayer = thePlayer;
	}
	
	public TerrariaPlayer getPlayer(byte id){
		
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
		for(TerrariaPlayer pl : players.values()){
			if(pl.getName().equals(player)){
				return pl;
			}
		}
		return null;
	}

	public List<Npc> getNpcs() {
		return npcs;
	}
	
}
