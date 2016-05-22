package factory;

public class FactoryProducer {
	public static AbstractFactory getFactory(Settings protocolo) {
		 
		if (protocolo.equals(Settings.CLIENT)) {
			return new ClientFactory();
		} 
		
		if(protocolo.equals(Settings.SERVER)){
			return new ServerFactory();
		}
		
		return null;  
	} 
}
