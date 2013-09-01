package se.springworks.whenismybus.directions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolyLine {

	@JsonProperty("points")
	private String points;
	
	
	public String getPoints() {
		return points;
	}
}
