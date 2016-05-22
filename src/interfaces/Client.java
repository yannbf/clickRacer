package interfaces;

import java.io.IOException;
import java.net.UnknownHostException;

public interface Client {  
	public Connection connectToServer(String address, Integer port) throws UnknownHostException, IOException;
}
