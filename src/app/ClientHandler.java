package app;

import interfaces.Connection;

public class ClientHandler extends Thread{
	private Connection connection;
	private ClientApplication ctx;

	public ClientHandler(ClientApplication ctx, Connection connection) {
		this.connection = connection;
		this.ctx = ctx;
	}

	public void run() {
		
		while(connection.isOpen()){
			String message = connection.receive();
			
			if(message != null){				
				System.out.println(message);
				ctx.click(message);
			}
		
		}
	}
}
