package se.springworks.whenismybus.directions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Distance {
	@JsonProperty("text")
	private String text;
	
	@JsonProperty("value")
	private int distance;
	
	public String getText() {
		return text;
	}
	
	public int getDistanceInMeters() {
		return distance;
	}

}
