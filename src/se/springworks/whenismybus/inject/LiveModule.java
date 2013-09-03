package se.springworks.whenismybus.inject;

import se.springworks.android.utils.map.directions.GoogleDirectionsApi;
import se.springworks.android.utils.map.directions.IDirectionsApi;
import se.springworks.android.utils.map.geocoding.GoogleGeoCodingApi;
import se.springworks.android.utils.map.geocoding.IGeoCodingApi;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class LiveModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IDirectionsApi.class).to(GoogleDirectionsApi.class);
		bind(IGeoCodingApi.class).to(GoogleGeoCodingApi.class).in(Singleton.class);
	}

}
