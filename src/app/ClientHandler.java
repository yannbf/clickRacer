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
				
				String[] params = message.split(",");
				ctx.updatePlayers(params[0], params[1], Integer.parseInt(params[2]));
			}
		}
	}
}
