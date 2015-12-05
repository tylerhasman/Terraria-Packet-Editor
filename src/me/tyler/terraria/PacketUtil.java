package me.tyler.terraria;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class PacketUtil {

	public static int calculateLength(String str){
		
		try{
			return str.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	public static String readString(byte[] buffer, int offset){
		
		int length = buffer[offset] & 0xFF;
		
		if(length <= 0){
			return "";
		}
		
		byte[] str = new byte[length];
		
		for(int i = 0; i < length;i++)
		{
			str[i] = buffer[i + offset + 1];
		}
		
		try {
			return new String(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static void writeString(ByteBuffer buf, String str){
		try {
			byte[] strBuf = str.getBytes("UTF-8");
			
			byte length = (byte) (strBuf.length & 0xFF);

			buf.put(length);
			buf.put(strBuf);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
}
