package factory;

public class FactoryProducer {
	public static AbstractFactory getFactory(Settings.types protocolo) {
		 
		if (protocolo.equals(protocolo.CLIENT)) {
			return new ClientFactory();
		} 
		
		if(protocolo.equals(protocolo.SERVER)){
			return new ServerFactory();
		}
		
		return null;  
	} 
}
