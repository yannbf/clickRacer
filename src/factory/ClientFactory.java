package factory;

import TCP.TCPClient;
import UDP.UDPClient;
import interfaces.Client;
import interfaces.Server;

public class ClientFactory extends AbstractFactory{

	@Override
	public Client getClient(Settings.types protocolo) {
 
		if (protocolo.equals(protocolo.TCP)) {
			return new TCPClient();
		} 
		
		if(protocolo.equals(protocolo.UDP)){
			return new UDPClient();
		}
		
		return null; 
	}

	@Override
	public Server getServer(int port, Settings.types protocol) { 
		return null;
	} 
}
