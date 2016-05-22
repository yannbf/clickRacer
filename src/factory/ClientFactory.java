package factory;

import TCP.TCPClient;
import UDP.UDPClient;
import interfaces.Client;
import interfaces.Server;

public class ClientFactory extends AbstractFactory{

	@Override
	public Client getClient(Settings protocolo) {
 
		if (protocolo.equals(Settings.TCP)) {
			return new TCPClient();
		} 
		
		if(protocolo.equals(Settings.UDP)){
			return new UDPClient();
		}
		
		return null; 
	}

	@Override
	public Server getServer(int port, Settings protocol) { 
		return null;
	} 
}
