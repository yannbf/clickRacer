package test_chat;
import java.net.*;

import interfaces.Connection;

import java.io.*;

public class ChatServerThread extends Thread{
	private Connection socket = null;
	private ChatServer server = null;
	private int        ID     = -1;

	public ChatServerThread(ChatServer server, Connection socket)	{ 
		this.server = server;
		this.socket = socket;
//		ID = socket.getPort();
	}

	public void run(){
		System.out.println("Server Thread " + ID + " running.");
		while (true){
			System.out.println(socket.receive());
		}
	}

	public void close() throws IOException{
		if (socket.isOpen())    socket.close();
	}
}