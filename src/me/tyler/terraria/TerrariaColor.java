package me.tyler.terraria;


public class TerrariaColor {

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
		if(buffer.length != 3){
			throw new RuntimeException("Error, color buffer must be of size 3, size was "+buffer.length);
		}
		
		TerrariaColor color = new TerrariaColor();
		
		color.r = buffer[0];
		color.g = buffer[1];
		color.b = buffer[2];
		
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
	
}
