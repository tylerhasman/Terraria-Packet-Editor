package me.tyler.terraria;

import java.io.UnsupportedEncodingException;

public class PacketUtil {

	public static String readString(byte[] buffer, int offset){
		
		byte length = buffer[offset];
		
		if(length == 0){
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
	
}
