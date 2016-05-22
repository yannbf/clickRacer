package factory;

import java.io.IOException;

import interfaces.Client;
import interfaces.Server;

public abstract class AbstractFactory {
   public abstract Server getServer(int port, Settings.types protocol) throws IOException;
   public abstract Client getClient(Settings.types protocol);
}
