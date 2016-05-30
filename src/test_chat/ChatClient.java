package test_chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.UnknownHostException;

import factory.AbstractFactory;
import factory.FactoryProducer;
import factory.Settings;
import interfaces.Client;
import interfaces.Connection;

public class ChatClient {
	private Connection socket          = null;
	private DataInputStream  console   = null;

	public ChatClient(String serverName, int serverPort) {
		System.out.println("Establishing connection. Please wait ...");
		try	{
			AbstractFactory clientFactory = FactoryProducer.getFactory(Settings.types.CLIENT);
			Client client = clientFactory.getClient(Settings.types.UDP);
			socket = client.connectToServer(Settings.host, Settings.port);
			System.out.println("Connected: " + socket);
			start();
		}
		catch(UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		}
		catch(IOException ioe){
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
		String line = "";

		while (!line.equals(".bye")){
			try			{
				line = console.readLine();
				socket.send(line);
			}
			catch(IOException ioe){
				System.out.println("Sending error: " + ioe.getMessage());
			}
		}
	}

	public void start() throws IOException{
		console   = new DataInputStream(System.in);
	}

	public void stop() {
		try{
			if (console   != null)  console.close();
			if (socket    != null)  socket.close();
		}
		catch(IOException ioe){
			System.out.println("Error closing ...");
		}
	}

	public static void main(String args[]){
		new ChatClient("localhost", 2000);
	}
}