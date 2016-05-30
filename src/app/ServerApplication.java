package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import factory.AbstractFactory;
import factory.FactoryProducer;
import factory.Settings;
import factory.Settings.types;
import interfaces.Connection;
import interfaces.Server;

public class ServerApplication {
	
	private static List<Connection> clientConnections = new ArrayList<Connection>();

	public static void main(String[] args) throws IOException {
		
		types serverType = Settings.types.UDP;

		AbstractFactory serverFactory = FactoryProducer.getFactory(serverType.SERVER);
		Server server = serverFactory.getServer(Settings.port, serverType);
		
		System.out.println("Server connected on port " + Settings.port + ".");
		
		while(true){
			
			Connection client = server.acceptConnection();			
			System.out.println("New client connected.");
			clientConnections.add(client);
			
			ClientObserver ch = new ClientObserver(client);
			ch.start();
		}	
	}
	
	public static void notifyClients(String msg) {
		for (Connection clientConnection : clientConnections) {
			clientConnection.send(msg);
		}
	}

}
