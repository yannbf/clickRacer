package connection;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import interfaces.Connection;

public class TCPConnection implements Connection {

	public Socket socket;
	public PrintStream prt;
	
	public TCPConnection(Socket socket) {
		this.socket = socket;
		
		/*ClientObserver ch = new ClientObserver(this);
		ch.start();
		
		ClientHandler rc = new ClientHandler(this);
		rc.start();*/
	}

	@Override
	public void send(String content){
		if(isOpen()){
			try {
				prt = new PrintStream(socket.getOutputStream());
				prt.println(content);
			} catch (IOException e) {
				System.out.println("Could not send message.");
			}
		}
	}

	@Override
	public String receive(){
		if(isOpen()){
			try {
				Scanner s = new Scanner(this.socket.getInputStream());
				if(s.hasNextLine())
					return s.nextLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Could not close connection.");
		}
	}

	@Override
	public boolean isOpen() {
		return !socket.isClosed();
	}
	
	/*public class ClientObserver extends Thread{
		private Connection connection;

		public ClientObserver(Connection connection) {
			
			this.connection = connection;
		}

		@Override
		public void run() {
			while(connection.isOpen()){
				String message = connection.receive();
				
				if(!message.isEmpty()){
					ServerApp.notifyClients(message);
				}
			}
		}
	}
	
	public class ClientHandler extends Thread{
		private Connection connection;

		public ClientHandler(Connection connection) {
			this.connection = connection;
		}
	
		public void run() {
			
			while(connection.isOpen()){
			
				String message = connection.receive();
				
				if(message != null)				
					System.out.println(message);
			
			}
		}
	}*/
}
