package app;

import interfaces.Connection;

public class ClientObserver extends Thread{
	private Connection connection;

	public ClientObserver(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void run() {
		while(connection.isOpen()){ 
			String message = connection.receive(); 
			if(!message.isEmpty()){
				System.out.println("Notifying: " +message);
				ServerApplication.notifyClients(message);
			}
		}
	}
}