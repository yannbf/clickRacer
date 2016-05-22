package testing;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import factory.AbstractFactory;
import factory.FactoryProducer;
import factory.Settings;
import interfaces.Client;
import interfaces.Connection;

public class UDPClientApp {
	private static int idMensagem = 1;

	public static void main(String[] args) throws Exception{
		AbstractFactory clientFactory = FactoryProducer.getFactory(Settings.CLIENT);
		Client client = clientFactory.getClient(Settings.UDP);
		Connection connection = client.connectToServer("localhost", 5000);
		System.out.println(connection.receive());
		
		for (int i = 0; i < 1000; i++) {
//			System.out.println(i);
			connection.send(""+i);
		}
	}
}
