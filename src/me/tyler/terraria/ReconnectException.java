package me.tyler.terraria;

public class ReconnectException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4149630192201794399L;

	private int id;
	
	public ReconnectException(int id, String message) {
		super(message);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
}
