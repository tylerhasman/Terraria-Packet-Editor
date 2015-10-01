package me.tyler.terraria.tools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CSharpDecompressor {

	private static Socket socket;
	
	public static boolean initialize(){
		socket = new Socket();
		
		try {
			socket.setSoTimeout(5000);
			socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), 11001));
			socket.setSoTimeout(60000);
			return true;
		} catch (UnknownHostException e) {
			//Fall through
		} catch (IOException e) {
			//Fall through
		}finally{
			if(!socket.isConnected()){
				socket = null;
			}
		}
		
		return false;
	}
	
	public static byte[] decompress(byte[] bytes){
		
		if(!isEnabled()){
			return bytes;
		}
		
		try {
			ByteBuffer buf = ByteBuffer.allocate(bytes.length + 4).order(ByteOrder.LITTLE_ENDIAN);
			
			buf.putInt(bytes.length);
			buf.put(bytes);
			
			socket.getOutputStream().write(buf.array());
			
			ByteBuffer sizeInByteArray = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
			
			socket.getInputStream().read(sizeInByteArray.array());
			
			int size = sizeInByteArray.getInt();
			
			byte[] toRead = null;
			
			System.out.println("Size: "+size);
			
			if(size > Runtime.getRuntime().freeMemory()){
				System.err.println("ERROR: SIZE IS GREATER THAN FREE MEMORY! "+size+" / "+Runtime.getRuntime().freeMemory());
				return null;
			}else{
				toRead = new byte[size];
			}
			
			socket.getInputStream().read(toRead);
			
			return toRead;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public static boolean isEnabled() {
		return socket != null && socket.isConnected();
	}
	
}
