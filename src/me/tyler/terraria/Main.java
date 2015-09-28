package me.tyler.terraria;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

import me.tyler.terraria.gui.PacketEditorGUI;

public class Main {

	public static boolean gui = true;
	
	public static String forwardIp = "";
	public static int port = 0;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		try{
			
			if(args.length > 0){
				gui = args[0].equalsIgnoreCase("true");
			}
			
			if(gui){
				PacketEditorGUI gui = new PacketEditorGUI(new File("log.txt"));
				
				gui.setVisible(true);
				
				System.setOut(gui);
				System.setErr(gui);
			}

			
			System.out.println("Loaded "+TerrariaData.BUFFS.size()+" buffs.");
			System.out.println("Loaded "+TerrariaData.PROJECTILES.size()+" projectile types.");
			System.out.println("Loaded "+TerrariaData.BOSSES.size()+" bosses.");
			System.out.println("Loaded "+TerrariaData.ITEMS.size()+" items.");

			inputUserSettings();
			
			ServerSocket server = new ServerSocket(11000);
			
			System.out.println("Listening on port "+server.getLocalPort());
			
			Socket client = server.accept();
			
			client.setSoTimeout(1000);
			server.setSoTimeout(1000);

			System.out.println("Client accepted!");
			
			Proxy proxy = new Proxy(forwardIp, port);
			
			System.out.println("Proxy setup. "+client.getInetAddress().getHostAddress()+":11000 <-> "+forwardIp+":"+port);
			
			proxy.connect();
			
			while(proxy.isConnected()){
				proxy.cycle(client);
			}
			
			
			proxy.close();
			client.close();
			server.close();
			
			System.out.println("Connection closed");
		}finally{
			System.exit(0);
		}
		
	}
	
	private static void inputUserSettings(){
		
		if(gui){

			forwardIp = JOptionPane.showInputDialog(null, "Enter server ip", "Server Info", JOptionPane.QUESTION_MESSAGE);
			port = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Enter port", "Server Info", JOptionPane.QUESTION_MESSAGE, null, null, 7777));
			
		}else{
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("Enter the desired target ip: ");
			
			forwardIp = scanner.nextLine();
			
			System.out.println("Enter the desired target port: ");
			
			port = scanner.nextInt();
			
			scanner.close();
		}
		
	}
	
}
