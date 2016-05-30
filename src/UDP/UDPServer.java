package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

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
				DatagramSocket ds = new DatagramSocket();
				
				InetAddress iAddress = receivedPacket.getAddress();
				int port = receivedPacket.getPort();
				String mensagem = "your-connection-port:" + ds.getLocalPort();
				DatagramPacket toBeSent = new DatagramPacket(mensagem.getBytes(), mensagem.length(), iAddress, port);
				
				datagramSocket.send(toBeSent);
				return new UDPConnection(ds, iAddress, port);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
