package se.springworks.whenismybus.inject;

import se.springworks.whenismybus.directions.GoogleDirectionsApi;
import se.springworks.whenismybus.directions.IDirectionsApi;

import com.google.inject.AbstractModule;

public class LiveModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IDirectionsApi.class).to(GoogleDirectionsApi.class);
	}

}
