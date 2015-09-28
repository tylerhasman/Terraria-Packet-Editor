package me.tyler.terraria;

import java.util.Random;


public class TerrariaColor {

	public static final TerrariaColor RED = TerrariaColor.getColor(190, 25, 25);
	public static final TerrariaColor GREEN = TerrariaColor.getColor(25, 210, 25);
	public static final TerrariaColor BLUE = TerrariaColor.getColor(100, 150, 190);
	public static final TerrariaColor PURPLE = TerrariaColor.getColor(140, 40, 255);
	public static final TerrariaColor YELLOW = TerrariaColor.getColor(255, 213, 28);
	
	private byte r, g, b;
	
	public byte getR() {
		return r;
	}
	
	public byte getG() {
		return g;
	}
	
	public byte getB() {
		return b;
	}
	
	public static TerrariaColor getColor(byte[] buffer){
		return getColor(buffer, 0);
	}
	
	public static TerrariaColor getColor(byte[] buffer, int offset)
	{
		if(buffer.length != 3){
			throw new RuntimeException("Error, color buffer must be of size 3, size was "+buffer.length);
		}
		
		TerrariaColor color = new TerrariaColor();
		
		color.r = buffer[offset];
		color.g = buffer[1+offset];
		color.b = buffer[2+offset];
		
		return color;
	}

	@Override
	public String toString() {
		return r+" "+g+" "+b;
	}

	public byte[] getBytes() {
		return new byte[] { r, g, b } ;
	}

	public static TerrariaColor getColor(int i, int j, int k) {
		
		TerrariaColor color = new TerrariaColor();
		
		color.r = (byte) i;
		color.g = (byte) j;
		color.b = (byte) k;
		
		return color;
	}

	public static TerrariaColor random() {
		
		Random random = new Random();
		
		byte[] bytes = new byte[3];
		
		random.nextBytes(bytes);
		
		TerrariaColor color = TerrariaColor.getColor(bytes);
		
		return color;
	}
	
}
