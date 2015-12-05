package me.tyler.terraria.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class SocketConnectionFactory implements ConnectionFactory {

	@Override
	public NetworkConnection connect(InetAddress address, int port) throws IOException {
		return new SocketNetworkConnection(new Socket(address, port));
	}

}
