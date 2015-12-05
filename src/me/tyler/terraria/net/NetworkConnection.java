package me.tyler.terraria.net;

import java.io.IOException;
import java.net.InetAddress;

import me.tyler.terraria.packets.TerrariaPacket;

public interface NetworkConnection {

	/**
	 * Send data to this connection
	 * @param packet the packet
	 */
	public void sendData(TerrariaPacket packet) throws IOException;
	
	/**
	 * Read a packet from this connect
	 * @return the packet or null if there is none to return
	 */
	public TerrariaPacket readPacket() throws IOException;
	
	/**
	 * Check if the network connection is still open
	 * @return true if it is
	 */
	public boolean isClosed();

	/**
	 * Close the connection
	 * @throws IOException 
	 */
	public void close() throws IOException;
	
	/**
	 * Set the connections timeout
	 * @param milliseconds
	 */
	public void setTimeout(int milliseconds);
	
	/**
	 * Get a connections timeout time
	 * @return
	 */
	public int getTimeout();

	/**
	 * Get address
	 * @return the connections remote address
	 */
	public InetAddress getInetAddress();
	
}
