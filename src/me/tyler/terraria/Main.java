package me.tyler.terraria;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import me.tyler.terraria.gui.PacketEditorGUI;
import me.tyler.terraria.net.SocketNetworkConnection;

public class Main {

	public static boolean gui = true;
	
	public static String forwardIp = "";
	public static int port = 0;
	public static boolean reset = false;
	
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

			inputUserSettings();
			
			ServerSocket server = new ServerSocket(11000);
			
			System.out.println("Listening on port "+server.getLocalPort());
			
			Socket client = server.accept();
			
			client.setSoTimeout(1000);
			server.setSoTimeout(1000);

			System.out.println("Client accepted!");
			
			Proxy proxy = new Proxy(forwardIp, port, new SocketNetworkConnection(client));
			
			System.out.println("Proxy setup. "+client.getInetAddress().getHostAddress()+":11000 <-> "+forwardIp+":"+port);
			
			proxy.connect();
			
			while(proxy.isConnected()){
				proxy.cycle();
			}
			
			proxy.close();
			client.close();
			server.close();
			
			System.out.println("Connection closed");
		}catch(SocketException e){
			System.out.println(e.getMessage());
		}catch(NullPointerException e){
			e.printStackTrace();
		}finally{
			if(!gui)
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
			
			forwardIp = scanner.nextLine().trim();
			
			System.out.println("Enter the desired target port: (Press enter for default port)");
			
			String line = scanner.nextLine().trim();
			
			if(line.isEmpty()){
				port = 7777;
			}else{
				port = Integer.parseInt(line);
			}
			
			scanner.close();
		}
		
	}
	
}
