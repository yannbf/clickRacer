package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import connection.UDPConnection;
import interfaces.Client;
import interfaces.Connection;

public class UDPClient implements Client {

	@Override
	public Connection connectToServer(String address, Integer port) throws UnknownHostException, IOException {
		try {			
			DatagramSocket socket = new DatagramSocket();
			
			InetAddress iAddress = InetAddress.getByName(address);
			
			String connectionMessage = "start-server";
			DatagramPacket connectionPackage = new DatagramPacket(connectionMessage.getBytes(), connectionMessage.length(), iAddress, port);
			socket.send(connectionPackage);
		
			byte[] buffer = new byte[1024];
			DatagramPacket receivedPackage = new DatagramPacket(buffer, buffer.length);
			socket.receive(receivedPackage);
			
			String [] codigo = new String(receivedPackage.getData(), 0, receivedPackage.getLength()).split(":");
			System.out.println(codigo[0]+": "+codigo[1]);
			Integer porta = Integer.parseInt(codigo[1]);
			if(codigo[0].equals("your-connection-port")){
				return new UDPConnection(socket, iAddress, porta);
			}
			
		} catch (Exception e) {
			System.out.println("Could not connect to server.");
		}		
		
		return null;
	}
}
