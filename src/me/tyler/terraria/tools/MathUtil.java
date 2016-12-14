package me.tyler.terraria.tools;

public class MathUtil {

	public static float distance(float x, float y, float x2, float y2){
		float x3 = (float) Math.pow(x - x2, 2);
		float y3 = (float) Math.pow(y - y2, 2);

		float distance = (float) Math.sqrt(x3 + y3);
		
		return distance;
	}
	
}
