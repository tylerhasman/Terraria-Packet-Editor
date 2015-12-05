package me.tyler.terraria.net;

import java.io.IOException;
import java.net.InetAddress;

public interface ConnectionFactory {

	/**
	 * Connect to a address
	 * @param address
	 * @param port
	 * @return the new connection
	 * @throws IOException
	 */
	public NetworkConnection connect(InetAddress address, int port) throws IOException;
	
}
