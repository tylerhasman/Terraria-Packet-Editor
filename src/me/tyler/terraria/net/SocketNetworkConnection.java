package me.tyler.terraria.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import me.tyler.terraria.packets.TerrariaPacket;

public class SocketNetworkConnection implements NetworkConnection, Runnable {

	private Socket socket;
	private List<TerrariaPacket> toSend;
	private ReentrantLock toSendLock;
	private List<TerrariaPacket> toRecieve;
	private ReentrantLock toRecieveLock;
	private Thread thread;
	private boolean threaded;
	
	public SocketNetworkConnection(Socket socket) {
		this(socket, true);
	}
	
	public SocketNetworkConnection(Socket socket, boolean threaded) {
		this.socket = socket;
		this.threaded = threaded;
		if(threaded){
			toSend = new ArrayList<TerrariaPacket>();
			toRecieve = new ArrayList<>();
			toSendLock = new ReentrantLock(true);
			toRecieveLock = new ReentrantLock(true);
			thread = new Thread(this, "Socket-Thread");
			thread.start();	
		}
	}
	
	public void run() {
		while(!isClosed()){
			try{
				try{
					toSendLock.lock();
					if(toSend.size() > 0){
						sendPacketToOutputStream(socket.getOutputStream(), toSend.remove(0));
					}
				}finally{
					toSendLock.unlock();
				}
				
				try{
					toRecieveLock.lock();
					TerrariaPacket packet = readDataFromSource(socket);
					
					if(packet != null){
						toRecieve.add(packet);
					}
					
				}finally{
					toRecieveLock.unlock();
				}

			} catch (IOException e) {
				System.out.println("Error "+ e.getMessage());
			}
		}
	}
	
	@Override
	public void sendData(TerrariaPacket packet) throws IOException {
		if(isClosed()){
			return;
		}
		if(threaded){
			try{
				toSendLock.lock();
				toSend.add(packet);
			}finally{
				toSendLock.unlock();
			}	
		}else{
			sendPacketToOutputStream(socket.getOutputStream(), packet);
		}
	}

	@Override
	public TerrariaPacket readPacket() throws IOException {
		if(isClosed()){
			return null;
		}
		if(threaded){
			if(toRecieve.size() > 0)
				return toRecieve.remove(0);
			else
				return null;
		}else{
			return readDataFromSource(socket);	
		}
	}
	
	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}
	
	@Override
	public void close() throws IOException {
		if(!socket.isClosed())
			socket.close();
		if(threaded)
			thread.interrupt();
	}
	
	@Override
	public void setTimeout(int milliseconds) {
		try {
			socket.setSoTimeout(milliseconds);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getTimeout() {
		try {
			return socket.getSoTimeout();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	@Override
	public InetAddress getRemoteAddress() {
		return socket.getInetAddress();
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
				if(isClosed()){
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
			socket.close();
		}
		
		return null;

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
			socket.close();
		}
	}
	
}
