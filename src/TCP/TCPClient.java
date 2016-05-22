package TCP;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import connection.TCPConnection;
import interfaces.Client;
import interfaces.Connection;

public class TCPClient implements Client {

	public Socket socket;
	
	@Override
	public Connection connectToServer(String address, Integer port) throws UnknownHostException, IOException {
		socket = new Socket(address, port);
		return new TCPConnection(socket);
	}
}


