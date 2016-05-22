package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.SecureRandom;

import connection.UDPConnection;
import interfaces.Connection;
import interfaces.Server;

public class UDPServer implements Server {

	DatagramSocket datagramSocket;

	public UDPServer(int port) {
		try {
			datagramSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("Could not connect the server");
		}
	}
	
	@Override
	public Connection acceptConnection() {
		byte[] buffer = new byte[4028];		
		DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);

		try {
			
			datagramSocket.receive(receivedPacket);
			
			String message = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
			
			if(message.equals("start-server")){
			
				DatagramSocket novoSocket = generateNewSocket();
				InetAddress address = receivedPacket.getAddress();
				int port = receivedPacket.getPort();
				String mensagem = "server-port:" + port;
				DatagramPacket toBeSent = new DatagramPacket(mensagem.getBytes(), mensagem.length(), address, port);
				
				datagramSocket.send(toBeSent);
				InetAddress iAddress = receivedPacket.getAddress();
				
				return new UDPConnection(novoSocket, iAddress, port);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	private DatagramSocket generateNewSocket(){
		
		int port = new SecureRandom().nextInt(50000);
		
		boolean creationSucceded = false;
		
		DatagramSocket newSocket = null;
		
		while(!creationSucceded){
			
			try {
				newSocket = new DatagramSocket(port);
				creationSucceded = true;
			} catch (SocketException e) {
				continue;
			}
			
		}
		
		return newSocket;
		
	}

}
