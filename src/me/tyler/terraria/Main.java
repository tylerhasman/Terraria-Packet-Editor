package me.tyler.terraria;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

	public static String forwardIp = "";
	public static int port = 0;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Items.loadItems();
		Buffs.loadBuffs();
		Bosses.loadBosses();
		
		inputUserSettings();
		
		ServerSocket server = new ServerSocket(11000);
		
		System.out.println("Listening on port "+server.getLocalPort());
		
		Socket client = server.accept();
		
		System.out.println("Client accepted!");
		
		Proxy proxy = new Proxy(forwardIp, port);
		
		proxy.connect();
		
		while(proxy.isConnected()){
			proxy.cycle(client);
		}
		
		
		proxy.close();
		client.close();
		server.close();
		
		System.out.println("Connection closed");
		
		
	}
	
	private static void inputUserSettings(){
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter the desired target ip: ");
		
		forwardIp = scanner.nextLine();
		
		System.out.println("Enter the desired target port: ");
		
		port = scanner.nextInt();
		
		scanner.close();
		
	}
	
}
