package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import interfaces.Connection;

public class UDPConnection implements Connection {

	public DatagramSocket datagramSocket;
	public InetAddress iAddress;
	public int port;

	public UDPConnection(DatagramSocket socket, InetAddress iAddress, int port) throws UnknownHostException {
		this.datagramSocket = socket;
		this.iAddress = iAddress;
		this.port = port;
	}

	@Override
	public void send(String content){
		if(!datagramSocket.isClosed()){
			DatagramPacket toBeSent = new DatagramPacket(content.getBytes(),content.length(), iAddress, port);
			
			try {
				datagramSocket.send(toBeSent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String receive(){
		if(!datagramSocket.isClosed()){
			
			byte[] buffer = new byte[4028];
						
			try {
				
				DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
				
				datagramSocket.receive(receivedPacket);	
				
				return new String(receivedPacket.getData(), 0, receivedPacket.getLength());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
		}

		return null;
	}

	@Override
	public void close(){
		datagramSocket.close();
	}

	@Override
	public boolean isOpen() {
		return !datagramSocket.isClosed();
	}

}
