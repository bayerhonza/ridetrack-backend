package radio;

import java.net.URISyntaxException;

public  class TTNService {
	
	private TTNService() {}
	
	public static TTNClient getTTNClient(String region, String appId, String accessKey) throws URISyntaxException {
		return new TTNClient(region, appId, accessKey);
	}
	
}
