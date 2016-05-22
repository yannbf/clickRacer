package TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import connection.TCPConnection;
import interfaces.Connection;
import interfaces.Server;

public class TCPServer implements Server {
	ServerSocket serverSocket;
	
	public TCPServer(int port) throws IOException{
		serverSocket = new ServerSocket(port);
	}
	
	@Override
	public Connection acceptConnection() {
		try {
			Socket socket = serverSocket.accept();
			TCPConnection tcp = new TCPConnection(socket);
			return tcp;
		} catch (IOException e) {
			System.out.println("Could not connect to server");
		}
		
		return null;
	}
}
