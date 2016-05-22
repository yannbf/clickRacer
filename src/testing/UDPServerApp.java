package testing;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

import factory.AbstractFactory;
import factory.FactoryProducer;
import factory.Settings;
import interfaces.Connection;
import interfaces.Server;

public class UDPServerApp {
	public static void main(String args[]) throws Exception{
		AbstractFactory serverFactory = FactoryProducer.getFactory(Settings.types.SERVER);
		Server server = serverFactory.getServer(5000, Settings.types.UDP);
		System.out.println("Servidor em execução");
		Connection connection = server.acceptConnection();
		
		connection.send("Teste1");
		while (true) {
			System.out.println(connection.receive());
		}
	}
}
