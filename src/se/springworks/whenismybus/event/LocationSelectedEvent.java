package se.springworks.whenismybus.event;

import se.springworks.android.utils.map.geocoding.GeoCodeResult;

public class LocationSelectedEvent {

	public GeoCodeResult location;
	
	public LocationSelectedEvent(GeoCodeResult location) {
		this.location = location;
	}
}
