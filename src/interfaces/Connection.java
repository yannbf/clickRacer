package interfaces;

public interface Connection {

	public void send(String content);

	public String receive();

	public void close();
	
	public boolean isOpen();
}