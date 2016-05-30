package test_chat;
import java.net.*;

import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion.Setting;

import factory.AbstractFactory;
import factory.FactoryProducer;
import factory.Settings;
import interfaces.Connection;
import interfaces.Server;

import java.io.*;

public class ChatServer implements Runnable {
	private Server           server = null;
	private Thread           thread = null;
	private ChatServerThread client = null;

	public ChatServer(int port)	{ 
		try {
			System.out.println("Binding to port " + port + ", please wait  ...");
			AbstractFactory serverFactory = FactoryProducer.getFactory(Settings.types.SERVER);
			server = serverFactory.getServer(Settings.port, Settings.types.UDP);
			System.out.println("Server started: " + server);
			start();
		}
		catch(IOException ioe) {
			System.out.println(ioe); }
	}

	public void run(){
		while (thread != null){
			System.out.println("Waiting for a client ..."); 
			addThread(server.acceptConnection());
		}
	}

	public void addThread(Connection socket){
		System.out.println("Client accepted: " + socket);
		client = new ChatServerThread(this, socket);
		client.start();
	}

	public void start(){
		if (thread == null){
			thread = new Thread(this); 
			thread.start();
		}
	}

	public void stop(){
		if (thread != null){
			thread.stop(); 
			thread = null;
		}
	}

	public static void main(String args[]){
		new ChatServer(2000);
	}
}