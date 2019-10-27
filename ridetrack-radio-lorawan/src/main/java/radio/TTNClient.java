package radio;
import java.net.URISyntaxException;

import org.thethingsnetwork.data.mqtt.Client;

public class TTNClient extends Client {
	
	public TTNClient(String broker, String appId, String appAccessKey) throws URISyntaxException {
		super(broker, appId, appAccessKey);
	}
}
