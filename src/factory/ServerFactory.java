package factory;

import java.io.IOException;

import TCP.TCPServer;
import UDP.UDPServer;
import interfaces.Client;
import interfaces.Server;

public class ServerFactory extends AbstractFactory{

	@Override
	public Server getServer(int port, Settings.types protocolo) throws IOException {
 
		if (protocolo.equals(protocolo.TCP)) {
			return new TCPServer(port);
		} 
		
		if(protocolo.equals(protocolo.UDP)){
			return new UDPServer(port);
		}
		
		return null;  
	}

	@Override
	public Client getClient(Settings.types protocol) { 
		return null;
	} 
}
