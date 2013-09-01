package se.springworks.whenismybus.directions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LatLong {
	@JsonProperty("lat")
	private double latitude;
	
	@JsonProperty("lng")
	private double longitude;

	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
}
