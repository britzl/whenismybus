package se.springworks.whenismybus.directions;

import java.util.HashMap;
import java.util.Map;

import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.json.IJsonParser;
import se.springworks.android.utils.logging.Logger;
import se.springworks.android.utils.rest.IRestClient;
import se.springworks.android.utils.rest.IRestClient.OnHttpResponseHandler;

import com.google.inject.Inject;

public class GoogleDirectionsApi implements IDirectionsApi {
	
	@InjectLogger
	private Logger logger;
	
	@Inject
	private IRestClient rest;
	
	@Inject
	private IJsonParser json;

	@Override
	public void directions(String from, String to, TravelMode mode, int departureTimeSeconds, final OnDirectionsCallback callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("origin", from);
		params.put("destination", to);
		params.put("sensor", "true");
		params.put("departure_time", Integer.toString(departureTimeSeconds));
		switch(mode) {
		default:
			logger.warn("unkown mode %s", mode);
		case DRIVING:
			params.put("mode", "driving");
			break;
		case TRANSIT:
			params.put("mode", "transit");
			break;
		case WALKING:
			params.put("mode", "walking");
			break;
		
		}
		
		rest.get("http://maps.googleapis.com/maps/api/directions/json", params, new OnHttpResponseHandler() {
			
			@Override
			public void onSuccess(String response) {
				Directions d = GoogleDirectionsApi.this.json.fromJson(response, Directions.class);
				callback.onDirections(d);
			}
			
			@Override
			public void onFailure(Throwable t, String response) {
				callback.onError(t, response);
			}
		});
	}
}
